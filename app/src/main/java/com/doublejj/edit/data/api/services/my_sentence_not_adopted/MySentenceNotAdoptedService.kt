package com.doublejj.edit.data.api.services.my_sentence_not_adopted

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.my_sentence_not_adopted.MySentenceNotAdoptedRetrofitInterface
import com.doublejj.edit.data.models.my_sentence_not_adopted.MySentenceNotAdoptedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySentenceNotAdoptedService(val view: MySentenceNotAdoptedView) {
    fun tryGetMySentenceNotAdopted(page: Int) {
        val retrofitInterface = sRetrofit.create(MySentenceNotAdoptedRetrofitInterface::class.java)
        retrofitInterface
            .getMySentenceNotAdopted(page)
            .enqueue(object : Callback<MySentenceNotAdoptedResponse> {
                override fun onResponse(
                    call: Call<MySentenceNotAdoptedResponse>,
                    response: Response<MySentenceNotAdoptedResponse>
                ) {
                    view.onGetMySentenceNotAdoptedSuccess(response.body() as MySentenceNotAdoptedResponse)
                }

                override fun onFailure(call: Call<MySentenceNotAdoptedResponse>, t: Throwable) {
                    view.onGetMySentenceNotAdoptedFailure(t.message ?: "통신 오류")
                }
            })
    }
}