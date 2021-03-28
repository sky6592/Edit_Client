package com.doublejj.edit.data.api.retrofitinterfaces.sentence

import com.doublejj.edit.data.models.sentence.SympathizeSentenceResponse
import retrofit2.Call
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SympathizeSentenceRetrofitInterface {
    @PATCH("/api/cover-letters/{cover-letter-id}/sympathize-cover-letters")
    fun patchSympathizeSentence(
        @Path("cover-letter-id") sentenceId: Long
    ) : Call<SympathizeSentenceResponse>
}