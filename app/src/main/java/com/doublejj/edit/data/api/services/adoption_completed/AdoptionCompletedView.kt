package com.doublejj.edit.data.api.services.adoption_completed

import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse

interface AdoptionCompletedView {
    fun onGetAdoptionCompletedSentenceSuccess(response: LookupSentenceResponse)
    fun onGetAdoptionCompletedSentenceFailure(message: String)
}