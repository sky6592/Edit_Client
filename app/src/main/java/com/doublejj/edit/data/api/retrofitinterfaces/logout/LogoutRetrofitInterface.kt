package com.doublejj.edit.data.api.retrofitinterfaces.logout

import com.doublejj.edit.data.models.BaseResponse
import retrofit2.Call
import retrofit2.http.POST

interface LogoutRetrofitInterface {
    @POST("/api/logout")
    fun postLogout(
    ) : Call<BaseResponse>
}