package com.doublejj.edit.data.models.profile.change_password

import com.google.gson.annotations.SerializedName

data class AuthPasswordRequest(
    @SerializedName("password") val password: String
)
