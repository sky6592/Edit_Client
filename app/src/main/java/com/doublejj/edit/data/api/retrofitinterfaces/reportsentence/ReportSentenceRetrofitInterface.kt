package com.doublejj.edit.data.api.retrofitinterfaces.reportsentence

import com.doublejj.edit.data.models.ResultResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportSentenceRetrofitInterface {
    @POST("/api/declare-cover-letters")
    fun postReportSentence(
        @Body coverLetterId: Long
    ) : Call<ResultResponse>
}