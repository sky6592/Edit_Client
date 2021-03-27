package com.doublejj.edit.data.api.retrofitinterfaces.emailfind

import com.doublejj.edit.data.models.emailfind.EmailFindRequest
import com.doublejj.edit.data.models.emailfind.EmailFindResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailFindRetrofitInterface {
    @POST("/api/users/email")
    fun postEmailFind(
        @Body request: EmailFindRequest
    ): Call<EmailFindResponse>
}