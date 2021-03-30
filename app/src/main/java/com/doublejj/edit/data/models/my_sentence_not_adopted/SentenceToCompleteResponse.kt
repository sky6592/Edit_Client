package com.doublejj.edit.data.models.my_sentence_not_adopted

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class SentenceToCompleteResponse(
    @SerializedName("result") val result: SentenceToCompleteResult,
    @SerializedName("position") var position: Int? = null
) : BaseResponse()