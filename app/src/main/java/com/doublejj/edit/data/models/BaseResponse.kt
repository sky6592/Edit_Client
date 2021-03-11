package com.doublejj.edit.data.models

import com.google.gson.annotations.SerializedName


// 반복되는 response 내용들 모아서 관리 (상속해서 사용)
open class BaseResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String? = null
)