package com.doublejj.edit.data.models.infofirst

import com.google.gson.annotations.SerializedName

data class InfoFirstRequest(
    @SerializedName("nickName") val nickName: String
)