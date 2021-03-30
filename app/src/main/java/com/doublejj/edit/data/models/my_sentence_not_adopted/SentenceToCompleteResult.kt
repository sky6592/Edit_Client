package com.doublejj.edit.data.models.my_sentence_not_adopted

import com.google.gson.annotations.SerializedName

data class SentenceToCompleteResult(
    @SerializedName("originalCoverLetterId") val originalCoverLetterId: Long,
    @SerializedName("originalCoverLetterCategoryName") val originalCoverLetterCategoryName: String,
    @SerializedName("originalCoverLetterContent") val originalCoverLetterContent: String,
    @SerializedName("adoptedCommentContent") val adoptedCommentContent: String
)