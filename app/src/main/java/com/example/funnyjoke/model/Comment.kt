package com.example.funnyjoke.model

import androidx.annotation.Nullable
import androidx.databinding.BaseObservable
import java.io.Serializable


/**
 * author: created by wentaoKing
 * date: created in 2020-04-21
 * description: 评论数据
 */

class Comment : BaseObservable(), Serializable {
    /**
     * id : 784
     * itemId : 6739143063064549000
     * commentId : 6739212214408380000
     * userId : 65200808093
     * commentType : 1
     * createTime : 1569095152
     * commentCount : 4454
     * likeCount : 152
     * commentText : 看见没。比甜蜜暴击好看一万倍！
     * imageUrl : null
     * videoUrl : null
     * width : 0
     * height : 0
     * hasLiked : false
     * author : {"id":978,"userId":65200808093,"name":"带鱼裹上面包糠","avatar":"","description":null,"likeCount":0,"topCommentCount":0,"followCount":0,"followerCount":0,"qqOpenId":null,"expires_time":0,"score":0,"historyCount":0,"commentCount":0,"favoriteCount":0,"feedCount":0,"hasFollow":false}
     * ugc2 : {"likeCount":153,"shareCount":0,"commentCount":4454,"hasFavorite":false,"hasLiked":true}
     */

    companion object {
        val COMMENT_TYPE_VIDEO = 3
        val COMMENT_TYPE_IMAGE_TEXT = 2
    }

    var id: Int = 0
    var itemId: Long = 0
    var commentId: Long = 0
    var userId: Long = 0
    var commentType: Int = 0
    var createTime: Long = 0
    var commentCount: Int = 0
    var likeCount: Int = 0
    var commentText: String? = null
    var imageUrl: String? = null
    var videoUrl: String? = null
    var width: Int = 0
    var height: Int = 0
    var hasLiked: Boolean = false
    var author: User? = null
    var ugc2: Ugc? = null

    override fun equals(@Nullable obj: Any?): Boolean {
        if (obj == null || obj !is Comment)
            return false

        val newComment = obj as Comment?
        return (likeCount == newComment!!.likeCount
                && hasLiked == newComment.hasLiked
                && author != null && author == newComment.author
                && ugc2 != null && ugc2 == newComment.ugc2)
    }

    fun getUgc(): Ugc {
        if (ugc2 == null) {
            ugc2 = Ugc()
        }
        return ugc2!!
    }

}