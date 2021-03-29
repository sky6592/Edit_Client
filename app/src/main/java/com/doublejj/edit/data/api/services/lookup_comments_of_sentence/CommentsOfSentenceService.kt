package com.doublejj.edit.data.api.services.lookup_comments_of_sentence

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.lookup_comments_of_sentence.CommentsOfSentenceRetrofitInterface
import com.doublejj.edit.data.models.lookup_comments_of_sentence.LookupCommentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsOfSentenceService(val view: CommentsOfSentenceView) {
    fun tryGetCommentsOfSentence(sentenceId: Long, page: Int) {
        val retrofitInterface = sRetrofit.create(CommentsOfSentenceRetrofitInterface::class.java)
        retrofitInterface
            .getCommentsOfSentence(sentenceId, page)
            .enqueue(object : Callback<LookupCommentResponse> {
                override fun onResponse(
                    call: Call<LookupCommentResponse>,
                    response: Response<LookupCommentResponse>
                ) {
                    view.onGetCommentsOfSentenceSuccess(response.body() as LookupCommentResponse)
                }

                override fun onFailure(call: Call<LookupCommentResponse>, t: Throwable) {
                    view.onGetCommentsOfSentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}