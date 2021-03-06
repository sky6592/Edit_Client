package com.doublejj.edit.data.models.comment

import com.google.gson.annotations.SerializedName

data class CommentData(
    @SerializedName("commentId") val commentId: Long,
    @SerializedName("userProfile") val userProfile: String,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("jobName") val jobName: String,
    @SerializedName("sentenceEvaluation") val sentenceEvaluation: String,
    @SerializedName("concretenessLogic") val concretenessLogic: String,
    @SerializedName("sincerity") var sincerity: String,
    @SerializedName("activity") var activity: String,
    @SerializedName("commentContent") var commentContent: String,
    @SerializedName("isAdopted") var isAdopted: String,
    @SerializedName("isAppreciated") var isAppreciated: Boolean,
    @SerializedName("isMine") var isMine: Boolean
)