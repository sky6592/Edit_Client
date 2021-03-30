package com.doublejj.edit.data.api.services.report_sentence

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.report_sentence.ReportSentenceRetrofitInterface
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.report_sentence.ReportSentenceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportSentenceService(val view: ReportSentenceView) {
    fun tryReportSentence(request: ReportSentenceRequest) {
        val retrofitInterface = sRetrofit.create(ReportSentenceRetrofitInterface::class.java)
        retrofitInterface
            .postReportSentence(request)
            .enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    view.onReportSentenceSuccess(response.body() as ResultResponse)
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    view.onReportSentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}