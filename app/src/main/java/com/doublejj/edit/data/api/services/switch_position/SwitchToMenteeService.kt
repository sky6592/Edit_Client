package com.doublejj.edit.data.api.services.switch_position

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.switch_position.SwitchToMenteeRetrofitInterface
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.switch_position.SwitchPositionRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SwitchToMenteeService(val view: SwitchPositionView) {
    fun tryPatchSwitchToMentee(request: SwitchPositionRequest) {
        val retrofitInterface = sRetrofit.create(SwitchToMenteeRetrofitInterface::class.java)
        retrofitInterface
            .patchSwitchToMentee(request)
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