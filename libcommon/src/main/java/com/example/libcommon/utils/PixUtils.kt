package com.example.libcommon.utils

import com.example.libcommon.AppGlobals

/**
 * author: created by wentaoKing
 * date: created in 2020-04-04
 * description: 像素工具类
 */
class PixUtils {

    companion object{
        fun dp2px(dpValue: Int): Int {
            val metrics = AppGlobals.getApplication().resources.displayMetrics
            return (metrics.density * dpValue + 0.5f).toInt()
        }

        fun getScreenWidth(): Int {
            val metrics = AppGlobals.getApplication().resources.displayMetrics
            return metrics.widthPixels
        }

        fun getScreenHeight(): Int {
            val metrics = AppGlobals.getApplication().resources.displayMetrics
            return metrics.heightPixels
        }
    }

}