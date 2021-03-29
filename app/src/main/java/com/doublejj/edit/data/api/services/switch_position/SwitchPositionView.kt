package com.doublejj.edit.data.api.services.switch_position

import com.doublejj.edit.data.models.ResultResponse

interface SwitchPositionView {
    fun onSwitchPositionSuccess(response: ResultResponse)
    fun onSwitchPositionFailure(message: String)
}