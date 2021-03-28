package com.doublejj.edit.data.api.services.logout

import com.doublejj.edit.data.models.BaseResponse

interface LogoutView {
    fun onPostLogoutSuccess(response: BaseResponse)
    fun onPostLogoutFailure(message: String)
}