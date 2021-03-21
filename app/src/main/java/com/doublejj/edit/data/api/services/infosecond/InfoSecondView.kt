package com.doublejj.edit.data.api.services.infosecond

import com.doublejj.edit.data.models.infosecond.InfoSecondResponse

interface InfoSecondView {
    fun onPostInfoSecondSuccess(response: InfoSecondResponse)
    fun onPostInfoSecondFailure(message: String)
}