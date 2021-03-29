package com.doublejj.edit.data.api.services.manage_coin

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.manage_coin.ManageCoinRetrofitInterface
import com.doublejj.edit.data.models.manage_coin.ManageCoinResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageCoinService(val view: ManageCoinView) {
    fun tryGetManageCoin() {
        val retrofitInterface = sRetrofit.create(ManageCoinRetrofitInterface::class.java)
        retrofitInterface
            .getManageCoin()
            .enqueue(object : Callback<ManageCoinResponse> {
                override fun onResponse(
                    call: Call<ManageCoinResponse>,
                    response: Response<ManageCoinResponse>
                ) {
                    view.onGetManageCoinSuccess(response.body() as ManageCoinResponse)
                }

                override fun onFailure(call: Call<ManageCoinResponse>, t: Throwable) {
                    view.onGetManageCoinFailure(t.message ?: "통신 오류")
                }
            })
    }
}