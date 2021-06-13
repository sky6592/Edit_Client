package com.doublejj.edit.data.api.services.ranking

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.ranking.RankingRetrofitInterface
import com.doublejj.edit.data.models.ranking.RankingResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRankingService(val view: UserRankingView) {
    fun tryGetUserRanking(requestRankByRole: String) {
        val retrofitInterface = sRetrofit.create(RankingRetrofitInterface::class.java)
        retrofitInterface
            .getRanking(requestRankByRole)
            .enqueue(object : Callback<RankingResponse> {
                override fun onResponse(
                    call: Call<RankingResponse>,
                    response: Response<RankingResponse>
                ) {
                    view.onGetUserRankingSuccess(response.body() as RankingResponse)
                }

                override fun onFailure(call: Call<RankingResponse>, t: Throwable) {
                    view.onGetUserRankingFailure(t.message ?: "통신 오류")
                }
            })
    }
}