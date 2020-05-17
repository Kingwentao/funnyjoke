package com.example.funnyjoke.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.funnyjoke.R
import com.example.libcommon.utils.PixUtils
import kotlinx.android.synthetic.main.layout_player_view.view.*

/**
 * author: created by wentaoKing
 * date: created in 2020-04-27
 * description: 视频播放列表view
 */
class ListPlayerView(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) :
    ImageView(context, attributeSet, defStyleAttr) {


    private var mVideoUrl: String? = null
    private var mCategory: String? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_player_view, null)
    }

    fun bindData(
        category: String, widthPx: Int,
        heightPx: Int, coverUrl: String, videoUrl: String
    ) {

        mCategory = category
        mVideoUrl = videoUrl

        cover.setImageUrl(coverUrl)
        //如果该视频的宽度小于高度,则高斯模糊背景图显示出来
        if (widthPx < heightPx){
            blur_background.setBlurImageUrl(cover,coverUrl,10)
            blur_background.visibility = View.VISIBLE
        }else{
            blur_background.visibility = View.INVISIBLE
        }
        setSize(widthPx,heightPx)
    }

    private fun setSize(widthPx: Int, heightPx: Int) {
        //视频宽大与高,或者高大于宽时,对视频进行等比缩放
        val maxWidth = PixUtils.getScreenWidth()
        val maxHeight = maxWidth

        val layoutWidth = maxWidth
        var layoutHeight = 0

        val coverWidth: Int
        val coverHeight: Int

        if (widthPx >= heightPx) {
            coverWidth = maxWidth
            coverHeight = (heightPx / (widthPx * 1.0f / maxWidth)).toInt()
            layoutHeight = coverHeight
        } else {
            coverHeight = maxHeight
            layoutHeight = coverHeight
            coverWidth = (widthPx / (heightPx * 1.0f / maxHeight)).toInt()
        }

        val params =  layoutParams
        params.width = layoutWidth
        params.height = layoutHeight
        layoutParams = params

        val blurParams =  blur_background.layoutParams
        blurParams.width = layoutWidth
        blurParams.height = layoutHeight
        layoutParams = blurParams


        val coverParams: FrameLayout.LayoutParams =  cover.layoutParams as FrameLayout.LayoutParams
        coverParams.width = coverWidth
        coverParams.height = coverHeight
        coverParams.gravity = Gravity.CENTER
        layoutParams = coverParams

        val playBtnParams = btnPlay.layoutParams as FrameLayout.LayoutParams
        playBtnParams.gravity = Gravity.CENTER
        btnPlay.layoutParams = playBtnParams
    }



}