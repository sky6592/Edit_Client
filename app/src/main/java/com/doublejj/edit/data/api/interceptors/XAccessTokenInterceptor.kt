package com.doublejj.edit.data.api.interceptors

import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class XAccessTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        // TODO : 테스트 jwt 지우기
        val jwtToken: String? = sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE3LCJyb2xlIjoiTUVOVE9SIiwiaWF0IjoxNjE2NzQxNzcxLCJleHAiOjE2MTc5NTEzNzF9._GZeqhOJpVYoyBIMR4tNkgiUpmP94wT1GSmxqh7f8nM")
//        val jwtToken: String? = sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
        if (jwtToken != null) {
            builder.addHeader("X-ACCESS-TOKEN", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}