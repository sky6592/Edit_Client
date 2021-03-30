package com.doublejj.edit.data.api.services.certificate_mentor

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.certificate_mentor.AuthMentorStatusRetrofitInterface
import com.doublejj.edit.data.models.certificate_mentor.AuthMentorStatusResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthMentorStatusService(val view: AuthMentorStatusView) {
    fun tryGetAuthMentorStatus() {
        val retrofitInterface = sRetrofit.create(AuthMentorStatusRetrofitInterface::class.java)
        retrofitInterface
            .getAuthMentorStatus()
            .enqueue(object : Callback<AuthMentorStatusResponse> {
                override fun onResponse(
                    call: Call<AuthMentorStatusResponse>,
                    response: Response<AuthMentorStatusResponse>
                ) {
                    view.onAuthMentorStatusSuccess(response.body() as AuthMentorStatusResponse)
                }

                override fun onFailure(call: Call<AuthMentorStatusResponse>, t: Throwable) {
                    view.onAuthMentorStatusFailure(t.message ?: "통신 오류")
                }
            })
    }
}