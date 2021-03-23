package com.doublejj.edit.data.api.services.infosecond

import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.data.api.retrofitinterfaces.infosecond.InfoSecondRetrofitInterface
import com.doublejj.edit.data.models.infosecond.InfoSecondRequest
import com.doublejj.edit.data.models.infosecond.InfoSecondResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoSecondService(val infoSecondView: InfoSecondView) {
    fun tryPostEmail(postInfoSecondRequest: InfoSecondRequest) {
        val infoSecondRetrofitInterface = ApplicationClass.sRetrofit.create(
            InfoSecondRetrofitInterface::class.java
        )
        infoSecondRetrofitInterface.postEmail(postInfoSecondRequest).enqueue(object :
            Callback<InfoSecondResponse> {
            override fun onResponse(
                call: Call<InfoSecondResponse>,
                response: Response<InfoSecondResponse>
            ) {
                infoSecondView.onPostInfoSecondSuccess(response.body() as InfoSecondResponse)
            }

            override fun onFailure(call: Call<InfoSecondResponse>, t: Throwable) {
                infoSecondView.onPostInfoSecondFailure(t.message ?: "통신 오류")
            }

        })
    }
}