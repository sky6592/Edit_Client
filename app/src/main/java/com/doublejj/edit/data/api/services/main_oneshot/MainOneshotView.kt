package com.doublejj.edit.data.api.services.main_oneshot

import com.doublejj.edit.data.models.main_oneshot.MainOneshotResponse

interface MainOneshotView {
    fun onGetMainSentencesSuccess(response: MainOneshotResponse)
    fun onGetMainSentencesFailure(message: String)
}