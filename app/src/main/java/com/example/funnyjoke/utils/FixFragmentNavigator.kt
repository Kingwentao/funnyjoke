package com.example.funnyjoke.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.lang.Exception
import java.util.*

/**
 * author: created by wentaoKing
 * date: created in 2020-02-01
 * description: 自定义的fragment navigator 实现显示和隐藏的方式
 */

@Navigator.Name("fixfragment")
class FixFragmentNavigator(
    var mContext: Context, var mFragmentManager: FragmentManager,
    var mContainerId: Int
) : FragmentNavigator(mContext, mFragmentManager, mContainerId) {

    companion object {
        private const val TAG = "FixFragmentNavigator";
    }


    override fun navigate(
        destination: Destination, args: Bundle?, navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?): NavDestination? {

        if (mFragmentManager.isStateSaved) {
            Log.i(TAG, "Ignoring navigate() call: FragmentManager has already" + " saved its state")
            return null
        }

        var className = destination.className
        if (className[0] == '.') {
            className = mContext.packageName + className
        }

//        val frag = instantiateFragment(
//            mContext, mFragmentManager,
//            className, args
//        )
//        frag.arguments = args
        val ft = mFragmentManager.beginTransaction()

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        // ft.replace(mContainerId, frag)
        //1.改成show和hide的方式来操作导航栏
        //1.1 隐藏之前的fragment
        val fragment = mFragmentManager.primaryNavigationFragment
        if (fragment != null) ft.hide(fragment)

        //1.2 对当前fragment进行显隐操作
        var frag: Fragment? = null
        val tag = destination.id.toString()

        frag = mFragmentManager.findFragmentByTag(tag)
        if (frag != null) {
            ft.show(frag)
        } else {
            frag = instantiateFragment(mContext, mFragmentManager, className, args)
            frag.arguments = args
            ft.add(mContainerId, frag)
        }

        ft.setPrimaryNavigationFragment(frag)

        @IdRes val destId = destination.id


        //2.通过反射拿到堆栈对象
        var mBackStack: ArrayDeque<Int>? = null
        try {
            val field = FragmentNavigator::class.java.getDeclaredField("mBackStack")
            field.isAccessible = true
            mBackStack = field.get(this) as ArrayDeque<Int>
        } catch (e: Exception) {
            Log.d(TAG, "get mBackStack object exception!!!")
        }

        val initialNavigation = mBackStack!!.isEmpty()
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStack.peekLast() == destId)

        val isAdded: Boolean
        if (initialNavigation) {
            isAdded = true
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (mBackStack.size > 1) {
                mFragmentManager.popBackStack(
                    generateBackStackName(mBackStack.size, mBackStack.peekLast()),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                ft.addToBackStack(generateBackStackName(mBackStack.size, destId))

            }
            isAdded = false
        } else {
            ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
            isAdded = true
        }
        if (navigatorExtras is Extras) {
            val extras = navigatorExtras as Extras?
            for ((key, value) in extras!!.sharedElements) {
                ft.addSharedElement(key, value)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        // The commit succeeded, update our view of the world
        if (isAdded) {
            mBackStack.add(destId)
            return destination
        } else {
            return null
        }
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
        return "$backStackIndex-$destId"
    }
}