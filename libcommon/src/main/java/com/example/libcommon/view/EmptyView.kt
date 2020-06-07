package com.example.libcommon.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.example.libcommon.R
import kotlinx.android.synthetic.main.layout_empty_view.view.*

/**
 * author: created by wentaoKing
 * date: created in 2020-06-03
 * description: 空内容页面
 */
class EmptyView @JvmOverloads constructor(
    @NonNull context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0,
    defStyleRes: Int = 0) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)
    }

    /**
     * 设置空图标
     */
    fun setEmptyIcon(@DrawableRes iconRes: Int) {
        ivEmptyIcon.setImageResource(iconRes)
    }

    /**
     * 设置空内容文本提示
     */
    fun setTitle(text: String) {
        if (TextUtils.isEmpty(text)) {
            tvEmptyText.visibility = View.GONE
        } else {
            tvEmptyText.text = text
            tvEmptyText.visibility = View.VISIBLE
        }
    }

    /**
     * 设置按钮
     */
    fun setButton(text: String, listener: OnClickListener) {
        if (TextUtils.isEmpty(text)) {
            btnEmptyAction.visibility = View.GONE
        } else {
            btnEmptyAction.text = text
            btnEmptyAction.visibility = View.VISIBLE
            btnEmptyAction.setOnClickListener(listener)
        }
    }
}