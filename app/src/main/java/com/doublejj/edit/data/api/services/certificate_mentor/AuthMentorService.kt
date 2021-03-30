package com.doublejj.edit.data.api.services.certificate_mentor

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.certificate_mentor.AuthMentorRetrofitInterface
import com.doublejj.edit.data.models.BaseResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthMentorService(val view: AuthMentorView) {
    fun tryPostAuthMentor(file: MultipartBody.Part) {
        val retrofitInterface = sRetrofit.create(AuthMentorRetrofitInterface::class.java)
        retrofitInterface
            .postAuthMentor(file)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    view.onAuthMentorSuccess(response.body() as BaseResponse)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onAuthMentorFailure(t.message ?: "통신 오류")
                }
            })
    }
}