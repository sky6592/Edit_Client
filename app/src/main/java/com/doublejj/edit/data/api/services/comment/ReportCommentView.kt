package com.doublejj.edit.data.api.services.comment

import com.doublejj.edit.data.models.ResultResponse

interface ReportCommentView {
    fun onReportCommentSuccess(response: ResultResponse)
    fun onReportCommentFailure(message: String)
}