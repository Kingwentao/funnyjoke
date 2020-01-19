package com.example.funnyjoke.utils

import android.annotation.SuppressLint
import android.app.Application
import java.lang.Exception

/**
 * author: created by wentaoKing
 * date: created in 2020-01-15
 * description:
 */
class AppGlobals {

    companion object {

        private var sApplication: Application? = null


        fun getApplication():Application{
            if (sApplication == null){
                //通过反射获取application对象
                try {
                    val method = Class.forName("android.app.ActivityThread")
                        .getDeclaredMethod("currentApplication")
                    sApplication = method.invoke(null,null) as Application
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            return sApplication!!
        }
    }
}