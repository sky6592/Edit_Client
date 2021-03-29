package com.doublejj.edit.data.api.retrofitinterfaces.switch_position

import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.switch_position.SwitchPositionRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface SwitchToMenteeRetrofitInterface {
    @PATCH("/api/change-roles/to-mentee")
    fun patchSwitchToMentee(
        @Body request: SwitchPositionRequest
    ) : Call<ResultResponse>
}