package com.doublejj.edit.data.api.services.reportsentence

import com.doublejj.edit.data.models.reportsentence.ReportSentenceResponse

interface ReportSentenceView {
    fun onReportSentenceSuccess(response: ReportSentenceResponse)
    fun onReportSentenceFailure(message: String)
}