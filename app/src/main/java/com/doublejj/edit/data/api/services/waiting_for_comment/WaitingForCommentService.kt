package com.doublejj.edit.data.api.services.waiting_for_comment

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.wating_for_comment.WaitingForCommentRetrofitInterface
import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WaitingForCommentService(val view: WaitingForCommentView) {
    fun tryGetWaitingCommentSentence(page: Int) {
        val retrofitInterface = sRetrofit.create(WaitingForCommentRetrofitInterface::class.java)
        retrofitInterface
            .getWaitingCommentSentence(page)
            .enqueue(object : Callback<LookupSentenceResponse> {
                override fun onResponse(
                    call: Call<LookupSentenceResponse>,
                    response: Response<LookupSentenceResponse>
                ) {
                    view.onGetWaitingCommentSentenceSuccess(response.body() as LookupSentenceResponse)
                }

                override fun onFailure(call: Call<LookupSentenceResponse>, t: Throwable) {
                    view.onGetWaitingCommentSentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}