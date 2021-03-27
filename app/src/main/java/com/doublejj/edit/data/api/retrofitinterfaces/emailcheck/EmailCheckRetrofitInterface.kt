package com.doublejj.edit.data.api.retrofitinterfaces.emailcheck

import com.doublejj.edit.data.models.emailcheck.EmailCheckRequest
import com.doublejj.edit.data.models.emailcheck.EmailCheckResponse
import com.doublejj.edit.data.models.infofirst.InfoFirstRequest
import com.doublejj.edit.data.models.infofirst.InfoFirstResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailCheckRetrofitInterface {
    @POST("/api/users/authentication-email")
    fun postEmailCheck(@Body params: EmailCheckRequest): Call<EmailCheckResponse>
}