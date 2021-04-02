package com.doublejj.edit.data.api.services.my_sympathized_sentence

import com.doublejj.edit.data.models.my_sympathized_sentence.MySympathizedSentenceResponse

interface MySympathizedSentenceView {
    fun onGetMySympathizedSentenceSuccess(response: MySympathizedSentenceResponse)
    fun onGetMySympathizedSentenceFailure(message: String)
}