package com.doublejj.edit.data.models.sentence

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class SympathizeSentenceResponse(
    @SerializedName("coverLetterId") val coverLetterId: Long,
    @SerializedName("userInfoId") val userInfoId: Long
) : BaseResponse()
