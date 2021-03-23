package com.doublejj.edit.data.api.services.waiting_for_comment

import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse

interface WaitingForCommentView {
    fun onGetWaitingCommentSentenceSuccess(response: LookupSentenceResponse)
    fun onGetWaitingCommentSentenceFailure(message: String)
}