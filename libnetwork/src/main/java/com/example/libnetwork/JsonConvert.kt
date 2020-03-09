package com.example.libnetwork

import com.alibaba.fastjson.JSON
import java.lang.reflect.Type

/**
 * author: created by wentaoKing
 * date: created in 2020-03-09
 * description:
 */
class JsonConvert<T> : Convert<T>{


    override fun convert(response: String, type: Type): Any? {
        val jSONObject = JSON.parseObject(response)
        val data = jSONObject.getJSONObject("data")
        if (data != null){
            val data1 = data.get("data")
            return JSON.parseObject(data1.toString(),type)
        }
        return null
    }

    override fun convert(response: String, claz: Class<T>): Any? {
        val jSONObject = JSON.parseObject(response)
        val data = jSONObject.getJSONObject("data")
        if (data != null){
            val data1 = data.get("data")
            return JSON.parseObject(data1.toString(),claz)
        }
        return null
    }
}