package com.example.libnetwork

import android.annotation.SuppressLint
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import android.text.TextUtils
import androidx.arch.core.executor.ArchTaskExecutor
import com.example.libnetwork.cache.CacheManager


/**
 * author: created by wentaoKing
 * date: created in 2020-03-02
 * description:
 */
abstract class Request<T, R> : Cloneable{

    private var mType: Type? = null
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

    private var mCacheStrategy = NET_ONLY


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

    @SuppressLint("RestrictedApi")
    fun execute(jsonCallBack: JsonCallBack<T>) {

        //加入缓存策略: 如果不只是通过网络
        if(mCacheStrategy != NET_ONLY){
            ArchTaskExecutor.getIOThreadExecutor().execute {
                val response = readCache()
                if (jsonCallBack != null && response?.body != null){
                    jsonCallBack.onCacheSuccess(response)
                }
            }
        }

        if (mCacheStrategy != CACHE_ONLY){

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

    fun execute(): ApiResponse<T>? {

        if (mType == null) throw RuntimeException("同步方法,response 返回值 类型必须设置")

        if (mCacheStrategy == CACHE_ONLY) {
            return readCache()
        }

        if (mCacheStrategy != CACHE_ONLY) {
            var result: ApiResponse<T>? = null
            try {
                val response = getCall().execute()
                result = parseResponse(response)
            } catch (e: IOException) {
                e.printStackTrace()
                if (result == null) {
                    result = ApiResponse()
                    result.message = e.message
                }
                return result
            }
        }
        return null
    }

    /**
     *  读取缓存
     */
    private fun readCache(): ApiResponse<T>? {
        val key = if (TextUtils.isEmpty(cacheKey)) {
            generateCacheKey()
        } else {
            cacheKey!!
        }

        val cache = CacheManager.getCache(key)
        val status = 304
        val message = "缓存获取成功"
        val body = cache as T
        val success = true
        return  ApiResponse(success,status,message,body)
    }


    /**
     * 生成缓存的key
     */
    private fun generateCacheKey(): String {
        val key  = UrlCreator.createUrlFromParams(mUrl, params)
        cacheKey = key
        return key
    }

    fun getCall(): Call {
        val builder = okhttp3.Request.Builder()
        addHeaders(builder)
        val request: okhttp3.Request = generateRequest(builder)
        val call = ApiService.okHttpClient.newCall(request)
        return call
    }

    abstract fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request

    private fun addHeaders(builder: okhttp3.Request.Builder) {
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

    override fun clone(): Request<T,R> {
        return super.clone() as (Request<T,R>)
    }
}