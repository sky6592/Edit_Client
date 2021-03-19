package com.doublejj.edit.data.models.infofirst

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class InfoFirstResponse(
//    @SerializedName("isSuccess") val isSuccess: Boolean = false,
//    @SerializedName("code") val code: Int = 0,
//    @SerializedName("message") val message: String? = null,
    @SerializedName("result") val result: InfoFirstResult
) : BaseResponse()