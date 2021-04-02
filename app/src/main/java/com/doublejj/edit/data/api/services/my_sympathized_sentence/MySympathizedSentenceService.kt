package com.doublejj.edit.data.api.services.my_sympathized_sentence

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.my_sympathized_sentence.MySympathizedSentenceRetrofitInterface
import com.doublejj.edit.data.models.my_sympathized_sentence.MySympathizedSentenceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySympathizedSentenceService(val view: MySympathizedSentenceView) {
    fun tryGetMySympathizedSentence(page: Int) {
        val retrofitInterface = sRetrofit.create(MySympathizedSentenceRetrofitInterface::class.java)
        retrofitInterface
            .getMySympathizedSentence(page)
            .enqueue(object : Callback<MySympathizedSentenceResponse> {
                override fun onResponse(
                    call: Call<MySympathizedSentenceResponse>,
                    response: Response<MySympathizedSentenceResponse>
                ) {
                    view.onGetMySympathizedSentenceSuccess(response.body() as MySympathizedSentenceResponse)
                }

                override fun onFailure(call: Call<MySympathizedSentenceResponse>, t: Throwable) {
                    view.onGetMySympathizedSentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}