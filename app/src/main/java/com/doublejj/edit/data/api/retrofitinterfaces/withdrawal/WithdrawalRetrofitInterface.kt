package com.doublejj.edit.data.api.retrofitinterfaces.withdrawal

import com.doublejj.edit.data.models.withdrawal.WithdrawalRequest
import com.doublejj.edit.data.models.withdrawal.WithdrawalResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HTTP

interface WithdrawalRetrofitInterface {
    // DELETE method에 body가 있는 경우 지원하지 않기에 예외적으로 추가
    @HTTP(method = "DELETE", path = "/api/users", hasBody = true)
    fun deleteWithdrawal(
        @Body request: WithdrawalRequest
    ) : Call<WithdrawalResponse>
}