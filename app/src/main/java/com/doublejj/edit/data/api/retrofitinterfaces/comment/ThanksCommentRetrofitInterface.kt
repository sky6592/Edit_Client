package com.doublejj.edit.data.api.retrofitinterfaces.comment

import com.doublejj.edit.data.models.comment.ThanksCommentResponse
import retrofit2.Call
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ThanksCommentRetrofitInterface {
    @PATCH("/api/comments/{comment-id}/appreciate-comments")
    fun patchThanksComment(
        @Path ("comment-id") commentId : Long
    ) : Call<ThanksCommentResponse>
}