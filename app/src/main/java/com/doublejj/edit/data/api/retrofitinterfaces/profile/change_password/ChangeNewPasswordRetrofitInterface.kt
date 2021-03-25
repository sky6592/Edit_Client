package com.doublejj.edit.data.api.retrofitinterfaces.profile.change_password

import com.doublejj.edit.data.models.profile.change_password.ChangeNewPasswordRequest
import com.doublejj.edit.data.models.profile.change_password.ChangeNewPasswordResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ChangeNewPasswordRetrofitInterface {
    @PATCH("/api/users/password")
    fun patchChangeNewPassword(
        @Body request: ChangeNewPasswordRequest
    ) : Call<ChangeNewPasswordResponse>
}