package com.doublejj.edit.data.api.services.profile.change_job

import com.doublejj.edit.ApplicationClass.Companion.sRetrofit
import com.doublejj.edit.data.api.retrofitinterfaces.profile.change_job.ChangeJobRetrofitInterface
import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.profile.change_job.ChangeJobRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeJobService(val view: ChangeJobView) {
    fun tryPatchChangeJob(request: ChangeJobRequest) {
        val retrofitInterface = sRetrofit.create(ChangeJobRetrofitInterface::class.java)
        retrofitInterface
            .patchChangeJob(request)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    view.onChangeJobSuccess(response.body() as BaseResponse)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onChangeJobFailure(t.message ?: "통신 오류")
                }
            })
    }
}