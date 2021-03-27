package com.doublejj.edit.data.api.services.emailfind

import com.doublejj.edit.data.models.emailfind.EmailFindResponse

interface EmailFindView {
    fun onPostEmailFindSuccess(response: EmailFindResponse)
    fun onPostEmailFindFailure(message: String)
}