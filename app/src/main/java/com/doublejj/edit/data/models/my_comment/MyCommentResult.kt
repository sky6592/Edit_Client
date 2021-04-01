package com.doublejj.edit.data.models.my_comment

import com.google.gson.annotations.SerializedName

data class MyCommentResult(
    @SerializedName("commentInfo") val commentInfo: CommentInfoResult,
    @SerializedName("userInfo") val userInfo: UserInfoResult
)

data class CommentInfoResult(
    @SerializedName("commentId") val commentId: Long,
    @SerializedName("sentenceEvaluation") val sentenceEvaluation: String,
    @SerializedName("activity") val activity: String,
    @SerializedName("sincerity") val sincerity: String,
    @SerializedName("concretenessLogic") val concretenessLogic: String,
    @SerializedName("commentContent") val commentContent: String
)

data class UserInfoResult(
    @SerializedName("nickName") val nickName: String,
    @SerializedName("emotionName") val emotionName: String,
    @SerializedName("colorName") val colorName: String,
    @SerializedName("userRole") val userRole: String,
    @SerializedName("jobName") val jobName: String
)
