package com.doublejj.edit.data.api.services.my_sentence_not_adopted

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.my_sentence_not_adopted.SentenceToCompleteRetrofitInterface
import com.doublejj.edit.data.models.my_sentence_not_adopted.SentenceToCompleteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SentenceToCompleteService(val view: SentenceToCompleteView) {
    fun tryGetSentenceToComplete(sentenceId: Long, position: Int) {
        val retrofitInterface = sRetrofit.create(SentenceToCompleteRetrofitInterface::class.java)
        retrofitInterface
            .getMySentenceNotAdopted(sentenceId)
            .enqueue(object : Callback<SentenceToCompleteResponse> {
                override fun onResponse(
                    call: Call<SentenceToCompleteResponse>,
                    response: Response<SentenceToCompleteResponse>
                ) {
                    view.onGetSentenceToCompleteSuccess(response.body() as SentenceToCompleteResponse, position)
                }

                override fun onFailure(call: Call<SentenceToCompleteResponse>, t: Throwable) {
                    view.onGetSentenceToCompleteFailure(t.message ?: "통신 오류")
                }
            })
    }
}