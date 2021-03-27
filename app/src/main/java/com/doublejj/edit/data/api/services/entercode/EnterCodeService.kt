package com.doublejj.edit.data.api.services.entercode

import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.data.api.retrofitinterfaces.entercode.EnterCodeRetrofitInterface
import com.doublejj.edit.data.models.entercode.EnterCodeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterCodeService(val enterCodeView: EnterCodeView) {
    fun tryGetEnterCode(authenticationCode: String) {
        val enterCodeRetrofitInterface =
            ApplicationClass.sRetrofit.create(EnterCodeRetrofitInterface::class.java)
        enterCodeRetrofitInterface.getEnterCode(authenticationCode)
            .enqueue(object : Callback<EnterCodeResponse> {
                override fun onResponse(
                    call: Call<EnterCodeResponse>,
                    response: Response<EnterCodeResponse>
                ) {
                    enterCodeView.onGetEnterCodeSuccess(response.body() as EnterCodeResponse)
                }

                override fun onFailure(call: Call<EnterCodeResponse>, t: Throwable) {
                    enterCodeView.onGetEnterCodeFailure(t.message ?: "통신 오류")
                }
            })
    }
}