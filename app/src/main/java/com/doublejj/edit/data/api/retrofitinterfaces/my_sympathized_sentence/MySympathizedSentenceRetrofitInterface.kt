package com.doublejj.edit.data.api.retrofitinterfaces.my_sympathized_sentence

import com.doublejj.edit.data.models.my_sympathized_sentence.MySympathizedSentenceResponse
import retrofit2.Call
import retrofit2.http.*

interface MySympathizedSentenceRetrofitInterface {
    @GET("/api/sympathize-cover-letters")
    fun getMySympathizedSentence(
        @Query ("page") page: Int
    ) : Call<MySympathizedSentenceResponse>
}