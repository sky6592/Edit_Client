package com.doublejj.edit.data.models.report_sentence

import com.google.gson.annotations.SerializedName

data class ReportSentenceRequest (
    @SerializedName("coverLetterId") val coverLetterId: Long
)