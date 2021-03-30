package com.doublejj.edit.data.api.services.comment

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.comment.WritingCommentRetrofitInterface
import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.writing_comment.WritingCommentRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WritingCommentService(val view: WritingCommentView) {
    fun tryPostWritingComment(request: WritingCommentRequest) {
        val retrofitInterface = sRetrofit.create(WritingCommentRetrofitInterface::class.java)
        retrofitInterface
            .postWritingComment(request)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    view.onWritingCommentSuccess(response.body() as BaseResponse)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onWritingCommentFailure(t.message ?: "통신 오류")
                }
            })
    }
}