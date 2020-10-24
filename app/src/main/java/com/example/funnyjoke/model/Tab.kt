package com.example.funnyjoke.model

/**
 * author: created by wentaoKing
 * date: created in 2020-01-15
 * description: 底部导航栏中的按钮
 * @property enable Boolean 若禁用则不显示
 * @property index Int 显示位置下标
 * @property pageUrl String 导航url
 * @property size Int 按钮大小
 * @property title String 显示文本
 * @property tintColor String? 重新着色
 */
data class Tab(
    val enable: Boolean,
    val index: Int,
    val pageUrl: String,
    val size: Int,
    val title: String,
    var tintColor: String? = null
)