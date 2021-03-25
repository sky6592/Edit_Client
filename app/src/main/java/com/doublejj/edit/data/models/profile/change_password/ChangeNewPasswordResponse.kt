package com.doublejj.edit.data.models.profile.change_password

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class ChangeNewPasswordResponse(
    @SerializedName("result") val result: NickNameResult
) : BaseResponse()

data class NickNameResult(
    @SerializedName("nickName") val nickName: String
)