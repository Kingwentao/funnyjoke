package com.example.funnyjoke.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.funnyjoke.databinding.LayoutFeedTypeImageBinding
import com.example.funnyjoke.databinding.LayoutFeedTypeVideoBinding
import com.example.funnyjoke.model.Feed
import java.lang.Exception

/**
 * author: created by wentaoKing
 * date: created in 2020-06-07
 * description: feed设配器：实现对Feed数据绑定
 */
class FeedAdapter : PagedListAdapter<Feed, FeedAdapter.ViewHolder> {

    private var mCategory: String
    private var mInflater: LayoutInflater

    companion object {
        private const val TAG = "FeedAdapter"
    }

    constructor(context: Context, category: String) : super(object : DiffUtil.ItemCallback<Feed>() {
        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            //根据equal比较内容是否相等
            return oldItem == newItem
        }
    }) {
        mInflater = LayoutInflater.from(context)
        mCategory = category
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ViewDataBinding = when (viewType) {
            Feed.TYPE_IMAGE_TEXT -> {
                LayoutFeedTypeImageBinding.inflate(mInflater)
            }
            Feed.TYPE_VIDEO -> {
                LayoutFeedTypeVideoBinding.inflate(mInflater)
            }
            else -> {
                throw Exception("not exist the type!")
            }
        }
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemViewType(position: Int): Int {
        val feed = getItem(position)
        if (feed != null) {
            return feed.itemType
        }
        return -1
    }

    inner class ViewHolder(private val view: View, private val mBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(view) {

        fun bindData(item: Feed) {
            when (mBinding) {
                is LayoutFeedTypeImageBinding -> {
                    mBinding.feed = item
                    mBinding.feedImage.bindData(
                        item.width, item.height, 16,
                        16, imageUrl = item.cover
                    )

                }
                is LayoutFeedTypeVideoBinding -> {
                    mBinding.feed = item
                    mBinding.listPlayerView.bindData(
                        mCategory,
                        item.width, item.height, item.cover, item.url
                    )
                }
                else -> {
                    throw Exception("bind type is not exist!")
                }

            }

        }

    }

}