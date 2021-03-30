package com.doublejj.edit.data.models.writing_comment

import com.google.gson.annotations.SerializedName

data class WritingCommentRequest (
    @SerializedName("sentenceEvaluation") var sentenceEvaluation: String,
    @SerializedName("activity") var activity: String,
    @SerializedName("sincerity") var sincerity: String,
    @SerializedName("concretenessLogic") var concretenessLogic: String,
    @SerializedName("content") var content: String,
    @SerializedName("coverLetterId") var coverLetterId: Long
)