package com.doublejj.edit.data.api.retrofitinterfaces.main_oneshot

import com.doublejj.edit.data.models.main_oneshot.MainOneshotResponse
import retrofit2.Call
import retrofit2.http.GET

interface MainOneshotRetrofitInterface {
    @GET("/api/main")
    fun getMainSentences() : Call<MainOneshotResponse>
}