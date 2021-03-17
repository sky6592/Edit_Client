package com.doublejj.edit.data.models.writing_sentence

import com.google.gson.annotations.SerializedName

data class WritingSentenceRequest (
    @SerializedName("coverLetterCategoryId") var coverLetterCategoryId: Long?,
    @SerializedName("coverLetterContent") var coverLetterContent: String?
)