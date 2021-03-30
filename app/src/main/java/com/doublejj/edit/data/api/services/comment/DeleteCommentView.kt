package com.doublejj.edit.data.api.services.comment

import com.doublejj.edit.data.models.BaseResponse

interface DeleteCommentView {
    fun onDeleteCommentSuccess(response: BaseResponse)
    fun onDeleteCommentFailure(message: String)
}