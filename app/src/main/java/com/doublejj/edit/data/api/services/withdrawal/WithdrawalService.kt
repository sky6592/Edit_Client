package com.doublejj.edit.data.api.services.withdrawal

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.withdrawal.WithdrawalRetrofitInterface
import com.doublejj.edit.data.models.withdrawal.WithdrawalRequest
import com.doublejj.edit.data.models.withdrawal.WithdrawalResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WithdrawalService(val view: WithdrawalView) {
    fun tryDeleteWithdrawal(request: WithdrawalRequest) {
        val retrofitInterface = sRetrofit.create(WithdrawalRetrofitInterface::class.java)
        retrofitInterface
            .deleteWithdrawal(request)
            .enqueue(object : Callback<WithdrawalResponse> {
                override fun onResponse(
                    call: Call<WithdrawalResponse>,
                    response: Response<WithdrawalResponse>
                ) {
                    view.onWithdrawalSuccess(response.body() as WithdrawalResponse)
                }

                override fun onFailure(call: Call<WithdrawalResponse>, t: Throwable) {
                    view.onWithdrawalFailure(t.message ?: "통신 오류")
                }
            })
    }
}