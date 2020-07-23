package com.example.libnetwork.cache

import androidx.room.TypeConverter
import java.util.*

/**
 * author: created by wentaoKing
 * date: created in 2020-03-24
 * description: 数据类型转化
 */
class DateConvert {

    companion object {

        @TypeConverter
        fun date2Long(date: Date): Long {
            return date.time
        }

        @TypeConverter
        fun long2Date(date: Long): Date {
            return Date(date)
        }
    }
}