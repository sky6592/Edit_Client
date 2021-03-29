package com.doublejj.edit.data.api.services.sentence

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.sentence.DeletePublishedSentenceRetrofitInterface
import com.doublejj.edit.data.models.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeletePublishedSentenceService(val view: DeletePublishedSentenceView) {
    fun tryDeletePublishedSentence(sentenceId: Long) {
        val retrofitInterface = sRetrofit.create(DeletePublishedSentenceRetrofitInterface::class.java)
        retrofitInterface
            .deletePublishedSentence(sentenceId)
            .enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    view.onDeletePublishedSentenceSuccess(response.body() as ResultResponse)
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    view.onDeletePublishedSentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}