package com.example.funnyjoke.model

/**
 * author: created by wentaoKing
 * date: created in 2020-01-15
 * description: 页面跳转目的地的信息
 */

data class Destination(
    val asStarter: Boolean,
    val className: String,
    val id: Int,
    val isFragment: Boolean,
    val needLogin: Boolean,
    val pageUrl: String
)