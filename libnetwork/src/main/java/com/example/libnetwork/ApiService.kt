package com.example.libnetwork

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * author: created by wentaoKing
 * date: created in 2020-03-02
 * description: api service
 */
class ApiService {

    companion object {
        private lateinit var sBaseUrl: String
        private const val TAG = "ApiService"
        var okHttpClient: OkHttpClient
        lateinit var sConvert: Convert<Any>

        init {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY

            okHttpClient = OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .build()

            //http 证书问题
            val trustManagers = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {

                }

                override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(1)
                }
            })

            //ssl
            try {
                val ssl = SSLContext.getInstance("SSL")
                ssl.init(null, trustManagers, SecureRandom())

                HttpsURLConnection.setDefaultSSLSocketFactory(ssl.socketFactory)
                HttpsURLConnection.setDefaultHostnameVerifier { hostName, session ->
                    Log.d(TAG, "hostName is $hostName session is $session")
                    //信任所有的域名证书
                    true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun init(baseUrl: String, convert: Convert<Any>? = null) {
            sBaseUrl = baseUrl
            if (convert == null) {
                sConvert = JsonConvert()
            }
            sConvert = convert!!
        }

         fun <T> get(url: String): GetRequest<T> {
            return GetRequest(sBaseUrl + url)
        }

        fun <T> post(url: String): PostRequest<T> {
            return PostRequest(sBaseUrl + url)
        }

    }

}