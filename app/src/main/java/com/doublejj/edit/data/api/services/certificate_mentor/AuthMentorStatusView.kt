package com.doublejj.edit.data.api.services.certificate_mentor

import com.doublejj.edit.data.models.certificate_mentor.AuthMentorStatusResponse

interface AuthMentorStatusView {
    fun onAuthMentorStatusSuccess(response: AuthMentorStatusResponse)
    fun onAuthMentorStatusFailure(message: String)
}