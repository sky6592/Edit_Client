package com.doublejj.edit.data.api.retrofitinterfaces.todaysentence

import com.doublejj.edit.data.models.todaysentence.TodaySentenceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TodaySentenceRetrofitInterface {
    @GET("/api/today-cover-letters")
    fun getTodaySentence(
        @Query("page") page: Int
    ) : Call<TodaySentenceResponse>
}