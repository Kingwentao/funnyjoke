package com.example.funnyjoke.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.funnyjoke.databinding.LayoutRefreshViewBinding
import com.example.libcommon.view.EmptyView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

/**
 * author: created by wentaoKing
 * date: created in 2020-06-07
 * description: 列表的基类
 */
abstract class AbsListFragment<T> : Fragment(), OnRefreshListener, OnLoadMoreListener {

    //如何实现绑定的？？？
    private lateinit var mBinding: LayoutRefreshViewBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRefreshLayout: RefreshLayout
    private lateinit var mEmptyView: EmptyView
    private lateinit var mAdapter: PagedListAdapter<T, RecyclerView.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = LayoutRefreshViewBinding.inflate(inflater, container, false)
        mRecyclerView = mBinding.recyclerView
        mRefreshLayout = mBinding.refreshLayout
        mEmptyView = mBinding.emptyView

        mRefreshLayout.setOnLoadMoreListener(this)
        mRefreshLayout.setOnRefreshListener(this)
        mRefreshLayout.setEnableLoadMore(true)
        mRefreshLayout.setEnableRefresh(true)

        mAdapter = getAdapter()
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.itemAnimator = null

        return mBinding.root
    }

    abstract fun getAdapter(): PagedListAdapter<T, RecyclerView.ViewHolder>

    /**
     * 完成数据刷新
     */
    private fun finishRefresh(hasData: Boolean) {
        val currentList = mAdapter.currentList
        val isHasData = hasData || currentList == null || currentList.size > 0
        val state = mRefreshLayout.state
        if (state.isFooter && state.isOpening) {
            mRefreshLayout.finishLoadMore()
        } else if (state.isHeader && state.isOpening) {
            mRefreshLayout.finishRefresh()
        }

        if (isHasData) {
            mEmptyView.visibility = View.GONE
        } else {
            mEmptyView.visibility = View.VISIBLE
        }
    }

    /**
     * 发送数据
     */
    fun submitList(pageList: PagedList<T>) {
        if (pageList.size > 0) {
            mAdapter.submitList(pageList)
        }
        finishRefresh(pageList.size > 0)
    }
}