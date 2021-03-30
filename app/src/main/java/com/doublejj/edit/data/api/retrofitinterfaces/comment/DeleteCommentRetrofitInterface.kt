package com.doublejj.edit.data.api.retrofitinterfaces.comment

import com.doublejj.edit.data.models.BaseResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteCommentRetrofitInterface {
    @DELETE("/api/comments/{comment-id}")
    fun deleteComment(
        @Path ("comment-id") commentId: Long
    ) : Call<BaseResponse>
}