package com.doublejj.edit.data.api.retrofitinterfaces.comment

import com.doublejj.edit.data.models.ResultResponse
import retrofit2.Call
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AdoptCommentRetrofitInterface {
    @PATCH("/api/comments/{comment-id}/adopt-comments")
    fun patchAdoptComment(
        @Path ("comment-id") commentId : Long
    ) : Call<ResultResponse>
}