package com.doublejj.edit.ui.modules.main.assistance.email

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.emailfind.EmailFindService
import com.doublejj.edit.data.api.services.emailfind.EmailFindView
import com.doublejj.edit.data.api.services.login.LogInService
import com.doublejj.edit.data.models.emailfind.EmailFindRequest
import com.doublejj.edit.data.models.emailfind.EmailFindResponse
import com.doublejj.edit.data.models.login.LoginRequest
import com.doublejj.edit.databinding.ActivityEmailFindBinding
import com.doublejj.edit.databinding.DialogEmailFindBinding
import com.doublejj.edit.ui.modules.main.signup.infofirst.InfoFirstActivity
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class EmailFindActivity : AppCompatActivity(), EmailFindView {
    private lateinit var mBinding: ActivityEmailFindBinding
    private var mNameFlag: Boolean = false
    private var mPhoneFlag: Boolean = false
    private var mNameSpacingFlag: Boolean = false
    private var mPhoneSpacingFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_email_find)

        //정규식
        val namePattern = "^[a-zA-Z가-힣]{2,10}$"
        val phonePattern = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$"

        // 이름 입력
        mBinding.etNameEmailFind.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //띄어쓰기 & 빈공란 정규식
                    if (s.toString().contains(" ") || s.toString().isEmpty()) {
                        mBinding.tvNameCaptionEmailFind.setTextColor(R.color.purple)
                        mBinding.tvNameCaptionEmailFind.text =
                            getString(R.string.tv_caption_spacing_info)
                        mNameSpacingFlag = true
                        mNameFlag = false
                        mBinding.btnEmailFind.setBackgroundResource(R.color.very_light_pink)
                    } else {
                        mNameSpacingFlag = false
                    }

                    //엔터 입력 : 다른 내용 입력 되어있는지 확인
                    if (s.toString().contains("\n")) {
                        if (mPhoneFlag) {
                            mBinding.btnEmailFind.setBackgroundResource(R.color.purple)
                        }
                    }
                    //글자 기준
                    if (s.toString().length < 2 || s.toString().length > 10) {
                        mBinding.tvNameCaptionEmailFind.setTextColor(R.color.purple)
                        mBinding.tvNameCaptionEmailFind.text =
                            getString(R.string.tv_name_caption_rule_info)
                    }

                    //이름 정규식
                    if (s.matches(namePattern.toRegex()) && !mNameSpacingFlag) {
                        mBinding.tvNameCaptionEmailFind.setTextColor(R.color.purple)
                        mBinding.tvNameCaptionEmailFind.text =
                            getString(R.string.tv_name_result_email_find)
                        mNameFlag = true
                    }

                    //버튼 색상 확인
                    if (mNameFlag && mPhoneFlag) {
                        mBinding.btnEmailFind.setBackgroundResource(R.color.purple)
                    }


                }
            }
        })

        //핸드폰 입력
        mBinding.etPhoneEmailFind.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //띄어쓰기 작성
                    if (s.toString().contains(" ")) {
                        mBinding.tvPhoneCaptionEmailFind.setTextColor(R.color.purple)
                        mBinding.tvPhoneCaptionEmailFind.text =
                            getString(R.string.tv_caption_spacing_info)
                        mPhoneSpacingFlag = true
                        mPhoneFlag = false
                        mBinding.btnEmailFind.setBackgroundResource(R.color.very_light_pink)

                    } else {
                        mPhoneSpacingFlag = false
                    }

                    //엔터 입력 : 다른 내용 입력 되어있는지 확인
                    if (s.toString().contains("\n")) {
                        if (mNameFlag) {
                            mBinding.btnEmailFind.setBackgroundResource(R.color.purple)
                        }
                    }

                    // '-' 정규식
                    if (s.toString().contains("-")) {
                        mBinding.tvPhoneCaptionEmailFind.setTextColor(R.color.purple)
                        mBinding.tvPhoneCaptionEmailFind.text =
                            getString(R.string.hint_phone_info)
                        mBinding.btnEmailFind.setBackgroundResource(R.color.very_light_pink)
                        mPhoneFlag = false
                    }

                    //핸드폰 정규식
                    if (s.matches(phonePattern.toRegex()) && !mPhoneSpacingFlag) {
                        mBinding.tvPhoneCaptionEmailFind.setTextColor(R.color.purple)
                        mBinding.tvPhoneCaptionEmailFind.text =
                            getString(R.string.tv_phone_caption_result_info)
                        mPhoneFlag = true
                    }
                    //빈공란 정규식
                    if (s.toString().isEmpty()) {
                        mBinding.tvPhoneCaptionEmailFind.setTextColor(R.color.purple)
                        mBinding.tvPhoneCaptionEmailFind.text =
                            getString(R.string.tv_phone_caption_info)
                    }
                    //입력 완료여부 정규식
                    if (mNameFlag && mPhoneFlag) {
                        mBinding.btnEmailFind.setBackgroundResource(R.color.purple)
                    }
                }
            }

        })

        //다음으로 버튼 클릭
        mBinding.btnEmailFind.setOnClickListener {
            var name = mBinding.etNameEmailFind.text.trim().toString()
            var phoneNumber = mBinding.etPhoneEmailFind.text.trim().toString()

            Log.d("sky", "버튼클릭 : $name,$phoneNumber")
            if (mNameFlag && mPhoneFlag) {
                //로그인 api
                val postRequest = EmailFindRequest(name = name, phoneNumber = phoneNumber)
                EmailFindService(this).tryPostEmailFind(postRequest)

            } else {
                //스낵 : 이메일 정보
                CustomSnackbar.make(
                    mBinding.root,
                    getString(R.string.tv_check_info_find_email),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onPostEmailFindSuccess(response: EmailFindResponse) {
        val builder = AlertDialog.Builder(this)
        val binding: DialogEmailFindBinding = DialogEmailFindBinding.inflate(layoutInflater)
        if (response.code == 1000) {
            binding.tvDialogContentEmailFind.text =
                getString(R.string.tv_dialog_content_find_email) + "\n" + response.result.email
//            binding.tvDialogApiEmailFind.text = response.result.email
            builder.setPositiveButton("확인") { _, i ->

            }
            builder.setView(binding.root).show()
            Log.d("sky", response.result.email)

        }
        if (response.code == 3012) {
            binding.tvDialogTitleEmailFind.text = "가입되니 않은 계정입니다."
            binding.tvDialogContentEmailFind.text = "회원가입을 새로 하시겠어요?"
            binding.tvDialogApiEmailFind.text = "확인을 누르면 회원가입화면으로 이동합니다!"
            builder.setNegativeButton(getString(R.string.tv_dialog_dismiss)) { _, _ ->

            }
            builder.setPositiveButton(getString(R.string.tv_dialog_confirm)) { _, _ ->
                startActivity(Intent(this, InfoFirstActivity::class.java))
                //모든엑티비티 모두 종료
                finishAffinity()
            }
            builder.setView(binding.root).show()
        } else {
            Log.d("sky", "onPostEmailFindSuccess" + response.message.toString())
        }
    }

    override fun onPostEmailFindFailure(message: String) {
        Log.d("sky", "onPostEmailFindFailure : $message")
    }
}