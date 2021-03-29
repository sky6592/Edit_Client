package com.doublejj.edit.data.models.sentence

import com.google.gson.annotations.SerializedName

data class SentenceData(
    @SerializedName("coverLetterId") val coverLetterId: Long,
    @SerializedName("userProfile") val userProfile: String,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("jobName") val jobName: String,
    @SerializedName("coverLetterCategoryName") val coverLetterCategoryName: String,
    @SerializedName("coverLetterContent") val coverLetterContent: String,
    @SerializedName("completedCoverLetterContent") val completedCoverLetterContent: String,
    @SerializedName("sympathiesCount") var sympathiesCount: Long,
    @SerializedName("isSympathy") var isSympathy: Boolean,
    @SerializedName("isMine") val isMine: Boolean
)
