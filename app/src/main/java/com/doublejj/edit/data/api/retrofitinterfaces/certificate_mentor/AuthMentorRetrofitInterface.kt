package com.doublejj.edit.data.api.retrofitinterfaces.certificate_mentor

import com.doublejj.edit.data.models.BaseResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthMentorRetrofitInterface {
    @Multipart
    @POST("/api/users/authentication")
    fun postAuthMentor(
        @Part authenticationImage : MultipartBody.Part
    ) : Call<BaseResponse>
}