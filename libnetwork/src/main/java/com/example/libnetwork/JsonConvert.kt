package com.example.libnetwork

import com.alibaba.fastjson.JSON
import java.lang.reflect.Type
import java.util.*

/**
 * author: created by wentaoKing
 * date: created in 2020-03-09
 * description: json convert
 */
class JsonConvert<T> : Convert<T> {

    override fun convert(response: String, type: Type): T? {
        val jSONObject = JSON.parseObject(response)
        val data = jSONObject.getJSONObject("data")
        if (data != null) {
            val data1 = data.get("data")
            return JSON.parseObject(data1.toString(), type)
        }
        return null
    }

}