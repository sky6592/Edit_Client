package com.doublejj.edit.data.models.emailfind

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.login.LogInResult
import com.google.gson.annotations.SerializedName

data class EmailFindResponse(
    @SerializedName("result") val result: EmailFindResult
) : BaseResponse()