package com.doublejj.edit.data.api.retrofitinterfaces.myedit_info

import com.doublejj.edit.data.models.myedit_info.ProfileInfoResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProfileInfoRetrofitInterface {
    @GET("/api/users/profile")
    fun getProfileInfo(
    ) : Call<ProfileInfoResponse>
}