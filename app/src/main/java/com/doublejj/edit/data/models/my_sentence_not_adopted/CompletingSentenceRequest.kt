package com.doublejj.edit.data.models.my_sentence_not_adopted

import com.google.gson.annotations.SerializedName

data class CompletingSentenceRequest (
    @SerializedName("originalCoverLetterId") var originalCoverLetterId: Long?,
    @SerializedName("coverLetterContent") var coverLetterContent: String?
)