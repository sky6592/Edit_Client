package com.doublejj.edit.data.api.retrofitinterfaces.infosecond

import com.doublejj.edit.data.models.infosecond.InfoSecondRequest
import com.doublejj.edit.data.models.infosecond.InfoSecondResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface InfoSecondRetrofitInterface {
    @POST("/api/users/duplication")
    fun postEmail(@Body params: InfoSecondRequest): Call<InfoSecondResponse>
}