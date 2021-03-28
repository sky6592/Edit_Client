package com.doublejj.edit.ui.modules.main.assistance.pw

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.emailfind.EmailFindService
import com.doublejj.edit.data.api.services.pwfind.PwFindService
import com.doublejj.edit.data.api.services.pwfind.PwFindView
import com.doublejj.edit.data.models.emailfind.EmailFindRequest
import com.doublejj.edit.data.models.pwfind.PwFindRequest
import com.doublejj.edit.data.models.pwfind.PwFindResponse
import com.doublejj.edit.databinding.ActivityPwFindBinding
import com.doublejj.edit.databinding.DialogEmailFindBinding
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar


//api 디비가 post로 변경해주면 테스트 그때 해보기!
class PwFindActivity : AppCompatActivity(), PwFindView {
    private lateinit var mBinding: ActivityPwFindBinding
    private var mNameFlag: Boolean = false
    private var mEmailFlag: Boolean = false
    private var mPhoneFlag: Boolean = false
    private var mNameSpacingFlag: Boolean = false
    private var mEmailSpacingFlag: Boolean = false
    private var mPhoneSpacingFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pw_find)

        //정규식
        val namePattern = "^[a-zA-Z가-힣]{2,10}$"
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        val phonePattern = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$"

        // 이름 입력
        mBinding.etNamePwFind.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //띄어쓰기 & 빈공란 정규식
                    if (s.toString().contains(" ") || s.toString().isEmpty()) {
                        mBinding.tvNameCaptionPwFind.setTextColor(R.color.purple)
                        mBinding.tvNameCaptionPwFind.text =
                            getString(R.string.tv_caption_spacing_info)
                        mNameSpacingFlag = true
                        mNameFlag = false
                        mBinding.btnPwFind.setBackgroundResource(R.color.very_light_pink)
                    } else {
                        mNameSpacingFlag = false
                    }

                    //엔터 입력 : 다른 내용 입력 되어있는지 확인
                    if (s.toString().contains("\n")) {
                        if (mEmailFlag && mPhoneFlag) {
                            mBinding.btnPwFind.setBackgroundResource(R.color.purple)
                        }
                    }
                    //글자 기준
                    if (s.toString().length < 2 || s.toString().length > 10) {
                        mBinding.tvNameCaptionPwFind.setTextColor(R.color.purple)
                        mBinding.tvNameCaptionPwFind.text =
                            getString(R.string.tv_name_caption_rule_info)
                    }

                    //이름 정규식
                    if (s.matches(namePattern.toRegex()) && !mNameSpacingFlag) {
                        mBinding.tvNameCaptionPwFind.setTextColor(R.color.purple)
                        mBinding.tvNameCaptionPwFind.text =
                            getString(R.string.tv_name_result_email_find)
                        mNameFlag = true
                    }

                    //버튼 색상 확인
                    if (mNameFlag && mEmailFlag && mPhoneFlag) {
                        mBinding.btnPwFind.setBackgroundResource(R.color.purple)
                    }
                }
            }
        })

        //이메일 입력
        mBinding.etEmailPwFind.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //띄어쓰기 정규식
                    if (s.toString().contains(" ") || s.toString().isEmpty()) {
                        mBinding.btnPwFind.setBackgroundResource(R.color.very_light_pink)
                        mBinding.tvCaptionEmailPwFind.setTextColor(R.color.purple)
                        mBinding.tvCaptionEmailPwFind.text =
                            getString(R.string.tv_caption_spacing_info)
                        mEmailSpacingFlag = true
                        mEmailFlag = false
                    } else {
                        mEmailSpacingFlag = false
                    }

                    //엔터 입력 : 다른 내용 입력 되어있는지 확인
                    if (s.toString().contains("\n")) {
                        if (mNameFlag && mPhoneFlag) {
                            mBinding.btnPwFind.setBackgroundResource(R.color.purple)
                        }
                    }

                    //이메일 정규식
                    if (emailPattern.matcher(s.toString()).matches() && !mEmailSpacingFlag) {
                        mBinding.tvCaptionEmailPwFind.setTextColor(R.color.purple)
                        mBinding.tvCaptionEmailPwFind.text =
                            getString(R.string.tv_email_result_info)
                    }

                    //버튼 색상 확인
                    if (mNameFlag && mEmailFlag && mPhoneFlag) {
                        mBinding.btnPwFind.setBackgroundResource(R.color.purple)
                    }
                }
            }
        })

        //핸드폰 입력
        mBinding.etPhonePwFind.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //띄어쓰기 작성
                    if (s.toString().contains(" ")) {
                        mBinding.tvPhoneCaptionPwFind.setTextColor(R.color.purple)
                        mBinding.tvPhoneCaptionPwFind.text =
                            getString(R.string.tv_caption_spacing_info)
                        mPhoneSpacingFlag = true
                        mPhoneFlag = false
                        mBinding.btnPwFind.setBackgroundResource(R.color.very_light_pink)

                    } else {
                        mPhoneSpacingFlag = false
                    }

                    //엔터 입력 : 다른 내용 입력 되어있는지 확인
                    if (s.toString().contains("\n")) {
                        if (mNameFlag && mEmailFlag) {
                            mBinding.btnPwFind.setBackgroundResource(R.color.purple)
                        }
                    }

                    // '-' 정규식
                    if (s.toString().contains("-")) {
                        mBinding.tvPhoneCaptionPwFind.setTextColor(R.color.purple)
                        mBinding.tvPhoneCaptionPwFind.text =
                            getString(R.string.hint_phone_info)
                        mBinding.btnPwFind.setBackgroundResource(R.color.very_light_pink)
                        mPhoneFlag = false
                    }

                    //핸드폰 정규식
                    if (s.matches(phonePattern.toRegex()) && !mPhoneSpacingFlag) {
                        mBinding.tvPhoneCaptionPwFind.setTextColor(R.color.purple)
                        mBinding.tvPhoneCaptionPwFind.text =
                            getString(R.string.tv_phone_caption_result_info)
                        mPhoneFlag = true
                    }
                    //빈공란 정규식
                    if (s.toString().isEmpty()) {
                        mBinding.tvPhoneCaptionPwFind.setTextColor(R.color.purple)
                        mBinding.tvPhoneCaptionPwFind.text =
                            getString(R.string.tv_phone_caption_info)
                    }
                    //버튼 색상 확인
                    if (mNameFlag && mEmailFlag && mPhoneFlag) {
                        mBinding.btnPwFind.setBackgroundResource(R.color.purple)
                    }
                }
            }

        })

        //다음으로 버튼 클릭
        mBinding.btnPwFind.setOnClickListener {
            Log.d("sky", "이메일 엔 : $mEmailFlag, $mEmailFlag, $mPhoneFlag")
            var name = mBinding.etNamePwFind.text.trim().toString()
            var email = mBinding.etEmailPwFind.text.trim().toString()
            var phoneNumber = mBinding.etPhonePwFind.text.trim().toString()

            Log.d("sky", "버튼클릭 : $name,$email,$phoneNumber")
            if (mNameFlag && mEmailFlag && mPhoneFlag) {
                //로그인 api
                val postRequest =
                    PwFindRequest(name = name, email = email, phoneNumber = phoneNumber)
                PwFindService(this).tryPostPwFind(postRequest)

            } else {
                //스낵 : 정보를 재 확인하세요
                CustomSnackbar.make(
                    mBinding.root,
                    getString(R.string.tv_check_info_find_email),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onPostPwFindSuccess(response: PwFindResponse) {
        val builder = AlertDialog.Builder(this)
        val binding: DialogEmailFindBinding = DialogEmailFindBinding.inflate(layoutInflater)

        if (response.code == 1000) {
            //Dialog - Title
            binding.tvDialogTitleEmailFind.text =
                getString(R.string.tv_dialog_title_find_pw)
            //Dialog - Content
            binding.tvDialogContentEmailFind.text =
                getString(R.string.tv_dialog_content_find_pw)
            //Dialog - 확인 버튼
            builder.setPositiveButton(getString(R.string.tv_dialog_confirm)) { _, i ->

            }
            builder.setView(binding.root).show()
        } else {
            //Dialog - Title
            binding.tvDialogTitleEmailFind.text =
                getString(R.string.tv_dialog_wrong_title_find_pw)
            //Dialog - Content
            binding.tvDialogContentEmailFind.text = response.message
            //Dialog - 확인 버튼
            builder.setPositiveButton(getString(R.string.tv_dialog_confirm)) { _, i ->

            }
            builder.setView(binding.root).show()
        }
    }

    override fun onPostPwFindFailure(message: String) {
        Log.d("sky", "onPostPwFindFailure - $message")
    }
}