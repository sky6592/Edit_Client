package com.doublejj.edit.data.api.retrofitinterfaces.comment

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.writing_comment.WritingCommentRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface WritingCommentRetrofitInterface {
    @POST("/api/comments")
    fun postWritingComment(
        @Body request: WritingCommentRequest
    ) : Call<BaseResponse>
}