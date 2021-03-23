package com.doublejj.edit.data.api.retrofitinterfaces.lookup_comments_of_sentence

import com.doublejj.edit.data.models.lookup_comments_of_sentence.LookupCommentResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentsOfSentenceRetrofitInterface {
    @GET("/api/cover-letters/{cover-letter-id}/comments")
    fun getCommentsOfSentence(
        @Path("cover-letter-id") sentenceId: Int,
        @Query("page") page: Int
    ) : Call<LookupCommentResponse>
}