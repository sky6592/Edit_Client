package com.doublejj.edit.data.api.services.withdrawal

import com.doublejj.edit.data.models.withdrawal.WithdrawalResponse

interface WithdrawalView {
    fun onWithdrawalSuccess(response: WithdrawalResponse)
    fun onWithdrawalFailure(message: String)
}