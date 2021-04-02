package com.doublejj.edit.data.api.services.lookup_sentence_of_comment

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.lookup_sentence_of_comment.SentencesOfCommentRetrofitInterface
import com.doublejj.edit.data.models.lookup_sentence_mentor.LookupSentenceMentorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SentencesOfCommentService(val view: SentencesOfCommentView) {
    fun tryGetSentencesOfComment(commentId: Long) {
        val retrofitInterface = sRetrofit.create(SentencesOfCommentRetrofitInterface::class.java)
        retrofitInterface
            .getSentencesOfComment(commentId)
            .enqueue(object : Callback<LookupSentenceMentorResponse> {
                override fun onResponse(
                    call: Call<LookupSentenceMentorResponse>,
                    response: Response<LookupSentenceMentorResponse>
                ) {
                    view.onGetSentencesOfCommentSuccess(response.body() as LookupSentenceMentorResponse)
                }

                override fun onFailure(call: Call<LookupSentenceMentorResponse>, t: Throwable) {
                    view.onGetSentencesOfCommentFailure(t.message ?: "통신 오류")
                }
            })
    }
}