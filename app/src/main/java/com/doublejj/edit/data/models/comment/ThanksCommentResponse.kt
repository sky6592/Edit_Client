package com.doublejj.edit.data.models.comment

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class ThanksCommentResponse (
    @SerializedName("result") val result: ThanksCommentResult
) : BaseResponse()