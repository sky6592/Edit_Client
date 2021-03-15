package com.doublejj.edit.data.api.services.todaysentence

import com.doublejj.edit.data.models.todaysentence.TodaySentenceResponse

interface TodaySentenceView {
    fun onGetTodaySentenceSuccess(response: TodaySentenceResponse)
    fun onGetTodaySentenceFailure(message: String)
}