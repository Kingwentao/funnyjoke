package com.example.libnetwork

import okhttp3.FormBody

/**
 * author: created by wentaoKing
 * date: created in 2020-03-09
 * description: post 请求
 */
class PostRequest<T>(private val mUrl: String) : Request<T, PostRequest<T>>(mUrl) {

    /**
     * 生成Post请求的request
     * @param builder Builder
     * @return okhttp3.Request
     */
    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        val formBuilder = FormBody.Builder()
        for (entry in params.entries) {
            formBuilder.add(entry.key, entry.value.toString())
        }
        return builder.url(mUrl).post(formBuilder.build()).build()
    }

}