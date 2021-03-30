package com.doublejj.edit.data.api.services.sentence

import com.doublejj.edit.data.models.ResultResponse

interface ReportSentenceView {
    fun onReportSentenceSuccess(response: ResultResponse)
    fun onReportSentenceFailure(message: String)
}