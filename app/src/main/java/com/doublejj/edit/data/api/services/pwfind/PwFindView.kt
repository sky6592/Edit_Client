package com.doublejj.edit.data.api.services.pwfind

import com.doublejj.edit.data.models.infofirst.InfoFirstResponse
import com.doublejj.edit.data.models.pwfind.PwFindResponse

interface PwFindView {
    fun onPostPwFindSuccess(response: PwFindResponse)
    fun onPostPwFindFailure(message: String)
}