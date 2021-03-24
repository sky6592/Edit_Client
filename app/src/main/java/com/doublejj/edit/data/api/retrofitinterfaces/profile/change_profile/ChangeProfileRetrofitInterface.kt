package com.doublejj.edit.data.api.retrofitinterfaces.profile.change_profile

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.profile.change_profile.ChangeProfileRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ChangeProfileRetrofitInterface {
    @PATCH("/api/users/profile")
    fun patchChangeProfile(
        @Body request: ChangeProfileRequest
    ) : Call<BaseResponse>
}