package com.example.libnetwork

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.Exception
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * author: created by wentaoKing
 * date: created in 2020-03-02
 * description: api service
 */
class ApiService {


    companion object {

        private const val TAG = "ApiService"
        var okHttpClient: OkHttpClient

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
    }


}