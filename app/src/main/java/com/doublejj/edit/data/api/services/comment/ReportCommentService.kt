package com.doublejj.edit.data.api.services.comment

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.comment.ReportCommentRetrofitInterface
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.comment.ReportCommentRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportCommentService(val view: ReportCommentView) {
    fun tryReportComment(request: ReportCommentRequest) {
        val retrofitInterface = sRetrofit.create(ReportCommentRetrofitInterface::class.java)
        retrofitInterface
            .postReportComment(request)
            .enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    view.onReportCommentSuccess(response.body() as ResultResponse)
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    view.onReportCommentFailure(t.message ?: "통신 오류")
                }
            })
    }
}