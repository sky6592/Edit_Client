package com.doublejj.edit.data.models.my_sentence_completed

import com.google.gson.annotations.SerializedName

data class MySentenceCompletedResult(
    @SerializedName("coverLetterId") val coverLetterId: Long,
    @SerializedName("userProfile") val userProfile: String,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("jobName") val jobName: String,
    @SerializedName("coverLetterCategoryName") val coverLetterCategoryName: String,
    @SerializedName("coverLetterContent") val coverLetterContent: String,
    @SerializedName("completedCoverLetterContent") val completedCoverLetterContent: String
)