package com.doublejj.edit.data.api.retrofitinterfaces.certificate_mentor

import com.doublejj.edit.data.models.certificate_mentor.AuthMentorStatusResponse
import retrofit2.Call
import retrofit2.http.GET

interface AuthMentorStatusRetrofitInterface {
    @GET("/api/users/authentication")
    fun getAuthMentorStatus(
    ) : Call<AuthMentorStatusResponse>
}