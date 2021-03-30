package com.doublejj.edit.data.api.services.my_sentence_not_adopted

import com.doublejj.edit.data.models.my_sentence_not_adopted.SentenceToCompleteResponse

interface SentenceToCompleteView {
    fun onGetSentenceToCompleteSuccess(response: SentenceToCompleteResponse, position: Int)
    fun onGetSentenceToCompleteFailure(message: String)
}