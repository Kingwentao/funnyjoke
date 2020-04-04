package com.example.funnyjoke.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
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

}