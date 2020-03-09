package com.example.libnetwork

/**
 * author: created by wentaoKing
 * date: created in 2020-03-02
 * description:
 */
abstract class JsonCallBack<T> {

    fun onSuccess(respone: ApiResponse<T>){

    }

    fun onError(respone: ApiResponse<T>){

    }

    fun onCacheSuccess(respone: ApiResponse<T>){

    }

}