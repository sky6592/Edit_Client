package com.doublejj.edit.data.models.sentence

import com.google.gson.annotations.SerializedName

data class SympathizeSentenceResult(
    @SerializedName("coverLetterId") val coverLetterId: Long,
    @SerializedName("userInfoId") val userInfoId: Long
)
