package com.doublejj.edit.data.api.services.today_sentence

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.today_sentence.TodaySentenceRetrofitInterface
import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodaySentenceService(val view: TodaySentenceView) {
    fun tryGetTodaySentence(page: Int) {
        val retrofitInterface = sRetrofit.create(TodaySentenceRetrofitInterface::class.java)
        retrofitInterface
            .getTodaySentence(page)
            .enqueue(object : Callback<LookupSentenceResponse> {
                override fun onResponse(
                    call: Call<LookupSentenceResponse>,
                    response: Response<LookupSentenceResponse>
                ) {
                    view.onGetTodaySentenceSuccess(response.body() as LookupSentenceResponse)
                }

                override fun onFailure(call: Call<LookupSentenceResponse>, t: Throwable) {
                    view.onGetTodaySentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}