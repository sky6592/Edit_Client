package com.doublejj.edit.ui.modules.main.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.login.LogInService
import com.doublejj.edit.data.api.services.login.LogInView
import com.doublejj.edit.data.models.login.LoginResponse
import com.doublejj.edit.data.models.login.LoginRequest
import com.doublejj.edit.databinding.ActivityLogInBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.assistance.email.EmailFindActivity
import com.doublejj.edit.ui.modules.main.assistance.pw.PwFindActivity
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar
import com.doublejj.edit.ApplicationClass

class LogInActivity : AppCompatActivity(), LogInView {

    private lateinit var mBinding: ActivityLogInBinding
    private var mEmailBtnFlag: Boolean = false
    private var mPwBtnFlag: Boolean = false

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        //이메일 정규식
        val emailPatternTest = android.util.Patterns.EMAIL_ADDRESS
        //비밀번호 정규식
        val pwPattern =
            "^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z~!`@#\$%^&*()_+=])(?=.*[0-9~!`@#\$%^&*()_+=]).{8,15}$"


        //이메일 입력
        mBinding.etEmailLogIn.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //띄어쓰기 정규식
                    if (s.toString().contains(" ") || s.toString().isEmpty()) {
                        mBinding.btnLogIn.setBackgroundResource(R.color.very_light_pink)
                        mBinding.tvEmailCaptionLogIn.setTextColor(R.color.purple)
                        mBinding.tvEmailCaptionLogIn.text =
                            getString(R.string.tv_caption_spacing_info)
//                        mEmailSpacingFlag = true
                        mEmailBtnFlag = false
                    } else {
//                        mEmailSpacingFlag = false
                    }
                    //이메일 정규식 - 중복확인을 누르세요!
                    if (emailPatternTest.matcher(s.toString()).matches()) {
                        mBinding.tvEmailCaptionLogIn.setTextColor(R.color.purple)
                        mBinding.tvEmailCaptionLogIn.text =
                            getString(R.string.tv_email_result_info)
                        mEmailBtnFlag = true
                    }
                    //입력 완료여부 정규식
                    if (mEmailBtnFlag && mPwBtnFlag) {
                        mBinding.btnLogIn.setBackgroundResource(R.color.purple)
                    }
                    Log.d("sky", "이메일 엔 : $mEmailBtnFlag, $mPwBtnFlag")

                }
            }

        })

        //비밀번호 입력
        mBinding.etPwLogIn.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //엔터 입력 : 다른 내용 입력 되어있는지 확인
                    if (s.toString().contains("\n")) {
                        if (mEmailBtnFlag && mPwBtnFlag) {
                            mBinding.btnLogIn.setBackgroundResource(R.color.purple)
                        }
                    }
                    //띄어쓰기 정규식
                    if (s.toString().contains(" ") || s.toString().isEmpty()) {
                        mBinding.tvPwCaptionLogIn.text =
                            getString(R.string.tv_caption_spacing_info)
                        mPwBtnFlag = false
                        mBinding.tvEmailCaptionLogIn.text =
                            getString(R.string.tv_pw_result_info)
                        mBinding.btnLogIn.setBackgroundResource(R.color.very_light_pink)
                    } else {
                        mBinding.tvPwCaptionLogIn.text = getString(R.string.tv_pw_caption_info)
                    }


                    //비밀번호 정규식
                    if (s.toString().matches(pwPattern.toRegex())) {
                        mBinding.tvPwCaptionLogIn.text = ""
                        mPwBtnFlag = true
                    } else {
                        mBinding.tvPwCaptionLogIn.setTextColor(R.color.purple)
                        mBinding.tvPwCaptionLogIn.text =
                            getString(R.string.tv_pw_caption_info)
                        mPwBtnFlag = false
                    }
                    //입력 완료여부 정규식
                    if (mEmailBtnFlag && mPwBtnFlag) {
                        mBinding.btnLogIn.setBackgroundResource(R.color.purple)
                    }
                }
            }
        })

        //다음으로 버튼 클릭
        mBinding.btnLogIn.setOnClickListener {
            var email = mBinding.etEmailLogIn.text.trim().toString()
            var password = mBinding.etPwLogIn.text.trim().toString()

            if (mEmailBtnFlag && mPwBtnFlag) {
                mEmailBtnFlag = false
                mPwBtnFlag = false
                mBinding.btnLogIn.setBackgroundResource(R.color.purple)

                //로그인 api
                val postRequest = LoginRequest(email = email, password = password)
                LogInService(this).tryPostLogin(postRequest)

            } else {
                //스낵바 : 로그인 재안내!
                mBinding.tvEmailCaptionLogIn.setTextColor(R.color.purple)
                mBinding.tvEmailCaptionLogIn.text = getString(R.string.tv_check_log_in)
            }
            Log.d("sky", "btn in - $mEmailBtnFlag + $mPwBtnFlag")
        }
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_find_pw_log_in -> {
                val intentFindEMail = Intent(this, PwFindActivity::class.java)
                startActivity(intentFindEMail)
            }
            R.id.tv_find_email_log_in -> {
                val intentFindEMail = Intent(this, EmailFindActivity::class.java)
                startActivity(intentFindEMail)
            }
        }
    }

    override fun onPostLoginSuccess(response: LoginResponse) {
        if (response.code == 1000) {
            val editor = ApplicationClass.sSharedPreferences.edit()
            editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.result.jwt)
//            editor.putBoolean(ApplicationClass.MENTOR_AUTH_CONFIRM,"가져온 값")
            editor.commit()
            editor.apply()
            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
            //모든화면종료
            finishAffinity()
        } else {
            CustomSnackbar.make(
                mBinding.root, "onPostLoginSuccess" +
                        response.message.toString(),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onPostLoginFailure(message: String) {
        CustomSnackbar.make(
            mBinding.root,
            message,
            Snackbar.LENGTH_LONG,
        )
            .show()
    }
}
