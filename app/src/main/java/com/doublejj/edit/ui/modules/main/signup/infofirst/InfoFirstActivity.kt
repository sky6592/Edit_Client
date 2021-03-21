package com.doublejj.edit.ui.modules.main.signup.infofirst

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.infofirst.InfoFirstService
import com.doublejj.edit.data.api.services.infofirst.InfoFirstView
import com.doublejj.edit.data.models.infofirst.InfoFirstRequest
import com.doublejj.edit.data.models.infofirst.InfoFirstResponse
import com.doublejj.edit.databinding.ActivityInfoFirstBinding
import com.doublejj.edit.ui.modules.main.signup.infosecond.InfoSecondActivity

class InfoFirstActivity : AppCompatActivity(), InfoFirstView {

    private lateinit var mBinding: ActivityInfoFirstBinding

    private var mNameFlag: Boolean = false
    private var mNickNameFlag: Boolean = false
    private var mPhoneFlag: Boolean = false

    private var mNameSpacingFlag: Boolean = false
    private var mNickNameSpacingFlag: Boolean = false
    private var mPhoneSpacingFlag: Boolean = false

    private var mNickNameLenghFlag: Boolean = false


    //@SuppressLint("ClickableViewAccessibility")
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_info_first)

        var namePattern = "^[a-zA-Z가-힣]{2,10}$"
        var nickNamePattern = "^[a-zA-Zㄱ-ㅎ가-힣0-9]{2,6}$"
        var phonePattern = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$"

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
//                        mBinding.tvNameCaptionInfoFirst.setTextColor(R.color.very_light_pink)
                        mBinding.tvNameCaptionInfoFirst.text = ""
                        mNameFlag = true
                    }

                    //버튼 색상 확인
                    if (mNameFlag && mNickNameFlag && mPhoneFlag) {
                        mBinding.btnInfoFirst.setBackgroundResource(R.color.purple)
                    }


                }
//                else {
//                    //빈공란 정규식
//                    mBinding.tvNameCaptionInfoFirst.setTextColor(R.color.purple)
//                    mBinding.tvNameCaptionInfoFirst.text =
//                        getString(R.string.tv_name_caption_info)
//                }
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
                        mBinding.btnInfoFirst.setBackgroundResource(R.color.very_light_pink)
                    } else {
                        mNickNameSpacingFlag = false
                    }

                    //엔터 입력 : 다른 내용 입력 되어있는지 확인
                    if (s.toString().contains("\n")) {
                        if (mNameFlag && mPhoneFlag) {
                            mBinding.btnInfoFirst.setBackgroundResource(R.color.purple)
                        }
                    }

                    if (s.toString().length > 6) {
                        mBinding.tvNickNameCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvNickNameCaptionInfoFirst.text =
                            getString(R.string.tv_nick_name_caption_info)
                        mNickNameLenghFlag = true
                    } else {
                        mNickNameLenghFlag = false

                    }

                    //닉네임 정규식
                    if (s.matches(nickNamePattern.toRegex()) && !mNickNameSpacingFlag) {
                        mBinding.tvNickNameCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvNickNameCaptionInfoFirst.text =
                            getString(R.string.tv_click_caption_info)
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

            if (!mNickNameLenghFlag) {
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

            @SuppressLint("ResourceAsColor")
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
                        mBinding.tvPhoneCaptionInfoFirst.text = ""
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

        //회원가입 버튼 클릭
        mBinding.btnInfoFirst.setOnClickListener {
            Log.d(
                "sky",
                mNameFlag.toString() + " " + mNickNameFlag.toString() + " " + mPhoneFlag.toString()
            )

            //입력내용 저장
            var name = mBinding.etNameInfoFirst.text.toString().replace(" ", "")
            var nickName = mBinding.etNickNameInfoFirst.text.toString().replace(" ", "")
            var phone = mBinding.etPhoneInfoFirst.text.toString().replace(" ", "")

            Log.d(
                "sky",
                "입력된 내용 : " + name.toString() + "," + nickName.toString() + "," + phone.toString()
            )

            //최종 넘기기
            if (mNameFlag && mNickNameFlag && mPhoneFlag) {
                //초기화
                mNameFlag = false
                mNickNameFlag = false
                mPhoneFlag = false
                //intent 값 저장
                val keyName = "name"
                val keyNickName = "nickName"
                val keyPhone = "phone"

                val intent = Intent(this, InfoSecondActivity::class.java)
                intent.putExtra(keyName, name)
                intent.putExtra(keyNickName, nickName)
                intent.putExtra(keyPhone, phone)
                startActivity(intent)
                finish()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onPostInfoFirstSuccess(response: InfoFirstResponse) {
        //닉네임 중복여부 성공
        Log.d("sky", "onPostInfoFirstSuccess - api성공")

        //다이얼로그!!

        runOnUiThread {
            mNickNameFlag = true
            mBinding.tvNickNameCaptionInfoFirst.text = ""

            //입력 완료여부 정규식
            if (mNameFlag && mNickNameFlag && mPhoneFlag) {
                mBinding.btnInfoFirst.setBackgroundResource(R.color.purple)
            }
        }

        Log.d(
            "sky",
            "중복버튼 : " + mNameFlag.toString() + "," + mNickNameFlag.toString() + "," + mPhoneFlag.toString()
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onPostInfoFirstFailure(message: String) {
        //닉네임 중복여부 실패
        Log.d("sky", "onPostInfoFirstFailure - api실패")
        runOnUiThread {
            mNickNameFlag = false
            mBinding.tvNickNameCaptionInfoFirst.setTextColor(R.color.purple)
            mBinding.tvNickNameCaptionInfoFirst.text =
                getString(R.string.tv_NickName_caption_fail_info)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //다이얼로그 만들기
    }
}