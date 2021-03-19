package com.doublejj.edit.data.api.services.writing_sentence

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.writing_sentence.WritingSentenceRetrofitInterface
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.writing_sentence.WritingSentenceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WritingSentenceService(val view: WritingSentenceView) {
    fun tryPostWritingSentence(params: WritingSentenceRequest) {
        val retrofitInterface = sRetrofit.create(WritingSentenceRetrofitInterface::class.java)
        retrofitInterface
            .postWritingSentence(params)
            .enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    view.onPostWritingSentenceSuccess(response.body() as ResultResponse)
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    view.onPostWritingSentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}