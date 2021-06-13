package com.doublejj.edit.data.api.retrofitinterfaces.ranking

import com.doublejj.edit.data.models.ranking.RankerDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RankerMentorRetrofitInterface {
    @GET("/api/ranks-mentor/users/{userId}")
    fun getRankerDetail(
        @Path("userId") userId: Long,
        @Query("rankId") rankId: Long
    ) : Call<RankerDetailResponse>
}