package com.example.funnyjoke.model

import androidx.annotation.Nullable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.io.Serializable


/**
 * author: created by wentaoKing
 * date: created in 2020-03-30
 * description:
 */
class Ugc : BaseObservable(), Serializable {
    /**
     * likeCount : 153
     * shareCounts : 0
     * commentCount : 4454
     * hasFavorites : false
     * hasLike : true
     * hasDiss:false
     */

    var likeCount: Int = 0

    var shareCounts: Int = 0
    var commentCount: Int = 0
    var hasFavorites: Boolean = false
    var hasDiss: Boolean = false

    var isHasdiss: Boolean
        @Bindable
        get() = hasDiss
        set(hasdiss) {
            if (this.hasDiss == hasdiss)
                return
            if (hasdiss) {
                isHasLiked = false
            }
            this.hasDiss = hasdiss
            notifyPropertyChanged(com.example.funnyjoke.BR._all)
        }

    var hasLike: Boolean = false

    var isHasLiked: Boolean
        @Bindable
        get() = hasLike
        set(hasLiked) {
            if (this.hasLike == hasLiked)
                return
            if (hasLiked) {
                likeCount += 1
                isHasdiss = false
            } else {
                likeCount -= 1
            }
            this.hasLike = hasLiked
            notifyPropertyChanged(com.example.funnyjoke.BR._all)
        }

    var isHasFavorite: Boolean
        @Bindable
        get() = hasFavorites
        set(hasFavorite) {
            this.hasFavorites = hasFavorite
            notifyPropertyChanged(com.example.funnyjoke.BR._all)
        }

    @Bindable
    fun getShareCount(): Int {
        return shareCounts
    }

    fun setShareCount(shareCount: Int) {
        this.shareCounts = shareCount
        notifyPropertyChanged(com.example.funnyjoke.BR._all)
    }


    override fun equals(@Nullable obj: Any?): Boolean {
        if (obj == null || obj !is Ugc)
            return false
        val newUgc = obj as Ugc?
        return (likeCount == newUgc!!.likeCount
                && shareCounts == newUgc.shareCounts
                && commentCount == newUgc.commentCount
                && hasFavorites == newUgc.hasFavorites
                && hasLike == newUgc.hasLike
                && hasDiss == newUgc.hasDiss)
    }

    override fun hashCode(): Int {
        var result = likeCount
        result = 31 * result + shareCounts
        result = 31 * result + commentCount
        result = 31 * result + hasFavorites.hashCode()
        result = 31 * result + hasDiss.hashCode()
        result = 31 * result + hasLike.hashCode()
        return result
    }
}