package com.doublejj.edit.data.models.comment

import com.google.gson.annotations.SerializedName

data class ReportCommentRequest (
    @SerializedName("commentId") val commentId: Long
)