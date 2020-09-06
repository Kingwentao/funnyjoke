package com.example.funnyjoke.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.alibaba.fastjson.TypeReference
import com.example.funnyjoke.AbsViewModel
import com.example.funnyjoke.model.Feed
import com.example.libnetwork.ApiResponse
import com.example.libnetwork.ApiService
import java.util.*

class HomeViewModel : AbsViewModel<Feed>() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    private val mFeedType: String? = null

    @Volatile
    private var witchCache = true

    override fun createDataSource(): DataSource{
        return FeedDataSource()
    }

    inner class FeedDataSource: ItemKeyedDataSource<Int,Feed>(){
        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Feed>) {

            loadData(0,params.requestedLoadSize,callback)
            witchCache = false
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Feed>) {
            //向后加载分页数据的
            Log.e("homeviewmodel", "loadAfter: ")
            loadData(params.key, params.requestedLoadSize, callback)
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Feed>) {
            callback.onResult(emptyList())
            //能够向前加载数据的
        }

        override fun getKey(item: Feed): Int {
            return item.id
        }

    }

    private fun loadData(key: Int, count: Int, callback: ItemKeyedDataSource.LoadCallback<Feed>
    ) {

        //feeds/queryHotFeedsList
        val request = ApiService.get<List<Feed>>("/feeds/queryHotFeedsList")
            .addParams("feedType", mFeedType)
            .addParams("userId", 0)
            .addParams("feedId", key)
            .addParams("pageCount", count)
            .responseType(object :
                TypeReference<ArrayList<Feed?>?>() {}.type)

        try {
            val netRequest = if (witchCache) request.clone() else request
            val response: ApiResponse<List<Feed>>? = netRequest.execute()
            val data =
                if (response?.body == null) emptyList<Feed>() else response.body

            if (data != null){
                callback.onResult(data.toMutableList())
            }

            if (key > 0) {
                //通过BoundaryPageData发送数据 告诉UI层 是否应该主动关闭上拉加载分页的动画
                (getBoundaryPageData() as MutableLiveData<*>).postValue(data.size > 0)

            }
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        Log.e("loadData", "loadData: key:$key")
    }

}