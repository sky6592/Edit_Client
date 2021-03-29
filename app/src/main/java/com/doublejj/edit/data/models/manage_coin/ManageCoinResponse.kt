package com.doublejj.edit.data.models.manage_coin

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class ManageCoinResponse(
    @SerializedName("result") val result: CoinDashboard
) : BaseResponse()

data class CoinDashboard(
    @SerializedName("coinCount") val coinCount: String,
    @SerializedName("appreciateCount") val appreciateCount: String,
    @SerializedName("adoptCount") val adoptCount: String
)