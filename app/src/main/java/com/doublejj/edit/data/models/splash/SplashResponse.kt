package com.doublejj.edit.data.models.splash

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.login.LogInResult
import com.google.gson.annotations.SerializedName

data class SplashResponse(
    @SerializedName("result") val result: SplashResult
) :BaseResponse()