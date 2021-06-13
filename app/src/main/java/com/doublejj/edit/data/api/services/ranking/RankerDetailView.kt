package com.doublejj.edit.data.api.services.ranking

import com.doublejj.edit.data.models.ranking.RankerDetailResponse

interface RankerDetailView {
    fun onGetRankerDetailSuccess(response: RankerDetailResponse)
    fun onGetRankerDetailFailure(message: String)
}