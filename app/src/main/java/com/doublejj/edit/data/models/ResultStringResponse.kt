package com.doublejj.edit.data.models

import com.google.gson.annotations.SerializedName

data class ResultStringResponse (
    @SerializedName("result") val result: String
) : BaseResponse()