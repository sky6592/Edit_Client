package com.doublejj.edit.data.models.main_oneshot

import com.doublejj.edit.data.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class MainOneshotResponse (
    @SerializedName("result") val result: MainSentences,
) : BaseResponse()

data class MainSentences(
    @SerializedName("todayCoverLetters") val todayCoverLetters: MutableList<MainSentenceData>,
    @SerializedName("waitingForCommentCoverLetters") val waitingForCommentCoverLetters: MutableList<MainSentenceData>,
    @SerializedName("adoptedCoverLetters") val adoptedCoverLetters: MutableList<MainSentenceData>,
    @SerializedName("sympathiesCoverLetters") val sympathiesCoverLetters: MutableList<MainSentenceData>
)

data class MainSentenceData(
    @SerializedName("coverLetterId") val coverLetterId: Long,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("jobName") val jobName: String,
    @SerializedName("coverLetterCategoryName") val coverLetterCategoryName: String,
    @SerializedName("coverLetterContent") val coverLetterContent: String,
    @SerializedName("sympathiesCount") var sympathiesCount: Long,
    @SerializedName("sympathy") val sympathy: Boolean
)