package com.doublejj.edit.data.api.retrofitinterfaces.lookup_sentence_of_comment

import com.doublejj.edit.data.models.lookup_sentence_mentor.LookupSentenceMentorResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SentencesOfCommentRetrofitInterface {
    @GET("/api/comments/{comment-id}/cover-letters")
    fun getSentencesOfComment(
        @Path("comment-id") commentId: Long
    ) : Call<LookupSentenceMentorResponse>
}