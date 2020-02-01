package com.example.funnyjoke.model


/**
 * author: created by wentaoKing
 * date: created in 2020-01-15
 * description: 底部导航栏信息
 */

data class BottomBar(
    val activeColor: String,
    val inActiveColor: String,
    val selectTab: Int,
    val tabs: List<Tab>
)