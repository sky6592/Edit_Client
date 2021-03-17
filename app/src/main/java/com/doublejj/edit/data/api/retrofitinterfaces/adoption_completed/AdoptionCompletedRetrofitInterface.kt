package com.doublejj.edit.data.api.retrofitinterfaces.adoption_completed

import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AdoptionCompletedRetrofitInterface {
    @GET("/api/adopted-cover-letters")
    fun getAdoptionCompletedSentence(
        @Query("page") page: Int
    ) : Call<LookupSentenceResponse>
}