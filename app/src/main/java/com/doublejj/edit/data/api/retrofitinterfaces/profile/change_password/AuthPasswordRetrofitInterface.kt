package com.doublejj.edit.data.api.retrofitinterfaces.profile.change_password

import com.doublejj.edit.data.models.ResultStringResponse
import com.doublejj.edit.data.models.profile.change_password.AuthPasswordRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthPasswordRetrofitInterface {
    @POST("/api/users/authentication-password")
    fun postAuthPassword(
        @Body request: AuthPasswordRequest
    ) : Call<ResultStringResponse>
}