package com.doublejj.edit.data.api.retrofitinterfaces.comment

import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.comment.ReportCommentRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportCommentRetrofitInterface {
    @POST("/api/declare-comments")
    fun postReportComment(
        @Body request : ReportCommentRequest
    ) : Call<ResultResponse>
}