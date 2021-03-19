package com.doublejj.edit.data.api.services.writing_sentence

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.writing_sentence.SentenceLimitRetrofitInterface
import com.doublejj.edit.data.models.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SentenceLimitService(val view: SentenceLimitView) {
    fun tryGetSentenceLimit() {
        val retrofitInterface = sRetrofit.create(SentenceLimitRetrofitInterface::class.java)
        retrofitInterface
            .getSentenceLimit()
            .enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    view.onGetSentenceLimitSuccess(response.body() as ResultResponse)
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    view.onGetSentenceLimitFailure(t.message ?: "통신 오류")
                }
            })
    }
}