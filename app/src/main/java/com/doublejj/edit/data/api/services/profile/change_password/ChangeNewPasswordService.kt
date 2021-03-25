package com.doublejj.edit.data.api.services.profile.change_password

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.profile.change_password.ChangeNewPasswordRetrofitInterface
import com.doublejj.edit.data.models.profile.change_password.ChangeNewPasswordRequest
import com.doublejj.edit.data.models.profile.change_password.ChangeNewPasswordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeNewPasswordService(val view: ChangeNewPasswordView) {
    fun tryPatchChangeNewPassword(request: ChangeNewPasswordRequest) {
        val retrofitInterface = sRetrofit.create(ChangeNewPasswordRetrofitInterface::class.java)
        retrofitInterface
            .patchChangeNewPassword(request)
            .enqueue(object : Callback<ChangeNewPasswordResponse> {
                override fun onResponse(
                    call: Call<ChangeNewPasswordResponse>,
                    response: Response<ChangeNewPasswordResponse>
                ) {
                    view.onChangeNewPasswordSuccess(response.body() as ChangeNewPasswordResponse)
                }

                override fun onFailure(call: Call<ChangeNewPasswordResponse>, t: Throwable) {
                    view.onChangeNewPasswordFailure(t.message ?: "통신 오류")
                }
            })
    }
}