package com.doublejj.edit.data.api.services.adoption_completed

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.adoption_completed.AdoptionCompletedRetrofitInterface
import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdoptionCompletedService(val view: AdoptionCompletedView) {
    fun tryGetAdoptionCompletedSentence(page: Int) {
        val retrofitInterface = sRetrofit.create(AdoptionCompletedRetrofitInterface::class.java)
        retrofitInterface
            .getAdoptionCompletedSentence(page)
            .enqueue(object : Callback<LookupSentenceResponse> {
                override fun onResponse(
                    call: Call<LookupSentenceResponse>,
                    response: Response<LookupSentenceResponse>
                ) {
                    view.onGetAdoptionCompletedSentenceSuccess(response.body() as LookupSentenceResponse)
                }

                override fun onFailure(call: Call<LookupSentenceResponse>, t: Throwable) {
                    view.onGetAdoptionCompletedSentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}