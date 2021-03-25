package com.doublejj.edit.data.models.withdrawal

import com.google.gson.annotations.SerializedName

data class WithdrawalRequest(
    @SerializedName("withdrawalContent") val withdrawalContent: String,
    @SerializedName("etcWithdrawalContent") val etcWithdrawalContent: String
)
