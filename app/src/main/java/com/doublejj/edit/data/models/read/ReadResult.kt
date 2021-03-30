package com.doublejj.edit.data.models.read

import com.google.gson.annotations.SerializedName

data class ReadResult(
    @SerializedName("jwt") val jwt: String,
    @SerializedName("userRole") val userRole: String,
)