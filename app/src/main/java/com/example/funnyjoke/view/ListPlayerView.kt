package com.example.funnyjoke.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import com.example.funnyjoke.R
import kotlinx.android.synthetic.main.layout_player_view.view.*

/**
 * author: created by wentaoKing
 * date: created in 2020-04-27
 * description: 视频播放列表view
 */
class ListPlayerView (context: Context, attributeSet: AttributeSet, defStyleAttr: Int) :
    ImageView(context, attributeSet, defStyleAttr){

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_player_view,null)
    }
}