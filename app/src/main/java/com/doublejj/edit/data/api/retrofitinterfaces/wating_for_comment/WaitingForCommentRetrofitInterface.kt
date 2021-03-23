package com.doublejj.edit.data.api.retrofitinterfaces.wating_for_comment

import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WaitingForCommentRetrofitInterface {
    @GET("/api/waiting-for-comment-cover-letters")
    fun getWaitingCommentSentence(
        @Query("page") page: Int
    ) : Call<LookupSentenceResponse>
}