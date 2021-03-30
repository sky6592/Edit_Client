package com.doublejj.edit.ui.modules.main.myedit.certificate_mentor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.ApplicationClass.Companion.sActivityList
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.logout.LogoutService
import com.doublejj.edit.data.api.services.logout.LogoutView
import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.databinding.ActivityCertificateLogoutBinding
import com.doublejj.edit.ui.modules.main.splash.SplashActivity
import com.doublejj.edit.ui.utils.dialog.CustomLoadingDialog
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class CertificateLogoutActivity : AppCompatActivity(), LogoutView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityCertificateLogoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_certificate_logout)

        // add activity at sActivityList
        sActivityList.add(this)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.btnLogout.setOnClickListener {
            // 로그아웃 API 적용
            LogoutService(this).tryPostLogout()
            CustomLoadingDialog(this).show()
        }
    }

    override fun onPostLogoutSuccess(response: BaseResponse) {
        if (response.isSuccess) {
            val editor = ApplicationClass.sSharedPreferences.edit()
            editor.putString(ApplicationClass.X_ACCESS_TOKEN, null)
            editor.putString(ApplicationClass.USER_POSITION, null)
            editor.putBoolean(ApplicationClass.MENTOR_AUTH_CONFIRM, false)
            editor.putString(ApplicationClass.USER_NICKNAME, null)
            editor.putString(ApplicationClass.USER_EMOTION, null)
            editor.putString(ApplicationClass.USER_COLOR, null)
            editor.commit()
            editor.apply()

            // 초기화면으로 가기
            val sendIntent = Intent(this, SplashActivity::class.java)
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(sendIntent)

            sActivityList.actFinish()
        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        CustomLoadingDialog(this).dismiss()
    }

    override fun onPostLogoutFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

        CustomLoadingDialog(this).dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        sActivityList.remove(this)
    }
}