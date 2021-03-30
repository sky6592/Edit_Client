package com.doublejj.edit.data.models.sentence

import com.google.gson.annotations.SerializedName

data class ReportSentenceRequest (
    @SerializedName("coverLetterId") val coverLetterId: Long
)