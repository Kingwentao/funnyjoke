package com.example.libcommon.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable

/**
 * author: created by wentaoKing
 * date: created in 2020-04-21
 * description:
 */
class CornerFrameLayout @JvmOverloads constructor(
    @NonNull context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        ViewHelper.setViewOutline(this, attrs, defStyleAttr, defStyleRes)
    }

    fun setViewOutline(radius: Int, radiusSide: Int) {
        ViewHelper.setViewOutline(this, radius, radiusSide)
    }
}
