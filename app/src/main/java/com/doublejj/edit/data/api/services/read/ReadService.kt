package com.doublejj.edit.data.api.services.read

import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.data.api.retrofitinterfaces.infofirst.InfoFirstRetrofitInterface
import com.doublejj.edit.data.api.retrofitinterfaces.read.ReadRetrofitInterpace
import com.doublejj.edit.data.models.infofirst.InfoFirstRequest
import com.doublejj.edit.data.models.infofirst.InfoFirstResponse
import com.doublejj.edit.data.models.read.ReadRequest
import com.doublejj.edit.data.models.read.ReadResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadService(val readView: ReadView) {
    fun tryPostUsers(readRequest: ReadRequest) {
        val readRetrofitInterpace = ApplicationClass.sRetrofit.create(
            ReadRetrofitInterpace::class.java)
        readRetrofitInterpace.postUsers(readRequest).enqueue(object :
            Callback<ReadResponse> {
            override fun onResponse(call: Call<ReadResponse>, response: Response<ReadResponse>) {
                readView.onPostReadSuccess(response.body() as ReadResponse)
            }

            override fun onFailure(call: Call<ReadResponse>, t: Throwable) {
                readView.onPostReadFailure(t.message ?: "통신 오류")
            }


        })
    }
}