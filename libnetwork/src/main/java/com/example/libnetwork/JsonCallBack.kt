package com.example.libnetwork

/**
 * author: created by wentaoKing
 * date: created in 2020-03-02
 * description: json的转换
 */
abstract class JsonCallBack<T> {

    open fun onSuccess(response: ApiResponse<T>){

    }

    open fun onError(response: ApiResponse<T>){

    }

    open fun onCacheSuccess(response: ApiResponse<T>){

    }

}