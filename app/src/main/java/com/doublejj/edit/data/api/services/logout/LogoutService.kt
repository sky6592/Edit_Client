package com.doublejj.edit.data.api.services.logout

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.logout.LogoutRetrofitInterface
import com.doublejj.edit.data.models.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoutService(val logInView: LogoutView) {
    fun tryPostLogout() {
        val retrofitInterface = sRetrofit.create(LogoutRetrofitInterface::class.java)
        retrofitInterface
            .postLogout()
            .enqueue(object : Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                logInView.onPostLogoutSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                logInView.onPostLogoutFailure(t.message ?: "통신 오류")
            }
        })
    }
}