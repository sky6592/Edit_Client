package com.doublejj.edit.data.api.retrofitinterfaces.profile.change_job

import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.profile.change_job.ChangeJobRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ChangeJobRetrofitInterface {
    @PATCH("/api/users/jobs")
    fun patchChangeJob(
        @Body request: ChangeJobRequest
    ) : Call<BaseResponse>
}