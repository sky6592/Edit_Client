package com.doublejj.edit.data.models.my_sympathized_sentence

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.lookup_sentences_home.GetCoverLettersForLimitScrollResult
import com.google.gson.annotations.SerializedName

data class MySympathizedSentenceResponse (
    @SerializedName("result") val result: GetCoverLettersForLimitScrollResult
) : BaseResponse()