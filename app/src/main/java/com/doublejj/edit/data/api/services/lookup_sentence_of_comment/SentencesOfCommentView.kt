package com.doublejj.edit.data.api.services.lookup_sentence_of_comment

import com.doublejj.edit.data.models.lookup_sentence_mentor.LookupSentenceMentorResponse

interface SentencesOfCommentView {
    fun onGetSentencesOfCommentSuccess(response: LookupSentenceMentorResponse)
    fun onGetSentencesOfCommentFailure(message: String)
}