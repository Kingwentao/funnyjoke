package com.example.funnyjoke.utils

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator

/**
 * author: created by wentaoKing
 * date: created in 2020-01-16
 * description: 页面导航图构建
 */
class NavGraphBuilder {

    companion object{

        fun build(navController: NavController,fragmentActivity: FragmentActivity,containerId: Int){
            val provider = navController.navigatorProvider
            //val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
            //使用自定义的fragment navigator 实现显示和隐藏的方式
            val fragmentNavigator = FixFragmentNavigator(fragmentActivity,fragmentActivity.supportFragmentManager,containerId)
            provider.addNavigator(fragmentNavigator)

            val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)
            val navGraph = NavGraph(NavGraphNavigator(provider))
            val destConfig = AppConfig.getDestConfig()

            for (destination in destConfig.values){
                if (destination.isFragment){
                    val fgDestination = fragmentNavigator.createDestination()
                    fgDestination.className = destination.className
                    fgDestination.id = destination.id
                    fgDestination.addDeepLink(destination.pageUrl)
                    navGraph.addDestination(fgDestination)
                }else{
                    val atDestination = activityNavigator.createDestination()
                    atDestination.setComponentName(ComponentName(
                        com.example.libcommon.AppGlobals.getApplication().packageName
                        ,destination.className))
                    atDestination.id = destination.id
                    atDestination.addDeepLink(destination.pageUrl)
                    navGraph.addDestination(atDestination)
                }
                //设置页面启动页：将启动页的id进行关联
                if (destination.asStarter){
                    navGraph.startDestination = destination.id
                }
            }

            //构建完成将graph赋值给navController
            navController.graph = navGraph
        }

    }
}