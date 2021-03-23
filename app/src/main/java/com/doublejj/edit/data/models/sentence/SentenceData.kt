package com.doublejj.edit.data.models.sentence

import com.google.gson.annotations.SerializedName

data class SentenceData(
    @SerializedName("userProfile") val userProfile: String,
    @SerializedName("coverLetterId") val coverLetterId: Long,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("jobName") val jobName: String,
    @SerializedName("coverLetterCategoryName") val coverLetterCategoryName: String,
    @SerializedName("coverLetterContent") val coverLetterContent: String,
    @SerializedName("sympathiesCount") var sympathiesCount: Long,
    @SerializedName("sympathy") val sympathy: Boolean,
    @SerializedName("mine") val mine: Boolean
)
