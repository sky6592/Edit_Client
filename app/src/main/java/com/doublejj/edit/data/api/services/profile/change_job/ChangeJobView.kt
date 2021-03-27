package com.doublejj.edit.data.api.services.profile.change_job

import com.doublejj.edit.data.models.BaseResponse

interface ChangeJobView {
    fun onChangeJobSuccess(response: BaseResponse)
    fun onChangeJobFailure(message: String)
}