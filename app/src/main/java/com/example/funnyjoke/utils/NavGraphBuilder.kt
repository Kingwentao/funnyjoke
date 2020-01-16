package com.example.funnyjoke.utils

import android.content.ComponentName
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator

/**
 * author: created by wentaoKing
 * date: created in 2020-01-16
 * description: 页面导航图构建
 */
class NavGraphBuilder {

    companion object{

        fun build(navController: NavController){
            val provider = navController.navigatorProvider
            val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
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
                    atDestination.setComponentName(ComponentName(AppGlobals.getApplication().packageName
                        ,destination.className))
                    atDestination.id = destination.id
                    atDestination.addDeepLink(destination.pageUrl)
                    navGraph.addDestination(atDestination)
                }

                if (destination.asStarter){
                    navGraph.startDestination = destination.id
                }
            }

            //构建完成将graph赋值给navController
            navController.graph = navGraph
        }

    }
}