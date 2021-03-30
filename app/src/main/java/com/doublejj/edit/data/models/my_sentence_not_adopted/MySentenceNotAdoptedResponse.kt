package com.doublejj.edit.data.models.my_sentence_not_adopted

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class MySentenceNotAdoptedResponse(
    @SerializedName("result") val result: MutableList<MySentenceNotAdoptedResult>
) : BaseResponse()