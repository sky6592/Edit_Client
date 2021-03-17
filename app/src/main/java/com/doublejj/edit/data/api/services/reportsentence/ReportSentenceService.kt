package com.doublejj.edit.data.api.services.reportsentence

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.reportsentence.ReportSentenceRetrofitInterface
import com.doublejj.edit.data.models.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportSentenceService(val view: ReportSentenceView) {
    fun tryReportSentence(coverLetterId: Long) {
        val retrofitInterface = sRetrofit.create(ReportSentenceRetrofitInterface::class.java)
        retrofitInterface
            .postReportSentence(coverLetterId)
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