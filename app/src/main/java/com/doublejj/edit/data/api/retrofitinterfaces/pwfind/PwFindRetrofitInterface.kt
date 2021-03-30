package com.doublejj.edit.data.api.retrofitinterfaces.pwfind

import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.infofirst.InfoFirstRequest
import com.doublejj.edit.data.models.infofirst.InfoFirstResponse
import com.doublejj.edit.data.models.pwfind.PwFindRequest
import com.doublejj.edit.data.models.pwfind.PwFindResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface PwFindRetrofitInterface {
    @POST("/api/users/temporary-password")
    fun postPwFind(@Body params: PwFindRequest): Call<PwFindResponse>
}