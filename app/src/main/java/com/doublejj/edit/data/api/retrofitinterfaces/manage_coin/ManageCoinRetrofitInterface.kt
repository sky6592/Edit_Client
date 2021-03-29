package com.doublejj.edit.data.api.retrofitinterfaces.manage_coin

import com.doublejj.edit.data.models.manage_coin.ManageCoinResponse
import retrofit2.Call
import retrofit2.http.GET

interface ManageCoinRetrofitInterface {
    @GET("/api/users/coins")
    fun getManageCoin(
    ) : Call<ManageCoinResponse>
}