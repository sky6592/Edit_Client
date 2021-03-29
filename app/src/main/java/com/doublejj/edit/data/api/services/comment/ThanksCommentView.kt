package com.doublejj.edit.data.api.services.comment

import com.doublejj.edit.data.models.comment.ThanksCommentResponse

interface ThanksCommentView {
    fun onThanksCommentSuccess(response: ThanksCommentResponse)
    fun onThanksCommentFailure(message: String)
}