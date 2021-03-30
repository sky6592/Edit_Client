package com.doublejj.edit.data.api.services.my_sentence_not_adopted

import com.doublejj.edit.data.models.ResultResponse

interface CompletingSentenceView {
    fun onPostCompletingSentenceSuccess(response: ResultResponse)
    fun onPostCompletingSentenceFailure(message: String)
}