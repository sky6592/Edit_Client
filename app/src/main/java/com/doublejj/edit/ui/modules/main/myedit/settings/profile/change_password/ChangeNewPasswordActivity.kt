package com.doublejj.edit.ui.modules.main.myedit.settings.profile.change_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass.Companion.sActivityList
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.profile.change_password.ChangeNewPasswordService
import com.doublejj.edit.data.api.services.profile.change_password.ChangeNewPasswordView
import com.doublejj.edit.data.models.profile.change_password.ChangeNewPasswordRequest
import com.doublejj.edit.data.models.profile.change_password.ChangeNewPasswordResponse
import com.doublejj.edit.databinding.ActivityChangeNewPasswordBinding
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

class ChangeNewPasswordActivity : AppCompatActivity(), ChangeNewPasswordView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityChangeNewPasswordBinding

    // 비밀번호 정규식 : 영문 대소문자, 숫자, 특수문자 중 2가지 포함(8-16자)
    val pwPattern = "^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z~!`@#\$%^&*()_+=])(?=.*[0-9!@#\$%^&*]).{8,16}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_new_password)

        // add activity at sActivityList
        sActivityList.add(this)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        /** edittext new password **/
        binding.etNewPassword.addTextChangedListener(object : TextWatcher {
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
                    binding.tvNewPasswordCaption.text = getString(R.string.tv_password_input_reg)
                    binding.tvNewPasswordCaption.setTextColor(ContextCompat.getColor(applicationContext, R.color.red_light))
                    binding.btnNext.isEnabled = false
                }
                // if matches pattern
                else {
                    binding.tvNewPasswordCaption.text = getString(R.string.tv_password_input_reg)
                    binding.tvNewPasswordCaption.setTextColor(ContextCompat.getColor(applicationContext, R.color.gray_shadow))
                    binding.btnNext.isEnabled = true
                }
            }
        })
        /** edittext re-enter password **/
        binding.etReenterPassword.addTextChangedListener(object : TextWatcher {
            // gets triggered immediately after something is typed
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // gets triggered before the next input
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            // gets triggered during an input
            override fun afterTextChanged(s: Editable?) {
                // if not equals eachother
                if (s.toString() != binding.etNewPassword.text.toString()) {
                    binding.tvReenterPasswordCaption.text = getString(R.string.tv_password_input_not_correct)
                    binding.tvReenterPasswordCaption.setTextColor(ContextCompat.getColor(applicationContext, R.color.red_light))
                    binding.btnNext.isEnabled = false
                }
                // if equals eachother
                else {
                    binding.tvReenterPasswordCaption.text = getString(R.string.tv_password_input_correct)
                    binding.tvReenterPasswordCaption.setTextColor(ContextCompat.getColor(applicationContext, R.color.green_light))
                    binding.btnNext.isEnabled = true
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.btnNext.isEnabled) {
                // TODO : 서버 비밀번호랑 새로운 비밀번호랑 같은지 확인해서 같다면 비밀번호 수정 API
                ChangeNewPasswordService(this).tryPatchChangeNewPassword(
                    ChangeNewPasswordRequest(
                        password = binding.etNewPassword.text.toString(),
                        authenticationPassword = binding.etReenterPassword.text.toString() // 일치하는지 확인의 의미가 더 강함
                    )
                )
            }
        }
    }

    override fun onChangeNewPasswordSuccess(response: ChangeNewPasswordResponse) {
        if (response.isSuccess) {
            if (response.code == 1000) {
                // TODO : 다음 화면으로 이동
                val sendIntent = Intent(this, ChangePasswordCompleteActivity::class.java)
                sendIntent.putExtra("nickName", response.result.nickName)
                startActivity(sendIntent)
            }
            else {
                // TODO : 다르다면 이동하지 않고 캡션에 다르다고 캡션 텍스트, 색상 바꿔주기
            }
        }
    }

    override fun onChangeNewPasswordFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
    }

    override fun onDestroy() {
        super.onDestroy()
        sActivityList.remove(this)
    }
}