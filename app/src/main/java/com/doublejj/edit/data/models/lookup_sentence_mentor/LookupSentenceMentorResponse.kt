package com.doublejj.edit.data.models.lookup_sentence_mentor

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.my_comment.MyCommentResult
import com.doublejj.edit.data.models.my_comment.UserInfoResult
import com.google.gson.annotations.SerializedName

class LookupSentenceMentorResponse (
    @SerializedName("result") val result: LookupSentenceMentorResult
) : BaseResponse()

data class LookupSentenceMentorResult (
    @SerializedName("commentRes") val commentRes: MyCommentResult,
    @SerializedName("coverLetterRes") val coverLetterRes: SentenceResult
)

data class SentenceResult(
    @SerializedName("coverLetterId") val coverLetterId: Long,
    @SerializedName("coverLetterCategoryName") val coverLetterCategoryName: String,
    @SerializedName("coverLetterContent") val coverLetterContent: String,
    @SerializedName("userInfo") val userInfo: UserInfoResult
)