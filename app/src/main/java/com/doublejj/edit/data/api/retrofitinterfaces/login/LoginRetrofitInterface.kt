package com.doublejj.edit.data.api.retrofitinterfaces.login

import com.doublejj.edit.data.models.login.LoginResponse
import com.doublejj.edit.data.models.login.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("/api/login")
    fun postLogin(
        @Body request: LoginRequest
    ) : Call<LoginResponse>
}