package com.doublejj.edit.data.api.retrofitinterfaces.my_comment

import com.doublejj.edit.data.models.my_comment.MyCommentResponse
import retrofit2.Call
import retrofit2.http.*

interface MyCommentRetrofitInterface {
    @GET("/api/comments")
    fun getMyCommentList(
        @Query ("page") page: Int
    ) : Call<MyCommentResponse>
}