package com.example.libnetwork

import java.lang.reflect.Type

/**
 * author: created by wentaoKing
 * date: created in 2020-03-09
 * description:
 */
interface Convert<T> {

    fun convert(response: String,type: Type): Any?

    fun convert(response: String,claz: Class<T>): Any?


}