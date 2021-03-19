package com.doublejj.edit.data.api.services.infofirst

import com.doublejj.edit.data.models.infofirst.InfoFirstResponse
import com.doublejj.edit.data.models.login.LoginResponse

interface InfoFirstView {
    fun onPostInfoFirstSuccess(response: InfoFirstResponse)
    fun onPostInfoFirstFailure(message: String)
}