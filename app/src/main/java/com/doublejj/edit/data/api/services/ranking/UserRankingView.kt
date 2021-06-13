package com.doublejj.edit.data.api.services.ranking

import com.doublejj.edit.data.models.ranking.RankingResponse

interface UserRankingView {
    fun onGetUserRankingSuccess(response: RankingResponse)
    fun onGetUserRankingFailure(message: String)
}