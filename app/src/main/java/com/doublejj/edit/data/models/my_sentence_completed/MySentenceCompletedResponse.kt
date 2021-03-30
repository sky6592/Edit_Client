package com.doublejj.edit.data.models.my_sentence_completed

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class MySentenceCompletedResponse(
    @SerializedName("result") val result: MutableList<MySentenceCompletedResult>
) : BaseResponse()