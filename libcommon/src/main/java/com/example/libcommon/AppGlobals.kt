package com.example.libcommon

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import java.lang.Exception
import java.lang.reflect.InvocationTargetException

/**
 * author: created by wentaoKing
 * date: created in 2020-01-15
 * description: 应用全局信息
 */
object AppGlobals {

        private const val TAG = "AppGlobals"
        private var sApplication: Application? = null

        @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
        fun getApplication(): Application {
            if (sApplication == null) {
                //通过反射`currentApplication`方法获取application对象
                try {
                    val method = Class.forName("android.app.ActivityThread")
                        .getDeclaredMethod("currentApplication")
                    sApplication = method.invoke(null ) as Application
                } catch (e: NoSuchMethodException) {
                    Log.d(TAG, "没有此方法异常")
                } catch (e: ClassNotFoundException) {
                    Log.d(TAG, "未发现类异常")
                } catch (e: IllegalAccessException) {
                    Log.d(TAG, "不合法接入异常")
                } catch (e: InvocationTargetException) {
                    Log.d(TAG, "调用目标异常")
                } catch (e: Exception) {
                    Log.d(TAG, "未知异常 + ${e.message}")
                    e.printStackTrace()
                }
            }
            return sApplication!!
        }

}