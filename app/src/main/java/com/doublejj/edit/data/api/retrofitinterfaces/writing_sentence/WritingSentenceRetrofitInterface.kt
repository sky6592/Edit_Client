package com.doublejj.edit.data.api.retrofitinterfaces.writing_sentence

import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.writing_sentence.WritingSentenceRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface WritingSentenceRetrofitInterface {
    @POST("/api/writing-cover-letters")
    fun postWritingSentence(
        @Body params: WritingSentenceRequest
    ) : Call<ResultResponse>
}