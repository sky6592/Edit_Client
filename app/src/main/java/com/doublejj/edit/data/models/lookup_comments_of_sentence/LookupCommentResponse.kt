package com.doublejj.edit.data.models.lookup_comments_of_sentence

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.comment.CommentData
import com.doublejj.edit.data.models.sentence.SentenceData
import com.google.gson.annotations.SerializedName

class LookupCommentResponse (
    @SerializedName("result") val result: CommentsOfSentence
) : BaseResponse()

data class CommentsOfSentence(
    @SerializedName("coverLetterInfo") val coverLetterInfo: SentenceData,
    @SerializedName("commentInfos") val commentInfos: MutableList<CommentData>
)