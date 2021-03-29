package com.doublejj.edit.data.api.services.sentence

import com.doublejj.edit.data.models.ResultResponse

interface DeletePublishedSentenceView {
    fun onDeletePublishedSentenceSuccess(response: ResultResponse)
    fun onDeletePublishedSentenceFailure(message: String)
}