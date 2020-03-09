package com.example.libnetwork

import java.lang.Exception
import java.lang.StringBuilder
import java.net.URLEncoder

/**
 * author: created by wentaoKing
 * date: created in 2020-03-02
 * description: url创建器
 */
class UrlCreator {

    companion object {

        /**
         * 根据参数构建url
         */
        fun createUrlFromParams(url: String, params: Map<String, Any>): String {

            val builder = StringBuilder()
            builder.append(url)

            if (url.indexOf("?") > 0 || url.indexOf("&") > 0) {
                builder.append("&")
            } else {
                builder.append("?")
            }

            for (entry in params.entries) {
                try {
                    val value = URLEncoder.encode(entry.value.toString(), "UTF-8")
                    builder.append(entry.key).append("=").append(value).append("&")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            builder.deleteCharAt(builder.length - 1)
            return builder.toString()
        }

    }
}
