package com.doublejj.edit.data.api.retrofitinterfaces.my_sentence_not_adopted

import com.doublejj.edit.data.models.my_sentence_not_adopted.MySentenceNotAdoptedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MySentenceNotAdoptedRetrofitInterface {
    @GET("/api/my-writing-cover-letters")
    fun getMySentenceNotAdopted(
        @Query("page") page : Int
    ) : Call<MySentenceNotAdoptedResponse>
}