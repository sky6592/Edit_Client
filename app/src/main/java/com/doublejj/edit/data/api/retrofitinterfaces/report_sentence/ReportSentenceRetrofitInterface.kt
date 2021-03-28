package com.doublejj.edit.data.api.retrofitinterfaces.report_sentence

import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.report_sentence.ReportSentenceRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportSentenceRetrofitInterface {
    @POST("/api/declare-cover-letters")
    fun postReportSentence(
        @Body request: ReportSentenceRequest
    ) : Call<ResultResponse>
}