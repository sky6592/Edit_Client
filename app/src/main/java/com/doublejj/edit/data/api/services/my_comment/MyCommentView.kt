package com.doublejj.edit.data.api.services.my_comment

import com.doublejj.edit.data.models.my_comment.MyCommentResponse

interface MyCommentView {
    fun onGetMyCommentSuccess(response: MyCommentResponse)
    fun onGetMyCommentFailure(message: String)
}