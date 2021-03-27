package com.doublejj.edit.data.models.emailfind

import com.google.gson.annotations.SerializedName

data class EmailFindRequest(
    @SerializedName("name") val name: String,
    @SerializedName("phoneNumber") val phoneNumber: String
)