package com.doublejj.edit.data.api.services.profile.info

import com.doublejj.edit.data.models.profile.info.ProfileInfoResponse

interface ProfileInfoView {
    fun onProfileInfoSuccess(response: ProfileInfoResponse)
    fun onProfileInfoFailure(message: String)
}