package com.doublejj.edit.data.api.services.my_sentence_not_adopted

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.my_sentence_not_adopted.CompletingSentenceRetrofitInterface
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.my_sentence_not_adopted.CompletingSentenceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompletingSentenceService(val view: CompletingSentenceView) {
    fun tryPostCompletingSentence(request: CompletingSentenceRequest) {
        val retrofitInterface = sRetrofit.create(CompletingSentenceRetrofitInterface::class.java)
        retrofitInterface
            .postCompletingSentence(request)
            .enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    view.onPostCompletingSentenceSuccess(response.body() as ResultResponse)
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    view.onPostCompletingSentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}