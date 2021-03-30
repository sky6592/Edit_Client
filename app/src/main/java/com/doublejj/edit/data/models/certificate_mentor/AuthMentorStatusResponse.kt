package com.doublejj.edit.data.models.certificate_mentor

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class AuthMentorStatusResponse (
    @SerializedName("result") val result: AuthMentorStatusResult
) : BaseResponse()