package com.doublejj.edit.data.models.reportsentence

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class ReportSentenceResponse (
    @SerializedName("result") val result: Long
) : BaseResponse()