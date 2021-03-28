package com.doublejj.edit.data.api.services.sentence

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.sentence.SympathizeSentenceRetrofitInterface
import com.doublejj.edit.data.models.sentence.SympathizeSentenceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SympathizeSentenceService(val view: SympathizeSentenceView) {
    fun tryPatchSympathizeSentence(sentenceId: Int) {
        val retrofitInterface = sRetrofit.create(SympathizeSentenceRetrofitInterface::class.java)
        retrofitInterface
            .patchSympathizeSentence(sentenceId)
            .enqueue(object : Callback<SympathizeSentenceResponse> {
                override fun onResponse(
                    call: Call<SympathizeSentenceResponse>,
                    response: Response<SympathizeSentenceResponse>
                ) {
                    view.onSympathizeSentenceSuccess(response.body() as SympathizeSentenceResponse)
                }

                override fun onFailure(call: Call<SympathizeSentenceResponse>, t: Throwable) {
                    view.onSympathizeSentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}