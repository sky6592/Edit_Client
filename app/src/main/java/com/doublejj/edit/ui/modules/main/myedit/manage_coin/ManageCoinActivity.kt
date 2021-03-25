package com.doublejj.edit.ui.modules.main.myedit.manage_coin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.manage_coin.ManageCoinService
import com.doublejj.edit.data.api.services.manage_coin.ManageCoinView
import com.doublejj.edit.data.models.manage_coin.ManageCoinResponse
import com.doublejj.edit.databinding.ActivityManageCoinBinding
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class ManageCoinActivity : AppCompatActivity(), ManageCoinView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityManageCoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_coin)

        /** get manage coin API **/
        ManageCoinService(this).tryGetManageCoin()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }
    }

    override fun onGetManageCoinSuccess(response: ManageCoinResponse) {
        if (response.isSuccess) {
            binding.tvCoinWallet.text = response.result.coinCount
            binding.tvCoinThanks.text = response.result.appreciateCount
            binding.tvCoinAdoption.text = response.result.adoptCount
        }
    }

    override fun onGetManageCoinFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
    }
}