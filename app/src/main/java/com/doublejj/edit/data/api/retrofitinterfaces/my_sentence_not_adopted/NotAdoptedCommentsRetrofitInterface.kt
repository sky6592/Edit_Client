package com.doublejj.edit.data.api.retrofitinterfaces.my_sentence_not_adopted

import com.doublejj.edit.data.models.ResultResponse
import retrofit2.Call
import retrofit2.http.*

interface NotAdoptedCommentsRetrofitInterface {
    @GET("/api/cover-letters/{cover-letter-id}/not-adopted-comments")
    fun getNotAdoptedComments(
        @Path ("cover-letter-id") sentenceId: Long,
        @Query ("page") page: Int
    ) : Call<ResultResponse>
}