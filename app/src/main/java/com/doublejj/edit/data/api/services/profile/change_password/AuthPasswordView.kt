package com.doublejj.edit.data.api.services.profile.change_password

import com.doublejj.edit.data.models.ResultStringResponse

interface AuthPasswordView {
    fun onAuthPasswordSuccess(response: ResultStringResponse)
    fun onAuthPasswordFailure(message: String)
}