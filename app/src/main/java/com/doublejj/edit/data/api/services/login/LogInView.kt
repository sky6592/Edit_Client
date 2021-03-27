package com.doublejj.edit.data.api.services.login

import com.doublejj.edit.data.models.login.LoginResponse

interface LogInView {
    fun onPostLoginSuccess(response: LoginResponse)
    fun onPostLoginFailure(message: String)
}