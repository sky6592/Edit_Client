package com.doublejj.edit.data.api.services.pwfind

import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.data.api.retrofitinterfaces.pwfind.PwFindRetrofitInterface
import com.doublejj.edit.data.models.infofirst.InfoFirstResponse
import com.doublejj.edit.data.models.pwfind.PwFindRequest
import com.doublejj.edit.data.models.pwfind.PwFindResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PwFindService(val pwFindView: PwFindView) {
    fun tryPostPwFind(postPwFindRequest: PwFindRequest) {
        val pwFindRetrofitInterface = ApplicationClass.sRetrofit.create(
            PwFindRetrofitInterface::class.java
        )
        pwFindRetrofitInterface.postPwFind(postPwFindRequest).enqueue(object :
            Callback<PwFindResponse> {
            override fun onResponse(
                call: Call<PwFindResponse>,
                response: Response<PwFindResponse>
            ) {
                pwFindView.onPostPwFindSuccess(response.body() as PwFindResponse)
            }

            override fun onFailure(call: Call<PwFindResponse>, t: Throwable) {
                pwFindView.onPostPwFindFailure(t.message ?: "통신 오류")
            }
        })
    }
}