package com.doublejj.edit.data.api.services.lookup_comments_of_sentence

import com.doublejj.edit.data.models.lookup_comments_of_sentence.LookupCommentResponse

interface CommentsOfSentenceView {
    fun onGetCommentsOfSentenceSuccess(response: LookupCommentResponse)
    fun onGetCommentsOfSentenceFailure(message: String)
}