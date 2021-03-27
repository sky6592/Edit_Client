package com.doublejj.edit.data.api.services.emailfind

import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.data.api.retrofitinterfaces.emailfind.EmailFindRetrofitInterface
import com.doublejj.edit.data.models.emailfind.EmailFindRequest
import com.doublejj.edit.data.models.emailfind.EmailFindResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailFindService(val emailFindView: EmailFindView) {
    fun tryPostEmailFind(emailFindRequest: EmailFindRequest) {
        val emailFindRetrofitInterface =
            ApplicationClass.sRetrofit.create(EmailFindRetrofitInterface::class.java)
        emailFindRetrofitInterface.postEmailFind(emailFindRequest).enqueue(object :
            Callback<EmailFindResponse> {
            override fun onResponse(
                call: Call<EmailFindResponse>,
                response: Response<EmailFindResponse>
            ) {
                emailFindView.onPostEmailFindSuccess(response.body() as EmailFindResponse)
            }

            override fun onFailure(call: Call<EmailFindResponse>, t: Throwable) {
                emailFindView.onPostEmailFindFailure(t.message ?: "통신오류")
            }


        })
    }
}