package com.doublejj.edit.data.api.retrofitinterfaces.read

import com.doublejj.edit.data.models.infofirst.InfoFirstRequest
import com.doublejj.edit.data.models.infofirst.InfoFirstResponse
import com.doublejj.edit.data.models.read.ReadRequest
import com.doublejj.edit.data.models.read.ReadResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReadRetrofitInterpace {
    @POST("/api/users")
    fun postUsers(@Body params: ReadRequest): Call<ReadResponse>
}