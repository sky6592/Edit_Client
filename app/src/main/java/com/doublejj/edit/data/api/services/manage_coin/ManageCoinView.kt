package com.doublejj.edit.data.api.services.manage_coin

import com.doublejj.edit.data.models.manage_coin.ManageCoinResponse

interface ManageCoinView {
    fun onGetManageCoinSuccess(response: ManageCoinResponse)
    fun onGetManageCoinFailure(message: String)
}