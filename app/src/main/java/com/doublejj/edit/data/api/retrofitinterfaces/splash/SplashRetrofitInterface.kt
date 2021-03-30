package com.doublejj.edit.data.api.retrofitinterfaces.splash

import com.doublejj.edit.data.models.splash.SplashResponse
import retrofit2.http.GET
import retrofit2.Call


interface SplashRetrofitInterface {
    @GET("/api/auto-login")
    fun getSplash(): Call<SplashResponse>
}