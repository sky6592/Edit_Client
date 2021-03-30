package com.doublejj.edit.data.api.services.my_sentence_completed

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.my_sentence_completed.MySentenceCompletedRetrofitInterface
import com.doublejj.edit.data.models.my_sentence_completed.MySentenceCompletedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySentenceCompletedService(val view: MySentenceCompletedView) {
    fun tryGetMySentenceCompleted(page: Int) {
        val retrofitInterface = sRetrofit.create(MySentenceCompletedRetrofitInterface::class.java)
        retrofitInterface
            .getMySentenceCompleted(page)
            .enqueue(object : Callback<MySentenceCompletedResponse> {
                override fun onResponse(
                    call: Call<MySentenceCompletedResponse>,
                    response: Response<MySentenceCompletedResponse>
                ) {
                    view.onGetMySentenceCompleteSuccess(response.body() as MySentenceCompletedResponse)
                }

                override fun onFailure(call: Call<MySentenceCompletedResponse>, t: Throwable) {
                    view.onGetMySentenceCompleteFailure(t.message ?: "통신 오류")
                }
            })
    }
}