package com.doublejj.edit.data.api.services.emailcheck

import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.data.api.retrofitinterfaces.emailcheck.EmailCheckRetrofitInterface
import com.doublejj.edit.data.api.retrofitinterfaces.infofirst.InfoFirstRetrofitInterface
import com.doublejj.edit.data.models.emailcheck.EmailCheckRequest
import com.doublejj.edit.data.models.emailcheck.EmailCheckResponse
import com.doublejj.edit.data.models.infofirst.InfoFirstResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailCheckService(val emailCheckVIew: EmailCheckVIew) {
    fun tryPostEmailCheck(emailCheckRequest: EmailCheckRequest) {
        val emailCheckRetrofitInterface = ApplicationClass.sRetrofit.create(
            EmailCheckRetrofitInterface::class.java
        )
        emailCheckRetrofitInterface.postEmailCheck(emailCheckRequest)
            .enqueue(object : Callback<EmailCheckResponse> {
                override fun onResponse(
                    call: Call<EmailCheckResponse>,
                    response: Response<EmailCheckResponse>
                ) {
                    emailCheckVIew.onPostEmailCheckSuccess(response.body() as EmailCheckResponse)
                }

                override fun onFailure(call: Call<EmailCheckResponse>, t: Throwable) {
                    emailCheckVIew.onPostEmailCheckFailure(t.message ?: "통신 오류")

                }

            })

    }
}