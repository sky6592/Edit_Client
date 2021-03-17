package com.doublejj.edit.data.api.services.writing_sentence

import com.doublejj.edit.data.models.ResultResponse

interface SentenceLimitView {
    fun onGetSentenceLimitSuccess(response: ResultResponse)
    fun onGetSentenceLimitFailure(message: String)
}