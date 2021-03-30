package com.doublejj.edit.ui.modules.main.myedit.profile.change_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass.Companion.sActivityList
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.profile.change_password.AuthPasswordService
import com.doublejj.edit.data.api.services.profile.change_password.AuthPasswordView
import com.doublejj.edit.data.models.ResultStringResponse
import com.doublejj.edit.data.models.profile.change_password.AuthPasswordRequest
import com.doublejj.edit.databinding.ActivityChangePasswordBinding
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

class ChangePasswordActivity : AppCompatActivity(), AuthPasswordView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityChangePasswordBinding

    // 비밀번호 정규식 : 영문 대소문자, 숫자, 특수문자 중 2가지 포함(8-16자)
    val pwPattern = "^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z~!`@#\$%^&*()_+=])(?=.*[0-9!@#\$%^&*]).{8,16}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)

        // add activity at sActivityList
        sActivityList.add(this)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        /** edittext old password **/
        binding.etOldPassword.addTextChangedListener(object : TextWatcher {
            // gets triggered immediately after something is typed
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // gets triggered before the next input
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            // gets triggered during an input
            override fun afterTextChanged(s: Editable?) {
                // if not matches pattern
                if (!Pattern.matches(pwPattern, s.toString())) {
                    binding.tvPasswordCaption.text = getString(R.string.tv_password_input_reg)
                    binding.tvPasswordCaption.setTextColor(ContextCompat.getColor(applicationContext, R.color.red_light))
                    binding.btnNext.isEnabled = false
                }
                // if matches pattern
                else {
                    binding.tvPasswordCaption.text = getString(R.string.tv_password_input_reg)
                    binding.tvPasswordCaption.setTextColor(ContextCompat.getColor(applicationContext, R.color.gray_shadow))
                    binding.btnNext.isEnabled = true
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.btnNext.isEnabled) {
                // 비밀번호 인증 API로 서버 비밀번호랑 같은지 확인
                val password = binding.etOldPassword.text.toString()
                AuthPasswordService(this).tryPostAuthPassword(request = AuthPasswordRequest(password))
            }
        }
    }

    override fun onAuthPasswordSuccess(response: ResultStringResponse) {
        if (response.isSuccess) {
            if (response.result == "YES") {
                // 같다면 다음 화면으로 이동
                val sendIntent = Intent(this, ChangeNewPasswordActivity::class.java)
                startActivity(sendIntent)
            }
            else {
                // 다르다면 이동하지 않고 캡션 텍스트, 색상 바꿔주기
                binding.tvPasswordCaption.text = getString(R.string.tv_password_input_not_correct)
                binding.tvPasswordCaption.setTextColor(ContextCompat.getColor(applicationContext, R.color.red_light))
            }
        }
    }

    override fun onAuthPasswordFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        sActivityList.remove(this)
    }
}