package com.doublejj.edit.data.models.login

import com.google.gson.annotations.SerializedName

data class LogInResult(
    @SerializedName("jwt") val jwt: String,
    @SerializedName("userRole") val userRole: String
)