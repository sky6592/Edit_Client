package com.doublejj.edit.data.models.profile.change_job

import com.google.gson.annotations.SerializedName

data class ChangeJobRequest(
    @SerializedName("jobName") val jobName: String,
    @SerializedName("etcJobName") val etcJobName: String
)
