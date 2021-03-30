package com.doublejj.edit.data.models.read

import com.google.gson.annotations.SerializedName

data class ReadRequest(
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickName: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("authenticationPassword") val authenticationPassword: String,
    @SerializedName("userRole") val userRole: String,
    @SerializedName("jobName") val jobName: String,
    @SerializedName("etcJobName") val etcJobName: String,
)