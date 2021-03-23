package com.doublejj.edit.data.api.services.best_sympathy

import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse

interface BestSympathyView {
    fun onGetBestSympathySentenceSuccess(response: LookupSentenceResponse)
    fun onGetBestSympathySentenceFailure(message: String)
}