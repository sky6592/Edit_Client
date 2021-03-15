package com.doublejj.edit.data.api.services.mainoneshot

import com.doublejj.edit.data.models.mainoneshot.MainOneshotResponse

interface MainOneshotView {
    fun onGetMainSentencesSuccess(response: MainOneshotResponse)
    fun onGetMainSentencesFailure(message: String)
}