package com.example.libcommon.view

import android.annotation.TargetApi
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import com.example.libcommon.R


/**
 * author: created by wentaoKing
 * date: created in 2020-04-21
 * description:
 */
object ViewHelper {

    private const val RADIUS_ALL = 0
    private const val RADIUS_LEFT = 1
    private const val RADIUS_TOP = 2
    private const val RADIUS_RIGHT = 3
    private const val RADIUS_BOTTOM = 4

    fun setViewOutline(view: View, attributes: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val array = view.context.obtainStyledAttributes(
            attributes,
            R.styleable.viewOutLineStrategy,
            defStyleAttr,
            defStyleRes
        )
        val radius = array.getDimensionPixelSize(R.styleable.viewOutLineStrategy_clip_radius, 0)
        val hideSide = array.getInt(R.styleable.viewOutLineStrategy_clip_side, 0)
        array.recycle()
        setViewOutline(view, radius, hideSide)
    }

    fun setViewOutline(owner: View, radius: Int, radiusSide: Int) {
        owner.outlineProvider = object : ViewOutlineProvider() {
            @TargetApi(21)
            override fun getOutline(view: View, outline: Outline) {
                val w = view.width
                val h = view.height
                if (w == 0 || h == 0) {
                    return
                }

                if (radiusSide != RADIUS_ALL) {
                    var left = 0
                    var top = 0
                    var right = w
                    var bottom = h
                    when (radiusSide) {
                        RADIUS_LEFT -> {
                            right += radius
                        }
                        RADIUS_TOP -> {
                            bottom += radius
                        }
                        RADIUS_RIGHT -> {
                            left -= radius
                        }
                        RADIUS_BOTTOM -> {
                            top -= radius
                        }
                    }
                    outline.setRoundRect(left, top, right, bottom, radius.toFloat())
                    return
                }

                val top = 0
                val left = 0
                if (radius <= 0) {
                    outline.setRect(left, top, w, h)
                } else {
                    outline.setRoundRect(left, top, w, h, radius.toFloat())
                }
            }
        }
        owner.clipToOutline = radius > 0
        owner.invalidate()
    }
}