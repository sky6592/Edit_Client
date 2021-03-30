package com.doublejj.edit.data.api.services.comment

import com.doublejj.edit.data.models.BaseResponse

interface WritingCommentView {
    fun onWritingCommentSuccess(response: BaseResponse)
    fun onWritingCommentFailure(message: String)
}