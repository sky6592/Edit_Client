package com.doublejj.edit.data.models.pwfind

import com.google.gson.annotations.SerializedName

data class PwFindRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phoneNumber") val phoneNumber: String
)