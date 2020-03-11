package com.example.libnetwork

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * author: created by wentaoKing
 * date: created in 2020-03-02
 * description:
 */
abstract class Request<T, R> {

    private var mType: Type ?= null
    private var mUrl: String
    protected val headers = HashMap<String, String>()
    protected val params = HashMap<String, Any>()

    private var cacheKey: String? = null

    companion object {
        private const val TAG = "Request"
        //仅仅只访问本地缓存，即便本地缓存不存在，也不会发起网络请求
        private const val CACHE_ONLY = 1
        //先访问缓存，同时发起网络的请求，成功后缓存到本地
        private const val CACHE_FIRST = 2
        //仅仅只访问服务器，不存任何存储
        private const val NET_ONLY = 3
        //先访问网络，成功后缓存到本地
        private const val NET_CACHE = 4
    }


    constructor(url: String) {
        this.mUrl = url
    }

    fun addHeaders(key: String, value: String): R {
        headers[key] = value
        return this as R
    }

    fun addParams(key: String, value: Any?): R {
        if (value == null) {
            return this as R
        }

        //int byte char short long double float boolean 和他们的包装类型，但是除了 String.class 所以要额外判断

        try {
            if (value.javaClass == String::class.java) {
                params[key] = value
            } else {
                val field = value.javaClass.getField("TYPE")
                val claz = field.get(null) as Class<*>
                if (claz.isPrimitive) {
                    params[key] = value
                }
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return this as R
    }

    fun cacheKey(key: String): R {
        this.cacheKey = key
        return this as R
    }

    fun execute(jsonCallBack: JsonCallBack<T>) {

        getCall().enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {

                val response = ApiResponse<T>()
                response.message = e.message
                jsonCallBack.onError(response)
            }

            override fun onResponse(call: Call, response: Response) {
                val apiResponse: ApiResponse<T> = parseResponse(response, jsonCallBack)

                if (apiResponse.success!!) {
                    jsonCallBack.onSuccess(apiResponse)
                } else {
                    jsonCallBack.onError(apiResponse)
                }

            }

        })
    }


    private fun parseResponse(
        response: Response,
        callBack: JsonCallBack<T>? = null
    ): ApiResponse<T> {
        var message: String? = null
        val status = response.code
        var success = response.isSuccessful
        val result = ApiResponse<T>()
        val convert = ApiService.sConvert
        try {
            val content = response.body.toString()
            when (success) {
                true -> {

                    if (callBack != null) {
                        val parameterizedType =
                            callBack.javaClass.genericSuperclass as ParameterizedType
                        val type = parameterizedType.actualTypeArguments[0]
                        result.body = convert.convert(content, type) as T
                    } else if (mType != null) {
                        val type = mType!!
                        result.body = convert.convert(content, type) as T
                    } else {
                        Log.e(TAG, "类型type为空，无法解析 ")
                    }
                }
                false -> {
                    message = content
                }
            }
        } catch (e: Exception) {
            message = e.message.toString()
            success = false
        }

        result.success = success
        result.message = message
        result.status = status
        return result
    }

    fun execute() {

    }

    fun getCall(): Call {
        val builder = Request.Builder()
        addHeaders(builder)
        val request: Request = generateRequest(builder)
        val call = ApiService.okHttpClient.newCall(request)
        return call
    }

    abstract fun generateRequest(builder: Request.Builder): Request

    private fun addHeaders(builder: Request.Builder) {
        for (entry in headers.entries) {
            builder.addHeader(entry.key, entry.value)
        }
    }

    /**
     * 对于jsonCallback为空的情况下，不能获取到泛型类型，所以必须手动传入
     */
    fun responseType(type: Type): R {
        mType = type
        return this as R
    }

}