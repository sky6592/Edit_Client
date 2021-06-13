package com.doublejj.edit.data.models.ranking

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class RankerDetailResponse(
    @SerializedName("result") val result: UserRankDetailResult
) : BaseResponse()

data class UserRankDetailResult (
    @SerializedName("rankId") val rankId: Long,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("emotionName") val emotionName: String,
    @SerializedName("colorName") val colorName: String,
    @SerializedName("jobName") val jobName: String,

    @SerializedName("userRole") val userRole: String,
    @SerializedName("coverLetterCount") val coverLetterCount: Int,
    @SerializedName("coverLetterCompleteCount") val coverLetterCompleteCount: Int,
    @SerializedName("commentCount") val commentCount: Int,
    @SerializedName("commentAdoptCount") val commentAdoptCount: Int
)