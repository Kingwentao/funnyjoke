package com.example.funnyjoke.model

/**
 * author: created by wentaoKing
 * date: created in 2020-01-15
 * description: 底部导航栏中个体的信息
 */

data class Tab(
    val enable: Boolean,
    val index: Int,
    val pageUrl: String,
    val size: Int,
    val title: String,
    var tintColor: String ?= null
)