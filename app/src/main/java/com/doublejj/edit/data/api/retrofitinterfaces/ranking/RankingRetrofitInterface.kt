package com.doublejj.edit.data.api.retrofitinterfaces.ranking

import com.doublejj.edit.data.models.ranking.RankingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RankingRetrofitInterface {
    @GET("/api/ranks")
    fun getRanking(
        @Query ("requestRankByRole") requestRankByRole: String
    ) : Call<RankingResponse>
}