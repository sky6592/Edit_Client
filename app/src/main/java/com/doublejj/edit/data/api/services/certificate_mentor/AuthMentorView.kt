package com.doublejj.edit.data.api.services.certificate_mentor

import com.doublejj.edit.data.models.BaseResponse

interface AuthMentorView {
    fun onAuthMentorSuccess(response: BaseResponse)
    fun onAuthMentorFailure(message: String)
}