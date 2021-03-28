package com.doublejj.edit.data.models.splash

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class SplashResponse(
    @SerializedName("result") val result: SplashResult
) : BaseResponse()