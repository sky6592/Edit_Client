package com.doublejj.edit.data.api.retrofitinterfaces.entercode

import com.doublejj.edit.data.models.entercode.EnterCodeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EnterCodeRetrofitInterface {
    @GET("/api/users/authentication-code")
    fun getEnterCode(@Query("authenticationCode") authenticationCode: String): Call<EnterCodeResponse>
}