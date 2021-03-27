package com.doublejj.edit.data.models.splash

import com.google.gson.annotations.SerializedName

data class SplashResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String? = null
)