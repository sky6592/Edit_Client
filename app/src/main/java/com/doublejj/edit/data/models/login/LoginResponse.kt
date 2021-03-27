package com.doublejj.edit.data.models.login

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.infofirst.InfoFirstResult
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("result") val result: LogInResult
) : BaseResponse()