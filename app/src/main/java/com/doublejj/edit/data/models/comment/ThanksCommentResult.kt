package com.doublejj.edit.data.models.comment

import com.google.gson.annotations.SerializedName

data class ThanksCommentResult (
    @SerializedName("commentId") val commentId: Long,
    @SerializedName("userInfoId") val userInfoId: Long
)