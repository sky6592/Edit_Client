package com.doublejj.edit.data.models.infofirst

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class InfoFirstResponse(
    @SerializedName("result") val result: InfoFirstResult
) : BaseResponse()