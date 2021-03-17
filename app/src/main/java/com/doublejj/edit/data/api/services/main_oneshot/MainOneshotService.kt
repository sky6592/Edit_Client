package com.doublejj.edit.data.api.services.main_oneshot

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.main_oneshot.MainOneshotRetrofitInterface
import com.doublejj.edit.data.models.main_oneshot.MainOneshotResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainOneshotService(val view: MainOneshotView) {
    fun tryGetMainSentences() {
        val mainOneshotRetrofitInterface = sRetrofit.create(MainOneshotRetrofitInterface::class.java)
        mainOneshotRetrofitInterface
            .getMainSentences()
            .enqueue(object : Callback<MainOneshotResponse> {
                override fun onResponse(
                    call: Call<MainOneshotResponse>,
                    response: Response<MainOneshotResponse>
                ) {
                    view.onGetMainSentencesSuccess(response.body() as MainOneshotResponse)
                }

                override fun onFailure(call: Call<MainOneshotResponse>, t: Throwable) {
                    view.onGetMainSentencesFailure(t.message ?: "통신 오류")
                }
            })
    }
}