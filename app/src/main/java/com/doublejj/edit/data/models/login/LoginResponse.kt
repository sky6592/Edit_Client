package com.doublejj.edit.data.models.login

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("jwt") val jwt: String,
    @SerializedName("userNickNameIdx") val userNickNameIdx: Int,
    @SerializedName("userProfilePicture") val userProfilePicture: String?
) : BaseResponse()