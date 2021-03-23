package com.doublejj.edit.data.api.retrofitinterfaces.infofirst

import com.doublejj.edit.data.models.infofirst.InfoFirstRequest
import com.doublejj.edit.data.models.infofirst.InfoFirstResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface InfoFirstRetrofitInterface {
//    @GET("/api/users/duplication")
//    fun getNickname(@Query("nickName") nickName:String) : Call<InfoFirstResponse>

    @POST("/api/users/duplication")
    fun postNickname(@Body params: InfoFirstRequest): Call<InfoFirstResponse>
}