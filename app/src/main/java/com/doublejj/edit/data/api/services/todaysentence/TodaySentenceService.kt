package com.doublejj.edit.data.api.services.todaysentence

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.todaysentence.TodaySentenceRetrofitInterface
import com.doublejj.edit.data.models.todaysentence.TodaySentenceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodaySentenceService(val view: TodaySentenceView) {
    fun tryGetTodaySentence(page: Int) {
        val retrofitInterface = sRetrofit.create(TodaySentenceRetrofitInterface::class.java)
        retrofitInterface
            .getTodaySentence(page)
            .enqueue(object : Callback<TodaySentenceResponse> {
                override fun onResponse(
                    call: Call<TodaySentenceResponse>,
                    response: Response<TodaySentenceResponse>
                ) {
                    view.onGetTodaySentenceSuccess(response.body() as TodaySentenceResponse)
                }

                override fun onFailure(call: Call<TodaySentenceResponse>, t: Throwable) {
                    view.onGetTodaySentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}