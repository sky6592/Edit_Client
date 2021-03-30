package com.doublejj.edit.data.api.services.comment

import com.doublejj.edit.data.models.ResultResponse

interface AdoptCommentView {
    fun onAdoptCommentSuccess(response: ResultResponse)
    fun onAdoptCommentFailure(message: String)
}