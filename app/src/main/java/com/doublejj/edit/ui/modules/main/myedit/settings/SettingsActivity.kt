package com.doublejj.edit.ui.modules.main.myedit.settings

import android.content.Intent
import android.content.pm.PackageInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivitySettingsBinding
import com.doublejj.edit.ui.modules.main.myedit.settings.withdrawal.WithdrawalActivity
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class SettingsActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }
        
        /** setting buttons **/
        binding.llBtnNotice.setOnClickListener {
            CustomSnackbar.make(binding.root, getString(R.string.tv_to_be_develop), Snackbar.LENGTH_SHORT).show()
        }
        binding.llBtnServiceCenter.setOnClickListener { 
            // TODO : 고객센터 페이지
            CustomSnackbar.make(binding.root, getString(R.string.tv_to_be_develop), Snackbar.LENGTH_SHORT).show()
        }
        binding.llBtnOpensource.setOnClickListener { 
            // TODO : 오픈소스 페이지
            CustomSnackbar.make(binding.root, getString(R.string.tv_to_be_develop), Snackbar.LENGTH_SHORT).show()
        }
        // TODO : 버전정보 페이지
//        binding.llBtnAppVersion.setOnClickListener {
//        }
//        binding.

        // 회원탈퇴 페이지
        binding.llBtnAccountWithdrawal.setOnClickListener { 
            val intent = Intent(this, WithdrawalActivity::class.java)
            startActivity(intent)
        }
    }

    fun getVersionInfo() : String {
        val info: PackageInfo = applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0)
        return info.versionName
    }
}