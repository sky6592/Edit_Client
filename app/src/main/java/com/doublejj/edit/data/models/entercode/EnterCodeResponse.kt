package com.doublejj.edit.data.models.entercode

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class EnterCodeResponse(
    @SerializedName("result") val result: String? = null
) : BaseResponse()