package com.doublejj.edit.data.models.ranking

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class RankingResponse(
    @SerializedName("result") val result: GetUserRankingListResult
) : BaseResponse()

data class GetUserRankingListResult (
    @SerializedName("getUserRankResList") val getUserRankResList: MutableList<UserRankingResult>
)

data class UserRankingResult (
    @SerializedName("rankId") val rankId: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("emotionName") val emotionName: String,
    @SerializedName("colorName") val colorName: String,
    @SerializedName("jobName") val jobName: String
)