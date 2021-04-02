package com.doublejj.edit.data.api.services.my_comment

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.my_comment.MyCommentRetrofitInterface
import com.doublejj.edit.data.models.my_comment.MyCommentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCommentService(val view: MyCommentView) {
    fun tryGetMyCommentList(page: Int) {
        val retrofitInterface = sRetrofit.create(MyCommentRetrofitInterface::class.java)
        retrofitInterface
            .getMyCommentList(page)
            .enqueue(object : Callback<MyCommentResponse> {
                override fun onResponse(
                    call: Call<MyCommentResponse>,
                    response: Response<MyCommentResponse>
                ) {
                    view.onGetMyCommentSuccess(response.body() as MyCommentResponse)
                }

                override fun onFailure(call: Call<MyCommentResponse>, t: Throwable) {
                    view.onGetMyCommentFailure(t.message ?: "통신 오류")
                }
            })
    }
}