package com.doublejj.edit.data.api.services.switch_position

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.switch_position.SwitchToMentorRetrofitInterface
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.switch_position.SwitchPositionRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SwitchToMentorService(val view: SwitchPositionView) {
    fun tryPatchSwitchToMentor(request: SwitchPositionRequest) {
        val retrofitInterface = sRetrofit.create(SwitchToMentorRetrofitInterface::class.java)
        retrofitInterface
            .patchSwitchToMentor(request)
            .enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    view.onSwitchPositionSuccess(response.body() as ResultResponse)
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    view.onSwitchPositionFailure(t.message ?: "통신 오류")
                }
            })
    }
}