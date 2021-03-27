package com.doublejj.edit.data.api.services.entercode

import com.doublejj.edit.data.models.entercode.EnterCodeResponse

interface EnterCodeView {
    fun onGetEnterCodeSuccess(response: EnterCodeResponse)
    fun onGetEnterCodeFailure(message: String)
}