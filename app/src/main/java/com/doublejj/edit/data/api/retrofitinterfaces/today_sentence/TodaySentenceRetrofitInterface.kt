package com.doublejj.edit.data.api.retrofitinterfaces.today_sentence

import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TodaySentenceRetrofitInterface {
    @GET("/api/today-cover-letters")
    fun getTodaySentence(
        @Query("page") page: Int
    ) : Call<LookupSentenceResponse>
}