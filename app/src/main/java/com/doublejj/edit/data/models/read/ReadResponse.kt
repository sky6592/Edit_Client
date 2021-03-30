package com.doublejj.edit.data.models.read

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class ReadResponse(
    @SerializedName("result") val result: ReadResult,
) : BaseResponse()