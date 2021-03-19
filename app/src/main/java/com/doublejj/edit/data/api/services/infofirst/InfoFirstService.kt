package com.doublejj.edit.data.api.services.infofirst

import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.data.api.retrofitinterfaces.infofirst.InfoFirstRetrofitInterface
import com.doublejj.edit.data.models.infofirst.InfoFirstRequest
import com.doublejj.edit.data.models.infofirst.InfoFirstResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class  InfoFirstService(val infoFirstView: InfoFirstView) {
//    fun tryGetNickName(nickname: String) {
//        val infoFirstRetrofitInterface =
//            ApplicationClass.sRetrofit.create(InfoFirstRetrofitInterface::class.java)
//        infoFirstRetrofitInterface.getNickname(nickname)
//            .enqueue(object : Callback<InfoFirstResponse> {
//                override fun onResponse(
//                    call: Call<InfoFirstResponse>,
//                    response: Response<InfoFirstResponse>
//                ) {
//                    infoFirstView.onPostInfoFirstSuccess(response.body() as InfoFirstResponse)
//                }
//
//                override fun onFailure(call: Call<InfoFirstResponse>, t: Throwable) {
//                    infoFirstView.onPostInfoFirstFailure(t.message ?: "통신 오류")
//                }
//            })
//    }


    fun tryPostNickName(postInfoFirstRequest: InfoFirstRequest){
        val infoFirstRetrofitInterface = ApplicationClass.sRetrofit.create(InfoFirstRetrofitInterface::class.java)
        infoFirstRetrofitInterface.postNickname(postInfoFirstRequest).enqueue(object :
            Callback<InfoFirstResponse> {
            override fun onResponse(
                call: Call<InfoFirstResponse>,
                response: Response<InfoFirstResponse>
            ) {
                infoFirstView.onPostInfoFirstSuccess(response.body() as InfoFirstResponse)
            }

            override fun onFailure(call: Call<InfoFirstResponse>, t: Throwable) {
                infoFirstView.onPostInfoFirstFailure(t.message ?: "통신 오류")
            }

        })

    }

}