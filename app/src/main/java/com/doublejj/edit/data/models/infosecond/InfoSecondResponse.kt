package com.doublejj.edit.data.models.infosecond

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.infofirst.InfoFirstResult
import com.google.gson.annotations.SerializedName

data class InfoSecondResponse(
    @SerializedName("result") val result: InfoSecondResult
) : BaseResponse()