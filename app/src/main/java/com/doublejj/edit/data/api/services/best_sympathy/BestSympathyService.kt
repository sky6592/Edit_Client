package com.doublejj.edit.data.api.services.best_sympathy

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.best_sympathy.BestSympathyRetrofitInterface
import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BestSympathyService(val view: BestSympathyView) {
    fun tryGetBestSympathySentence(page: Int) {
        val retrofitInterface = sRetrofit.create(BestSympathyRetrofitInterface::class.java)
        retrofitInterface
            .getBestSympathySentence(page)
            .enqueue(object : Callback<LookupSentenceResponse> {
                override fun onResponse(
                    call: Call<LookupSentenceResponse>,
                    response: Response<LookupSentenceResponse>
                ) {
                    view.onGetBestSympathySentenceSuccess(response.body() as LookupSentenceResponse)
                }

                override fun onFailure(call: Call<LookupSentenceResponse>, t: Throwable) {
                    view.onGetBestSympathySentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}