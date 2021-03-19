package com.doublejj.edit.data.models.lookup_sentences_home

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.sentence.SentenceData
import com.google.gson.annotations.SerializedName

class LookupSentenceResponse (
    @SerializedName("result") val result: MutableList<SentenceData>
) : BaseResponse()