package com.doublejj.edit.data.models.switch_position

import com.google.gson.annotations.SerializedName

data class SwitchPositionRequest(
    @SerializedName("changeContent") val changeContent: String,
    @SerializedName("etcChangeContent") val etcChangeContent: String
)
