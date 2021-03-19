package com.doublejj.edit.data.api.services.writing_sentence

import com.doublejj.edit.data.models.ResultResponse

interface WritingSentenceView {
    fun onPostWritingSentenceSuccess(response: ResultResponse)
    fun onPostWritingSentenceFailure(message: String)
}