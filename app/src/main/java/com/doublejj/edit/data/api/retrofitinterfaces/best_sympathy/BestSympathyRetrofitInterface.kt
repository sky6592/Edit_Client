package com.doublejj.edit.data.api.retrofitinterfaces.best_sympathy

import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BestSympathyRetrofitInterface {
    @GET("/api/many-sympathies-cover-letters")
    fun getBestSympathySentence(
        @Query("page") page: Int
    ) : Call<LookupSentenceResponse>
}