package com.doublejj.edit.data.api.services.sentence

import com.doublejj.edit.data.models.sentence.SympathizeSentenceResponse

interface SympathizeSentenceView {
    fun onSympathizeSentenceSuccess(response: SympathizeSentenceResponse)
    fun onSympathizeSentenceFailure(message: String)
}