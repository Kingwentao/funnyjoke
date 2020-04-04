package com.example.funnyjoke.model

import android.text.TextUtils
import androidx.annotation.Nullable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import org.w3c.dom.Comment
import java.io.Serializable


/**
 * author: created by wentaoKing
 * date: created in 2020-03-30
 * description:
 */
class Feed : BaseObservable(), Serializable {
    /**
     * id : 364
     * itemId : 6739143063064549000
     * itemType : 2
     * createTime : 1569079017
     * duration : 299.435
     * feeds_text : 当中国地图出来那一幕，我眼泪都出来了！
     * 太震撼了！
     * authorId : 3223400206308231
     * activityIcon : null
     * activityText : null
     * width : 640
     * height : 368
     * url : https://pipijoke.oss-cn-hangzhou.aliyuncs.com/6739143063064549643.mp4
     * cover :
     */

    var id: Int = 0
    var itemId: Long = 0
    var itemType: Int = 0
    var createTime: Long = 0
    var duration: Double = 0.toDouble()
    var feeds_text: String? = null
    var authorId: Long = 0
    var activityIcon: String? = null
    var activityText: String? = null
    var width: Int = 0
    var height: Int = 0
    var url: String? = null
    var cover: String? = null

    @get:Bindable
    var author: User? = null
    var topComment: Comment? = null

    var ugC: Ugc? = null
    fun getUgc(): Ugc? {
        if (ugC == null) {
            ugC = Ugc()
        }
        return ugC
    }

    override fun equals(@Nullable any: Any?): Boolean {
        if (any == null || any !is Feed)
            return false
        val newFeed = any as Feed?
        return (id == newFeed!!.id
                && itemId == newFeed.itemId
                && itemType == newFeed.itemType
                && createTime == newFeed.createTime
                && duration == newFeed.duration
                && TextUtils.equals(feeds_text, newFeed.feeds_text)
                && authorId == newFeed.authorId
                && TextUtils.equals(activityIcon, newFeed.activityIcon)
                && TextUtils.equals(activityText, newFeed.activityText)
                && width == newFeed.width
                && height == newFeed.height
                && TextUtils.equals(url, newFeed.url)
                && TextUtils.equals(cover, newFeed.cover)
                && author != null && author == newFeed.author
                && topComment != null && topComment!!.equals(newFeed.topComment)
                && ugC != null && ugC == newFeed.ugC)
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + itemId.hashCode()
        result = 31 * result + itemType
        result = 31 * result + createTime.hashCode()
        result = 31 * result + duration.hashCode()
        result = 31 * result + (feeds_text?.hashCode() ?: 0)
        result = 31 * result + authorId.hashCode()
        result = 31 * result + (activityIcon?.hashCode() ?: 0)
        result = 31 * result + (activityText?.hashCode() ?: 0)
        result = 31 * result + width
        result = 31 * result + height
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (cover?.hashCode() ?: 0)
        result = 31 * result + (topComment?.hashCode() ?: 0)
        result = 31 * result + (ugC?.hashCode() ?: 0)
        return result
    }

    companion object {

        val TYPE_IMAGE_TEXT = 1//图文
        val TYPE_VIDEO = 2//视频
    }


}
