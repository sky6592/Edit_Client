package com.doublejj.edit.data.models.splash

import com.google.gson.annotations.SerializedName

data class SplashResult (
    @SerializedName("userRole") val userRole: String,
    @SerializedName("isCertificatedMentor") val isCertificatedMentor: Boolean
)