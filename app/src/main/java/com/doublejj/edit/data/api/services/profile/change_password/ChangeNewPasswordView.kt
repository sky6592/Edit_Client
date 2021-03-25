package com.doublejj.edit.data.api.services.profile.change_password

import com.doublejj.edit.data.models.profile.change_password.ChangeNewPasswordResponse

interface ChangeNewPasswordView {
    fun onChangeNewPasswordSuccess(response: ChangeNewPasswordResponse)
    fun onChangeNewPasswordFailure(message: String)
}