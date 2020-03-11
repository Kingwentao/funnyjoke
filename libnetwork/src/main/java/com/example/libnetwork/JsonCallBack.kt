package com.example.libnetwork

/**
 * author: created by wentaoKing
 * date: created in 2020-03-02
 * description:
 */
abstract class JsonCallBack<T> {

    open fun onSuccess(respone: ApiResponse<T>){

    }

    open fun onError(respone: ApiResponse<T>){

    }

    open fun onCacheSuccess(respone: ApiResponse<T>){

    }

}