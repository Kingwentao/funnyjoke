package com.example.libnetwork

/**
 * author: created by wentaoKing
 * date: created in 2020-03-02
 * description: get 请求
 */
class GetRequest<T>(val url: String) : Request<T, GetRequest<T>>(url) {


    /**
     * 生成get请求
     */
    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        return builder.get().url(UrlCreator.createUrlFromParams(url, params)).build()
    }

}

