package com.doublejj.edit.data.api.services.comment

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.comment.DeleteCommentRetrofitInterface
import com.doublejj.edit.data.models.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteCommentService(val view: DeleteCommentView) {
    fun tryDeleteComment(commentId: Long) {
        val retrofitInterface = sRetrofit.create(DeleteCommentRetrofitInterface::class.java)
        retrofitInterface
            .deleteComment(commentId)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    view.onDeleteCommentSuccess(response.body() as BaseResponse)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onDeleteCommentFailure(t.message ?: "통신 오류")
                }
            })
    }
}