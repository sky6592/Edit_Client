package com.doublejj.edit.data.api.services

import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.data.api.retrofitinterfaces.LoginRetrofitInterface
import com.doublejj.edit.data.models.login.LoginResponse
import com.doublejj.edit.data.models.login.PostLoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInService(val logInView: LogInView) {
    fun tryPostLogin(postLoginRequest: PostLoginRequest) {
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postLogin(postLoginRequest).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                logInView.onPostLoginSuccess(response.body() as LoginResponse)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                logInView.onPostLoginFailure(t.message ?: "통신 오류")
            }
        })
    }
}