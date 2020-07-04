package com.example.libnetwork

import okhttp3.FormBody

/**
 * author: created by wentaoKing
 * date: created in 2020-03-09
 * description: post 请求
 */
class PostRequest<T>(val url: String) : Request<T,PostRequest<T>>(url) {

    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {

        val formBuilder = FormBody.Builder()
        for (entry in params.entries){
            formBuilder.add(entry.key,entry.value.toString())
        }
        val request = builder.url(url).post(formBuilder.build()).build()
        return  request
    }

}