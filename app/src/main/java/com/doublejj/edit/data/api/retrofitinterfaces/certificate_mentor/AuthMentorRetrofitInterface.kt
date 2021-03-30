package com.doublejj.edit.data.api.retrofitinterfaces.certificate_mentor

import com.doublejj.edit.data.models.BaseResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface AuthMentorRetrofitInterface {
    @Multipart
    @POST("/api/users/authentication")
    fun postAuthMentor(
        @Part file : MultipartBody.Part
    ) : Call<BaseResponse>

}