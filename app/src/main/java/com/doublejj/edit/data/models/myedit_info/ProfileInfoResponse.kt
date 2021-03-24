package com.doublejj.edit.data.models.myedit_info

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class ProfileInfoResponse (
    @SerializedName("result") val result: ProfileInfo
) : BaseResponse()

data class ProfileInfo(
    @SerializedName("nickName") val nickName: String,
    @SerializedName("emotionName") val emotionName: String,
    @SerializedName("colorName") val colorName: String,
    @SerializedName("userRole") val userRole: String
)