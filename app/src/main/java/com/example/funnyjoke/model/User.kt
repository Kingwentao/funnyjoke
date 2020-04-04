package com.example.funnyjoke.model

import android.text.TextUtils
import androidx.annotation.Nullable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.io.Serializable


/**
 * author: created by wentaoKing
 * date: created in 2020-03-27
 * description: 用户类
 */
class User : BaseObservable(), Serializable {

    /**
     * id : 962
     * userId : 3223400206308231
     * name : 二师弟请随我来
     * avatar :
     * description :
     * likeCount : 0
     * topCommentCount : 0
     * followCount : 0
     * followerCount : 0
     * qqOpenId : null
     * expires_time : 0
     * score : 0
     * historyCount : 0
     * commentCount : 0
     * favoriteCount : 0
     * feedCount : 0
     * hasFollows : false
     */

    var id: Int = 0
    var userId: Long = 0
    var name: String? = null
    var avatar: String? = null
    var description: String? = null
    var likeCount: Int = 0
    var topCommentCount: Int = 0
    var followCount: Int = 0
    var followerCount: Int = 0
    var qqOpenId: String? = null
    var expires_time: Long = 0
    var score: Int = 0
    var historyCount: Int = 0
    var commentCount: Int = 0
    var favoriteCount: Int = 0
    var feedCount: Int = 0
    var hasFollows: Boolean = false

    var isHasFollow: Boolean
        @Bindable
        get() = hasFollows
        set(hasFollow) {
            this.hasFollows = hasFollow
            notifyPropertyChanged(com.example.funnyjoke.BR._all)
        }

    override fun equals(@Nullable obj: Any?): Boolean {
        if (obj == null || obj !is User)
            return false
        val newUser = obj as User?
        return (TextUtils.equals(name, newUser!!.name)
                && TextUtils.equals(avatar, newUser.avatar)
                && TextUtils.equals(description, newUser.description)
                && likeCount == newUser.likeCount
                && topCommentCount == newUser.topCommentCount
                && followCount == newUser.followCount
                && followerCount == newUser.followerCount
                && qqOpenId === newUser.qqOpenId
                && expires_time == newUser.expires_time
                && score == newUser.score
                && historyCount == newUser.historyCount
                && commentCount == newUser.commentCount
                && favoriteCount == newUser.favoriteCount
                && feedCount == newUser.feedCount
                && hasFollows == newUser.hasFollows)
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (avatar?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + likeCount
        result = 31 * result + topCommentCount
        result = 31 * result + followCount
        result = 31 * result + followerCount
        result = 31 * result + (qqOpenId?.hashCode() ?: 0)
        result = 31 * result + expires_time.hashCode()
        result = 31 * result + score
        result = 31 * result + historyCount
        result = 31 * result + commentCount
        result = 31 * result + favoriteCount
        result = 31 * result + feedCount
        result = 31 * result + hasFollows.hashCode()
        return result
    }
}