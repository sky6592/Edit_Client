package com.doublejj.edit.data.api.retrofitinterfaces.writing_sentence

import com.doublejj.edit.data.models.ResultResponse
import retrofit2.Call
import retrofit2.http.GET

interface SentenceLimitRetrofitInterface {
    @GET("/api/today-writing-cover-letter-count")
    fun getSentenceLimit() : Call<ResultResponse>
}