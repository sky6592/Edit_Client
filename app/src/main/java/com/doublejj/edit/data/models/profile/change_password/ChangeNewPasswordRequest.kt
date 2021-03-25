package com.doublejj.edit.data.models.profile.change_password

import com.google.gson.annotations.SerializedName

data class ChangeNewPasswordRequest(
    @SerializedName("password") val password: String,
    @SerializedName("authenticationPassword") val authenticationPassword: String
)
