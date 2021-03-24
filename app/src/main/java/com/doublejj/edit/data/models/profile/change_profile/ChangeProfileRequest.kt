package com.doublejj.edit.data.models.profile.change_profile

import com.google.gson.annotations.SerializedName

data class ChangeProfileRequest(
    @SerializedName("colorName") val colorName: String,
    @SerializedName("emotionName") val emotionName: String
)
