package com.doublejj.edit.data.models

import com.google.gson.annotations.SerializedName

data class ResultResponse (
    @SerializedName("result") val result: Long
) : BaseResponse()