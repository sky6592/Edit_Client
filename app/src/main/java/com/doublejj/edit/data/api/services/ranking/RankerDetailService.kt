package com.doublejj.edit.data.api.services.ranking

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.ranking.RankerMenteeRetrofitInterface
import com.doublejj.edit.data.api.retrofitinterfaces.ranking.RankerMentorRetrofitInterface
import com.doublejj.edit.data.models.ranking.RankerDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankerDetailService(val view: RankerDetailView) {
    fun tryGetRankerMentor(userId: Long, rankId: Long) {
        val retrofitInterface = sRetrofit.create(RankerMentorRetrofitInterface::class.java)
        retrofitInterface
            .getRankerDetail(userId, rankId)
            .enqueue(object : Callback<RankerDetailResponse> {
                override fun onResponse(
                    call: Call<RankerDetailResponse>,
                    response: Response<RankerDetailResponse>
                ) {
                    view.onGetRankerDetailSuccess(response.body() as RankerDetailResponse)
                }

                override fun onFailure(call: Call<RankerDetailResponse>, t: Throwable) {
                    view.onGetRankerDetailFailure(t.message ?: "통신 오류")
                }
            })
    }

    fun tryGetRankerMentee(userId: Long, rankId: Long) {
        val retrofitInterface = sRetrofit.create(RankerMenteeRetrofitInterface::class.java)
        retrofitInterface
            .getRankerDetail(userId, rankId)
            .enqueue(object : Callback<RankerDetailResponse> {
                override fun onResponse(
                    call: Call<RankerDetailResponse>,
                    response: Response<RankerDetailResponse>
                ) {
                    view.onGetRankerDetailSuccess(response.body() as RankerDetailResponse)
                }

                override fun onFailure(call: Call<RankerDetailResponse>, t: Throwable) {
                    view.onGetRankerDetailFailure(t.message ?: "통신 오류")
                }
            })
    }
}