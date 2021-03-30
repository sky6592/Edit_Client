package com.doublejj.edit.data.api.services.comment

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.comment.AdoptCommentRetrofitInterface
import com.doublejj.edit.data.models.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdoptCommentService(val view: AdoptCommentView) {
    fun tryAdoptComment(commentId: Long) {
        val retrofitInterface = sRetrofit.create(AdoptCommentRetrofitInterface::class.java)
        retrofitInterface
            .patchAdoptComment(commentId)
            .enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    view.onAdoptCommentSuccess(response.body() as ResultResponse)
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    view.onAdoptCommentFailure(t.message ?: "통신 오류")
                }
            })
    }
}