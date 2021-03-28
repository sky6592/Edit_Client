package com.doublejj.edit.data.api.retrofitinterfaces.sentence

import com.doublejj.edit.data.models.ResultResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeletePublishedSentenceRetrofitInterface {
    @DELETE("/api/cover-letters/{cover-letters-id}")
    fun deletePublishedSentence(
        @Path("cover-letters-id") sentenceId: Long
    ) : Call<ResultResponse>
}