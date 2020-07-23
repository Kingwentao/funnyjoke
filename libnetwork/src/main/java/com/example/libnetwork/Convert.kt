package com.example.libnetwork

import java.lang.reflect.Type

/**
 * author: created by wentaoKing
 * date: created in 2020-03-09
 * description: 转换器接口
 */
interface Convert<T> {

    fun convert(response: String, type: Type): T?

}