package com.doublejj.edit.data.models.withdrawal

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.profile.change_password.NickNameResult
import com.google.gson.annotations.SerializedName

data class WithdrawalResponse(
    @SerializedName("result") val result: NickNameResult
) : BaseResponse()