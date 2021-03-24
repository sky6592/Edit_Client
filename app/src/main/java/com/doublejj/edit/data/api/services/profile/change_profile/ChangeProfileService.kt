package com.doublejj.edit.data.api.services.profile.change_profile

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.profile.change_profile.ChangeProfileRetrofitInterface
import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.profile.change_profile.ChangeProfileRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeProfileService(val view: ChangeProfileView) {
    fun tryPatchChangeProfile(request: ChangeProfileRequest) {
        val retrofitInterface = sRetrofit.create(ChangeProfileRetrofitInterface::class.java)
        retrofitInterface
            .patchChangeProfile(request)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    view.onChangeProfileSuccess(response.body() as BaseResponse)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onChangeProfileFailure(t.message ?: "통신 오류")
                }
            })
    }
}