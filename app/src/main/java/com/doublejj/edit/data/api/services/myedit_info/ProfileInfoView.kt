package com.doublejj.edit.data.api.services.myedit_info

import com.doublejj.edit.data.models.myedit_info.ProfileInfoResponse

interface ProfileInfoView {
    fun onProfileInfoSuccess(response: ProfileInfoResponse)
    fun onProfileInfoFailure(message: String)
}