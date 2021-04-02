package com.doublejj.edit.data.models.my_comment

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class MyCommentResponse(
    @SerializedName("result") val result: MutableList<MyCommentResult>
) : BaseResponse()