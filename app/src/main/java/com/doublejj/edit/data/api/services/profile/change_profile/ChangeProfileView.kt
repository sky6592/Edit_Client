package com.doublejj.edit.data.api.services.profile.change_profile

import com.doublejj.edit.data.models.BaseResponse

interface ChangeProfileView {
    fun onChangeProfileSuccess(response: BaseResponse)
    fun onChangeProfileFailure(message: String)
}