package com.example.funnyjoke

import android.app.Application
import com.example.libnetwork.ApiService

/**
 * author: created by wentaoKing
 * date: created in 2020/9/6
 * description: app of application
 */
class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ApiService.init("http://123.56.232.18:8080/serverdemo", null)
    }
}