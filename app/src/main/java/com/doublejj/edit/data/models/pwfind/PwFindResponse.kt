package com.doublejj.edit.data.models.pwfind

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.infofirst.InfoFirstResult
import com.google.gson.annotations.SerializedName

data class PwFindResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String? = null
)