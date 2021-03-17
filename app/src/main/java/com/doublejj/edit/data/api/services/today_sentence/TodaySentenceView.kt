package com.doublejj.edit.data.api.services.today_sentence

import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse

interface TodaySentenceView {
    fun onGetTodaySentenceSuccess(response: LookupSentenceResponse)
    fun onGetTodaySentenceFailure(message: String)
}