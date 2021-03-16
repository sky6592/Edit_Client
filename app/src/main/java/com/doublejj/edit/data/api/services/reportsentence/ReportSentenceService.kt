package com.doublejj.edit.data.api.services.reportsentence

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.reportsentence.ReportSentenceRetrofitInterface
import com.doublejj.edit.data.models.reportsentence.ReportSentenceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportSentenceService(val view: ReportSentenceView) {
    fun tryReportSentence(coverLetterId: Long) {
        val retrofitInterface = sRetrofit.create(ReportSentenceRetrofitInterface::class.java)
        retrofitInterface
            .postReportSentence(coverLetterId)
            .enqueue(object : Callback<ReportSentenceResponse> {
                override fun onResponse(
                    call: Call<ReportSentenceResponse>,
                    response: Response<ReportSentenceResponse>
                ) {
                    view.onReportSentenceSuccess(response.body() as ReportSentenceResponse)
                }

                override fun onFailure(call: Call<ReportSentenceResponse>, t: Throwable) {
                    view.onReportSentenceFailure(t.message ?: "통신 오류")
                }
            })
    }
}