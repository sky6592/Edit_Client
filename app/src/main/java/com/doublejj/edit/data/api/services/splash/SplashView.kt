package com.doublejj.edit.data.api.services.splash

import com.doublejj.edit.data.models.splash.SplashResponse

interface SplashView {
    fun onGetSplashSuccess(response: SplashResponse)
    fun onGetSplashFailure(message: String)
}