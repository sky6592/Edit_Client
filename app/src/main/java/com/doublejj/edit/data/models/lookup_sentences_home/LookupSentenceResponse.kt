package com.doublejj.edit.data.models.lookup_sentences_home

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.sentence.SentenceData
import com.google.gson.annotations.SerializedName

class LookupSentenceResponse (
    @SerializedName("result") val result: GetCoverLettersForLimitScrollResult
) : BaseResponse()

data class GetCoverLettersForLimitScrollResult (
    @SerializedName("coverLetters") val coverLetters: MutableList<SentenceData>,
    @SerializedName("totalCoverLetterCount") val totalCoverLetterCount: Long,
    @SerializedName("hasNext") val hasNext: Boolean
)