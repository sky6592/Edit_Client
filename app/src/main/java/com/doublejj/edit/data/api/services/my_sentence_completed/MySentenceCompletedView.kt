package com.doublejj.edit.data.api.services.my_sentence_completed

import com.doublejj.edit.data.models.my_sentence_completed.MySentenceCompletedResponse

interface MySentenceCompletedView {
    fun onGetMySentenceCompleteSuccess(response: MySentenceCompletedResponse)
    fun onGetMySentenceCompleteFailure(message: String)
}