package com.doublejj.edit.data.api.services.emailcheck

import com.doublejj.edit.data.models.emailcheck.EmailCheckResponse

interface EmailCheckVIew {
    fun onPostEmailCheckSuccess(response: EmailCheckResponse)
    fun onPostEmailCheckFailure(message: String)
}