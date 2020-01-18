package com.example.funnyjoke.model

data class BottomBar(
    val activeColor: String,
    val inActiveColor: String,
    val selectTab: Int,
    val tabs: List<Tab>
)