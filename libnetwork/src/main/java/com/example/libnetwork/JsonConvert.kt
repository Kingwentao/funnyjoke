package com.example.libnetwork

import com.alibaba.fastjson.JSON
import java.lang.reflect.Type

/**
 * author: created by wentaoKing
 * date: created in 2020-03-09
 * description: json convert
 */
class JsonConvert<T> : Convert<T> {

    override fun convert(response: String, type: Type): T? {
        val jSONObject = JSON.parseObject(response)
        val dataJsonObject = jSONObject.getJSONObject("data")
        if (dataJsonObject != null) {
            val data = dataJsonObject["data"]
            return JSON.parseObject(data.toString(), type)
        }
        return null
    }

}