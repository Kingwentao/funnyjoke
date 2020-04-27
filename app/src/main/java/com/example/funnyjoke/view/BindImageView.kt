package com.example.funnyjoke.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.libcommon.utils.PixUtils
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * author: created by wentaoKing
 * date: created in 2020-03-30
 * description:
 */
class BindImageView(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) :
    ImageView(context, attributeSet, defStyleAttr) {

    @BindingAdapter(value = ["image_url", "isCircle", "radius"], requireAll = false)
    fun setImageUrl(view: BindImageView, imageUrl: String, isCircle: Boolean, radius: Int) {

        val builder = Glide.with(view).load(imageUrl)
        if (isCircle) {
            builder.transform(CircleCrop())
        } else if (radius > 0) {
            builder.transform(RoundedCornersTransformation(PixUtils.dp2px(radius), 0))
        }
        val layoutParams = view.layoutParams
        if (layoutParams != null && layoutParams.width > 0 && layoutParams.height > 0) {
            builder.override(layoutParams.width, layoutParams.height)
        }
        builder.into(view)
    }


    fun bindData(
        widthPx: Int, heightPx: Int, marginLeft: Int, marginWidth: Int,
        maxWidth: Int = PixUtils.getScreenWidth(), maxHeight: Int = PixUtils.getScreenHeight(),
        imageUrl: String
    ) {
        if (widthPx <= 0 || heightPx <= 0) {
            Glide.with(this).load(imageUrl).into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    val width = resource.intrinsicHeight
                    val height = resource.intrinsicHeight
                    setSize(width, height, marginLeft, marginWidth, maxWidth, maxHeight)
                    setImageDrawable(resource)
                }
            })
            return
        }

        setSize(width, height, marginLeft, marginWidth, maxWidth, maxHeight)
        setImageUrl(this, imageUrl, false,0)

    }

    private fun setSize(width: Int, height: Int, marginLeft: Int, marginWidth: Any,
        maxWidth: Int, maxHeight: Int) {

        val finalWidth: Int
        val finalHeight: Int
        if (width > height) {
            finalWidth = maxWidth
            finalHeight = height / (width / finalWidth)
        } else {
            finalHeight = maxHeight
            finalWidth = width / (height / finalHeight)
        }
        val params = ViewGroup.MarginLayoutParams(finalWidth, finalHeight)
        params.leftMargin = if (height > width) PixUtils.dp2px(marginLeft) else 0
        layoutParams = params
    }


}