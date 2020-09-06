package com.example.funnyjoke

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

/**
 * author: created by wentaoKing
 * date: created in 2020/7/19
 * description: 公共viewModel抽象层
 */
abstract class AbsViewModel<T> : ViewModel() {

    private val config: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(10)
        .setInitialLoadSizeHint(12)
        // .setMaxSize(100)；
        // .setEnablePlaceholders(false)
        // .setPrefetchDistance()
        .build()
    private var dataSource: DataSource<Any, T>? = null
    private val mPageData: LiveData<PagedList<T>>
    private val boundaryPageData = MutableLiveData<Boolean>()

    private val mFactory = object : DataSource.Factory<Any, T>() {
        override fun create(): DataSource<Any, T> {
            if (dataSource == null || dataSource!!.isInvalid) {
                dataSource = createDataSource()
            }
            return dataSource!!
        }
    }

    private val mCallBack = object : PagedList.BoundaryCallback<T>() {
        override fun onZeroItemsLoaded() {
            //新提交的PagedList中没有数据
            boundaryPageData.postValue(false)
        }

        override fun onItemAtFrontLoaded(itemAtFront: T) {
            //新提交的PagedList中第一条数据被加载到列表上
            boundaryPageData.postValue(true)
        }

        override fun onItemAtEndLoaded(itemAtEnd: T) {
            //新提交的PagedList中最后一条数据被加载到列表上
        }
    }

    init {
        mPageData = LivePagedListBuilder(mFactory, config)
            .setBoundaryCallback(mCallBack)
            .build()
    }

    open fun getPageData(): LiveData<PagedList<T>>? {
        return mPageData
    }

    open fun getDataSource(): DataSource<*, *>? {
        return dataSource
    }

    open fun getBoundaryPageData(): LiveData<Boolean?>? {
        return boundaryPageData
    }

    abstract fun createDataSource(): DataSource<Any,T>
}