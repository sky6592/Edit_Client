package com.doublejj.edit.data.models.todaysentence

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.sentence.SentenceData
import com.google.gson.annotations.SerializedName

class TodaySentenceResponse (
    @SerializedName("result") val result: MutableList<SentenceData>
) : BaseResponse()