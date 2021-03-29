package com.doublejj.edit.data.api.services.myedit_info

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.myedit_info.ProfileInfoRetrofitInterface
import com.doublejj.edit.data.models.myedit_info.ProfileInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileInfoService(val view: ProfileInfoView) {
    fun tryGetProfileInfo() {
        val retrofitInterface = sRetrofit.create(ProfileInfoRetrofitInterface::class.java)
        retrofitInterface
            .getProfileInfo()
            .enqueue(object : Callback<ProfileInfoResponse> {
                override fun onResponse(
                    call: Call<ProfileInfoResponse>,
                    response: Response<ProfileInfoResponse>
                ) {
                    view.onProfileInfoSuccess(response.body() as ProfileInfoResponse)
                }

                override fun onFailure(call: Call<ProfileInfoResponse>, t: Throwable) {
                    view.onProfileInfoFailure(t.message ?: "통신 오류")
                }
            })
    }
}