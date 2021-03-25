package com.doublejj.edit.data.api.services.profile.change_password

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.profile.change_password.AuthPasswordRetrofitInterface
import com.doublejj.edit.data.models.ResultStringResponse
import com.doublejj.edit.data.models.profile.change_password.AuthPasswordRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthPasswordService(val view: AuthPasswordView) {
    fun tryPostAuthPassword(request: AuthPasswordRequest) {
        val retrofitInterface = sRetrofit.create(AuthPasswordRetrofitInterface::class.java)
        retrofitInterface
            .postAuthPassword(request)
            .enqueue(object : Callback<ResultStringResponse> {
                override fun onResponse(
                    call: Call<ResultStringResponse>,
                    response: Response<ResultStringResponse>
                ) {
                    view.onAuthPasswordSuccess(response.body() as ResultStringResponse)
                }

                override fun onFailure(call: Call<ResultStringResponse>, t: Throwable) {
                    view.onAuthPasswordFailure(t.message ?: "통신 오류")
                }
            })
    }
}