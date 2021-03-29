package com.doublejj.edit.data.api.retrofitinterfaces.my_sentence_completed

import com.doublejj.edit.data.models.my_sentence_completed.MySentenceCompletedResponse
import retrofit2.Call
import retrofit2.http.*

interface MySentenceCompletedRetrofitInterface {
    @GET("/api/my-completing-cover-letters")
    fun getMySentenceCompleted(
        @Query ("page") page: Int
    ) : Call<MySentenceCompletedResponse>
}