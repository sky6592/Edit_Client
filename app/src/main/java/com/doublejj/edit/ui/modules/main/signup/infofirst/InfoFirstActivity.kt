package com.doublejj.edit.ui.modules.main.signup.infofirst

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.infofirst.InfoFirstService
import com.doublejj.edit.data.api.services.infofirst.InfoFirstView
import com.doublejj.edit.data.models.infofirst.InfoFirstRequest
import com.doublejj.edit.data.models.infofirst.InfoFirstResponse
import com.doublejj.edit.databinding.ActivityInfoFirstBinding
import com.doublejj.edit.databinding.DialogEmailFindBinding
import com.doublejj.edit.ui.modules.main.signup.infosecond.InfoSecondActivity
import com.doublejj.edit.ui.modules.main.signup.privacyagree.PrivacyAgreeActivity
import com.doublejj.edit.ui.modules.main.walkthrough.WalkThroughActivity
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class InfoFirstActivity : AppCompatActivity(), InfoFirstView {

    private lateinit var mBinding: ActivityInfoFirstBinding

    private var mNameFlag: Boolean = false
    private var mNickNameFlag: Boolean = false
    private var mPhoneFlag: Boolean = false

    private var mNameSpacingFlag: Boolean = false
    private var mNickNameSpacingFlag: Boolean = false
    private var mPhoneSpacingFlag: Boolean = false

    private var mNickNameLengthFlag: Boolean = false

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_info_first)

        //정규식
        var namePattern = "^[a-zA-Z가-힣]{2,10}$"
        var nickNamePattern = "^[a-zA-Zㄱ-ㅎ가-힣0-9]{2,6}$"
        var phonePattern = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$"


        //이용약관(보라색)
        val spannable = SpannableStringBuilder(mBinding.tvPrivacyInfoFirst.text)
        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#5a32dc")),
            0,
            8,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        mBinding.tvPrivacyInfoFirst.text = spannable


        // 이름 입력
        mBinding.etNameInfoFirst.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //띄어쓰기 & 빈공란 정규식
                    if (s.toString().contains(" ") || s.toString().isEmpty()) {
                        mBinding.tvNameCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvNameCaptionInfoFirst.text =
                            getString(R.string.tv_caption_spacing_info)
                        mNameSpacingFlag = true
                        mNameFlag = false
                        mBinding.btnInfoFirst.setBackgroundResource(R.color.very_light_pink)
                    } else {
                        mNameSpacingFlag = false
                    }

                    //엔터 입력 : 다른 내용 입력 되어있는지 확인
                    if (s.toString().contains("\n")) {
                        if (mNickNameFlag && mPhoneFlag) {
                            mBinding.btnInfoFirst.setBackgroundResource(R.color.purple)
                        }
                    }
                    //글자 기준
                    if (s.toString().length < 2 || s.toString().length > 10) {
                        mBinding.tvNameCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvNameCaptionInfoFirst.text =
                            getString(R.string.tv_name_caption_rule_info)
                    }

                    //이름 정규식
                    if (s.matches(namePattern.toRegex()) && !mNameSpacingFlag) {
                        mBinding.tvNameCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvNameCaptionInfoFirst.text =
                            getString(R.string.tv_name_caption_result_info)
                        mNameFlag = true
                    }

                    //버튼 색상 확인
                    if (mNameFlag && mNickNameFlag && mPhoneFlag) {
                        mBinding.btnInfoFirst.setBackgroundResource(R.color.purple)
                    }


                }
            }
        })

        //닉네임 입력
        mBinding.etNickNameInfoFirst.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {

                    //띄어쓰기 & 빈공란 정규식
                    if (s.toString().contains(" ") || s.toString().isEmpty()) {
                        mBinding.tvNickNameCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvNickNameCaptionInfoFirst.text =
                            getString(R.string.tv_caption_spacing_info)
                        mNickNameSpacingFlag = true
                        mNickNameFlag = false
                        mNickNameLengthFlag = true
                        mBinding.btnInfoFirst.setBackgroundResource(R.color.very_light_pink)
                    } else {
                        mNickNameSpacingFlag = false
                        mNickNameLengthFlag = false

                    }



                    if (s.toString().length > 6) {
                        mBinding.tvNickNameCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvNickNameCaptionInfoFirst.text =
                            getString(R.string.tv_nick_name_caption_info)
                        mNickNameLengthFlag = true
                    } else {
                        mNickNameLengthFlag = false

                    }

                    //닉네임 정규식
                    if (s.matches(nickNamePattern.toRegex()) && !mNickNameSpacingFlag) {
                        mBinding.tvNickNameCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvNickNameCaptionInfoFirst.text =
                            getString(R.string.tv_click_caption_info)
                    }

                    //엔터 입력 : 다른 내용 입력 되어있는지 확인
                    if (s.toString().contains("\n")) {
                        if (mNameFlag && mPhoneFlag) {
                            mBinding.btnInfoFirst.setBackgroundResource(R.color.purple)
                        }
                    }

                    if (s.toString().contains("\n") && mNickNameFlag) {
                        mBinding.tvNickNameCaptionInfoFirst.text =
                            getString(R.string.tv_NickName_caption_result_info)
                    }

                    //입력 완료여부 정규식
                    if (mNameFlag && mNickNameFlag && mPhoneFlag) {
                        mBinding.btnInfoFirst.setBackgroundResource(R.color.purple)
                    }

                }
            }
        })

        //닉네임 중복버튼 - API
        mBinding.btnDoubleCheck.setOnClickListener {

            if (!mNickNameLengthFlag) {
                mBinding.etNickNameInfoFirst.toString().replace(" ", "")

                val nickName = mBinding.etNickNameInfoFirst.text.toString()
                val postRequest = InfoFirstRequest(nickName = nickName)
                //다이얼로그 넣어야함!
                InfoFirstService(this).tryPostNickName(postRequest)
            } else {
                mBinding.tvNickNameCaptionInfoFirst.setTextColor(R.color.purple)
                mBinding.tvNickNameCaptionInfoFirst.text =
                    getString(R.string.tv_nick_name_caption_info)
            }

        }

        //핸드폰 입력
        mBinding.etPhoneInfoFirst.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("ResourceAsColor", "ResourceType")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //띄어쓰기 작성
                    if (s.toString().contains(" ")) {
                        mBinding.tvPhoneCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvPhoneCaptionInfoFirst.text =
                            getString(R.string.tv_caption_spacing_info)
                        mPhoneSpacingFlag = true
                        mPhoneFlag = false
                        mBinding.btnInfoFirst.setBackgroundResource(R.color.very_light_pink)

                    } else {
                        mPhoneSpacingFlag = false
                    }

                    //엔터 입력 : 다른 내용 입력 되어있는지 확인
                    if (s.toString().contains("\n")) {
                        if (mNameFlag && mNickNameFlag) {
                            mBinding.btnInfoFirst.setBackgroundResource(R.color.purple)
                        }
                    }

                    // '-' 정규식
                    if (s.toString().contains("-")) {
                        mBinding.tvPhoneCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvPhoneCaptionInfoFirst.text =
                            getString(R.string.hint_phone_info)
                        mBinding.btnInfoFirst.setBackgroundResource(R.color.very_light_pink)
                        mPhoneFlag = false
                    }

                    //핸드폰 정규식
                    if (s.matches(phonePattern.toRegex()) && !mPhoneSpacingFlag) {
                        mBinding.tvPhoneCaptionInfoFirst.setText(R.color.purple)
                        mBinding.tvPhoneCaptionInfoFirst.text =
                            getString(R.string.tv_phone_caption_result_info)
                        mPhoneFlag = true
                    }
                    //빈공란 정규식
                    if (s.toString().isEmpty()) {
                        mBinding.tvPhoneCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvPhoneCaptionInfoFirst.text =
                            getString(R.string.tv_phone_caption_info)
                    }
                    //입력 완료여부 정규식
                    if (mNameFlag && mNickNameFlag && mPhoneFlag) {
                        mBinding.btnInfoFirst.setBackgroundResource(R.color.purple)
                    }

                }
            }

        })

        //버튼 클릭
        mBinding.btnInfoFirst.setOnClickListener {
            Log.d(
                "sky",
                mNameFlag.toString() + " " + mNickNameFlag.toString() + " " + mPhoneFlag.toString()
            )

            //입력내용 저장
            var name = mBinding.etNameInfoFirst.text.trim().toString()
            var nickname = mBinding.etNickNameInfoFirst.text.trim().toString()
            var phone = mBinding.etPhoneInfoFirst.text.trim().toString()
            var arrayList = ArrayList<String>()
            Log.d("sky", arrayList.toString())

            //최종 넘기기
            if (mNameFlag && mNickNameFlag && mPhoneFlag) {
                //초기화
                mNameFlag = false
                mNickNameFlag = false
                mPhoneFlag = false

                arrayList.add(name)
                arrayList.add(nickname)
                arrayList.add(phone)

                //동의하기 화면으로 넘어감
                val intent = Intent(this, PrivacyAgreeActivity::class.java)
                intent.putExtra("arrayList", arrayList)
                startActivity(intent)
                finish()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onPostInfoFirstSuccess(response: InfoFirstResponse) {
        //닉네임 중복여부 성공
        Log.d("sky", "onPostInfoFirstSuccess - api성공")

        //성공
        if (response.code == 1000 && response.result.duplicationCheck == "NO") {
            //중복없음
            mNickNameFlag = true
            mBinding.tvNickNameCaptionInfoFirst.setTextColor(R.color.purple)
            mBinding.tvNickNameCaptionInfoFirst.text =
                getString(R.string.tv_NickName_caption_result_info)

            //입력 완료여부 정규식
            if (mNameFlag && mNickNameFlag && mPhoneFlag) {
                mBinding.btnInfoFirst.setBackgroundResource(R.color.purple)
            }
        } else {
            mNickNameFlag = false
            mBinding.tvNickNameCaptionInfoFirst.setTextColor(R.color.purple)
            mBinding.tvNickNameCaptionInfoFirst.text =
                getString(R.string.tv_NickName_caption_duplication_result_info)
        }

        Log.d(
            "sky",
            "중복버튼 : $mNameFlag,$mNickNameFlag,$mPhoneFlag"
        )
    }

    override fun onPostInfoFirstFailure(message: String) {
        //닉네임 중복여부 실패
        Log.d("sky", "onPostInfoFirstFailure - api실패")
        CustomSnackbar.make(mBinding.root, message, Snackbar.ANIMATION_MODE_SLIDE)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val builder = AlertDialog.Builder(this)
        val binding: DialogEmailFindBinding = DialogEmailFindBinding.inflate(layoutInflater)

        //Dialog - Title
        binding.tvDialogTitleEmailFind.text =
            getString(R.string.tv_dialog_title_back_press)
        //Dialog - Content
        binding.tvDialogContentEmailFind.text =
            getString(R.string.tv_dialog_content_back_press)
        binding.tvDialogApiEmailFind.visibility = View.GONE
        //Dialog - 확인 버튼
        builder.setPositiveButton(getString(R.string.tv_dialog_confirm)) { _, _ ->
            val intentWalkThrough = Intent(this, WalkThroughActivity::class.java)
            startActivity(intentWalkThrough)
            finishAffinity()
        }
        builder.setNegativeButton(getString(R.string.tv_dialog_dismiss)) { _, _ ->

        }
        builder.setView(binding.root).show()
    }
}