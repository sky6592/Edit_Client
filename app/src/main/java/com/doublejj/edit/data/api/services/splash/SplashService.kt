package com.doublejj.edit.data.api.services.splash

import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.data.api.retrofitinterfaces.splash.SplashRetrofitInterface
import com.doublejj.edit.data.models.splash.SplashResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashService(val splashView: SplashView) {
    fun tryGetSplash() {
        val splashRetrofitInterface =
            ApplicationClass.sRetrofit.create(SplashRetrofitInterface::class.java)
        splashRetrofitInterface.getSplash()
            .enqueue(object : Callback<SplashResponse> {
                override fun onResponse(
                    call: Call<SplashResponse>,
                    response: Response<SplashResponse>
                ) {
                    splashView.onGetSplashSuccess(response.body() as SplashResponse)
                }

                override fun onFailure(call: Call<SplashResponse>, t: Throwable) {
                    splashView.onGetSplashFailure(t.message ?: "통신 오류")
                }
            })
    }
}