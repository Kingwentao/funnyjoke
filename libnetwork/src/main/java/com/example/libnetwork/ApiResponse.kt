package com.example.libnetwork

/**
 * author: created by wentaoKing
 * date: created in 2020-03-09
 * description: api回复请求类
 */
data class ApiResponse<T>(var success: Boolean?=null, var status: Int?=null,
                          var message: String?=null, var body: T?=null)