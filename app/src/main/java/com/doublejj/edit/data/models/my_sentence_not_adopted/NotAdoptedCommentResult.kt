package com.doublejj.edit.data.models.my_sentence_not_adopted

import com.google.gson.annotations.SerializedName

data class NotAdoptedCommentResult(
    @SerializedName("notAdoptedCommentContents") val notAdoptedCommentContents: String
)