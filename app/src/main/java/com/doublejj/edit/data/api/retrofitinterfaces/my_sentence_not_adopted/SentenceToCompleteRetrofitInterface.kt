package com.doublejj.edit.data.api.retrofitinterfaces.my_sentence_not_adopted

import com.doublejj.edit.data.models.my_sentence_not_adopted.SentenceToCompleteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SentenceToCompleteRetrofitInterface {
    @GET("/api/cover-letters/{cover-letter-id}/to-complete")
    fun getMySentenceNotAdopted(
        @Path("cover-letter-id") sentenceId: Long
    ) : Call<SentenceToCompleteResponse>
}