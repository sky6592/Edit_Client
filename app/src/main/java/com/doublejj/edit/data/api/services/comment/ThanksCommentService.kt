package com.doublejj.edit.data.api.services.comment

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.comment.ThanksCommentRetrofitInterface
import com.doublejj.edit.data.models.comment.ThanksCommentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThanksCommentService(val view: ThanksCommentView) {
    fun tryThanksComment(commentId: Long) {
        val retrofitInterface = sRetrofit.create(ThanksCommentRetrofitInterface::class.java)
        retrofitInterface
            .patchThanksComment(commentId)
            .enqueue(object : Callback<ThanksCommentResponse> {
                override fun onResponse(
                    call: Call<ThanksCommentResponse>,
                    response: Response<ThanksCommentResponse>
                ) {
                    view.onThanksCommentSuccess(response.body() as ThanksCommentResponse)
                }

                override fun onFailure(call: Call<ThanksCommentResponse>, t: Throwable) {
                    view.onThanksCommentFailure(t.message ?: "통신 오류")
                }
            })
    }
}