package com.example.funnyjoke.ui.home

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.funnyjoke.model.Feed

/**
 * author: created by wentaoKing
 * date: created in 2020-06-07
 * description: feed设配器
 */
class FeedAdapter : PagedListAdapter<Feed, FeedAdapter.ViewHolder> {

    constructor(context: Context, category: String) : super(object : DiffUtil.ItemCallback<Feed>() {
        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //TODO
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }


    inner class ViewHolder : RecyclerView.ViewHolder {

    }
}