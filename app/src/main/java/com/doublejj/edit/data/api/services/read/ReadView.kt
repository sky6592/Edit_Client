package com.doublejj.edit.data.api.services.read

import com.doublejj.edit.data.models.read.ReadResponse

interface ReadView {
    fun onPostReadSuccess(response: ReadResponse)
    fun onPostReadFailure(message: String)
}