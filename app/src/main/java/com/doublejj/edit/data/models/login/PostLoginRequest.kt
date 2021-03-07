package com.doublejj.edit.data.models.login

import com.google.gson.annotations.SerializedName

data class PostLoginRequest(
    @SerializedName("userEmail") val userEmail: String,
    @SerializedName("userPassword") val userPassword: String
)
