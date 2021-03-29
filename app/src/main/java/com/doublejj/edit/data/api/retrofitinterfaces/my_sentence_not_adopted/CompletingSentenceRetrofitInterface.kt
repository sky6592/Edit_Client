package com.doublejj.edit.data.api.retrofitinterfaces.my_sentence_not_adopted

import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.my_sentence_not_adopted.CompletingSentenceRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CompletingSentenceRetrofitInterface {
    @POST("/api/completing-cover-letters")
    fun postCompletingSentence(
        @Body request: CompletingSentenceRequest
    ) : Call<ResultResponse>
}