package com.doublejj.edit.ui.modules.main.signup.infosecond

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.infosecond.InfoSecondService
import com.doublejj.edit.data.api.services.infosecond.InfoSecondView
import com.doublejj.edit.data.models.infosecond.InfoSecondRequest
import com.doublejj.edit.data.models.infosecond.InfoSecondResponse
import com.doublejj.edit.databinding.ActivityInfoSecondBinding
import com.doublejj.edit.ui.modules.main.signup.emailcheck.EmailCheckActivity
import com.doublejj.edit.ui.modules.main.signup.infofirst.InfoFirstActivity
import com.doublejj.edit.ui.modules.main.signup.slecttype.SelectTypeActivity
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment


class InfoSecondActivity : AppCompatActivity(), InfoSecondView {
    private lateinit var mBinding: ActivityInfoSecondBinding

    private var mEmailFlag: Boolean = false
    private var mPwFlag: Boolean = false
    private var mRePwFlag: Boolean = false

    private var mEmailSpacingFlag: Boolean = false
    private var mPwSpacingFlag: Boolean = false
    private var mRePwSpacingFlag: Boolean = false
    private var mEmailBtnFlag: Boolean = false

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_info_second)

        //Intent - ArrayList 저장
        var arrayList = intent.getSerializableExtra("arrayList") as ArrayList<String>
//        var    check = "test"
//        arrayList.add(check)
//        Log.d("sky", arrayList.toString())

        val emailPatternTest = android.util.Patterns.EMAIL_ADDRESS
        //문자(한글,영어), 숫자, 특수문자 중 2가지 포함(8-15자)
        val pwPattern =
            "^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z~!`@#\$%^&*()_+=])(?=.*[0-9!@#\$%^&*]).{8,15}$"

        //이메일 입력
        mBinding.etEmailInfoSecond.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //띄어쓰기 정규식
                    if (s.toString().contains(" ") || s.toString().isEmpty()) {
                        mBinding.btnInfoSecond.setBackgroundResource(R.color.very_light_pink)
                        mBinding.tvEmailCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvEmailCaptionInfoFirst.text =
                            getString(R.string.tv_caption_spacing_info)
                        mEmailSpacingFlag = true
                        mEmailFlag = false
                    } else {
                        mEmailSpacingFlag = false
                    }
                    //이메일 정규식 - 중복확인을 누르세요!
                    if (emailPatternTest.matcher(s.toString()).matches()) {
                        mBinding.tvEmailCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvEmailCaptionInfoFirst.text =
                            getString(R.string.tv_click_caption_info)
                        mEmailBtnFlag = true
                    }
                    //입력 완료여부 정규식
                    if (mEmailFlag && mPwFlag && mRePwFlag) {
                        mBinding.btnInfoSecond.setBackgroundResource(R.color.purple)
                    }
                    Log.d("sky", "이메일 엔 : $mEmailFlag, $mPwFlag, $mRePwFlag")

                }
            }
        })

        //이메일 중복확인
        mBinding.btnDoubleCheck.setOnClickListener {
            if (mEmailBtnFlag) {
                mBinding.etEmailInfoSecond.toString().replace(" ", "")

                //이메일 중복 API
                val email = mBinding.etEmailInfoSecond.text.toString()
                val postRequest = InfoSecondRequest(email = email)
                //다이얼로그 넣어야함
                InfoSecondService(this).tryPostEmail(postRequest)
                mEmailBtnFlag = false
            } else {
                mBinding.tvEmailCaptionInfoFirst.setTextColor(R.color.purple)
                mBinding.tvEmailCaptionInfoFirst.text =
                    getString(R.string.tv_email_result_wrong_info)
            }
        }

        //비밀번호 입력
        mBinding.etPwInfoSecond.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //엔터 입력 : 다른 내용 입력 되어있는지 확인
                    if (s.toString().contains("\n")) {
                        //s.toString().trim()
                        if (mEmailFlag && mRePwFlag) {
                            mBinding.btnInfoSecond.setBackgroundResource(R.color.purple)
                        }
                    }
                    //띄어쓰기 정규식
                    if (s.toString().contains(" ") || s.toString().isEmpty()) {
                        mBinding.tvPwCaptionInfoSecond.text =
                            getString(R.string.tv_caption_spacing_info)
                        mPwSpacingFlag = true
                        mPwFlag = false
                        mBinding.btnInfoSecond.setBackgroundResource(R.color.very_light_pink)
                    } else {
                        mPwSpacingFlag = false
                        mBinding.tvPwCaptionInfoSecond.text = getString(R.string.tv_pw_caption_info)
                    }


                    //비밀번호 일치여부
                    if (mBinding.etRePwInfoFirst.text.toString() == s.toString()) {
                        mBinding.tvPwCaptionInfoSecond.text = ""
                        mRePwFlag = true
                    } else {
                        mBinding.btnInfoSecond.setBackgroundResource(R.color.very_light_pink)

                        mRePwFlag = false
                    }
                    //비밀번호 정규식
                    if (s.toString().matches(pwPattern.toRegex())) {
                        mBinding.tvPwCaptionInfoSecond.text = ""
                        mPwFlag = true
                    } else {
                        mBinding.tvPwCaptionInfoSecond.setTextColor(R.color.purple)
                        mBinding.tvPwCaptionInfoSecond.text =
                            getString(R.string.tv_pw_wrong_caption_info)
                        mPwFlag = false
                    }
                    //입력 완료여부 정규식
                    if (mEmailFlag && mPwFlag && mRePwFlag) {
                        mBinding.btnInfoSecond.setBackgroundResource(R.color.purple)
                    }
                    Log.d("sky", "비번 엔터 : $mEmailFlag, $mPwFlag, $mRePwFlag")

                }
            }
        })

        //비밀번호 재입력
        mBinding.etRePwInfoFirst.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //엔터 입력 : 다른 내용 입력 되어있는지 확인
                    if (s.toString().contains("\n")) {
                        //s.toString().replace("\n", "")
                        //s.toString().trim()

                        if (mPwFlag && mRePwFlag) {
                            mBinding.btnInfoSecond.setBackgroundResource(R.color.purple)
                        }
                    }
                    //띄어쓰기 정규식
                    if (s.toString().contains(" ") || s.toString().isEmpty()) {
                        mBinding.tvRePwCaptionInfoFirst.setTextColor(R.color.purple)
                        mBinding.tvRePwCaptionInfoFirst.text =
                            getString(R.string.tv_caption_spacing_info)
                        mRePwSpacingFlag = true
                        mRePwFlag = false
                        mBinding.btnInfoSecond.setBackgroundResource(R.color.very_light_pink)
                    } else {
                        mRePwSpacingFlag = false
                        mBinding.tvRePwCaptionInfoFirst.setTextColor(R.color.very_light_pink)

                    }

                    Log.d("sky", mBinding.etPwInfoSecond.text.toString() + ", " + s.toString())

                    //비밀번호 일치여부
                    if (mBinding.etPwInfoSecond.text.toString() == s.toString()) {
                        mBinding.tvRePwCaptionInfoFirst.text = ""
                        mRePwFlag = true

                    } else {
                        mBinding.btnInfoSecond.setBackgroundResource(R.color.very_light_pink)
                        mBinding.tvRePwCaptionInfoFirst.text =
                            getString(R.string.tv_re_pw_caption_wrong_info)
                        mRePwFlag = false
                    }
                    //비밀번호 정규식
                    if (s.toString().matches(pwPattern.toRegex())) {
                        mBinding.tvRePwCaptionInfoFirst.text = ""
                        mRePwFlag = true
                    } else {
                        mBinding.tvRePwCaptionInfoFirst.text =
                            getString(R.string.tv_pw_wrong_caption_info)
                        mRePwFlag = false
                    }
                    //입력 완료여부 정규식
                    if (mEmailFlag && mPwFlag && mRePwFlag) {
                        mBinding.btnInfoSecond.setBackgroundResource(R.color.purple)
                    }
                    Log.d("sky", "비번 재 엔터 : $mEmailFlag, $mPwFlag, $mRePwFlag")
                }
            }

        })


        //다음으로 버튼 클릭
        mBinding.btnInfoSecond.setOnClickListener {
            Log.d("sky", "btnInfoSecond - In")
            Log.d(
                "sky",
                "중복버튼 :$mEmailFlag, $mPwFlag, $mRePwFlag"
            )


            var email = mBinding.etEmailInfoSecond.text.toString().replace(" ", "")
            var pw = mBinding.etPwInfoSecond.text.toString().replace(" ", "")
            var rePw = mBinding.etRePwInfoFirst.text.toString().replace(" ", "")
            Log.d("sky", "값 : $email, $pw, $rePw")

            arrayList.add(email)
            arrayList.add(pw)
            arrayList.add(rePw)


            if (mEmailFlag && mPwFlag && mRePwFlag) {
                mEmailFlag = false
                mPwFlag = false
                mRePwFlag = false
                mEmailSpacingFlag = false
                mPwSpacingFlag = false
                mRePwSpacingFlag = false

                val intent = Intent(this, EmailCheckActivity::class.java)
                intent.putExtra("arrayList",arrayList)
                startActivity(intent)
                finish()
                Log.d("sky - array",arrayList.toString())
            } else {
                //다이얼로그 : 입력을 다시 확인해주세요!
            }

        }
    }

    //네트워크 통신
    @SuppressLint("ResourceType")
    override fun onPostInfoSecondSuccess(response: InfoSecondResponse) {
        //다이얼로그 해제 넣기
        Log.d("sky", "onPostInfoSecondSuccess - api성공")
        runOnUiThread {
            mEmailFlag = true
            //이메일 입력 감사합니다
            mBinding.tvEmailCaptionInfoFirst.text = ""

            //입력 완료여부 정규식
            if (mEmailFlag && mPwFlag && mRePwFlag) {
                mBinding.btnInfoSecond.setBackgroundResource(R.color.purple)
            }
        }
    }

    @SuppressLint("ResourceType")
    override fun onPostInfoSecondFailure(message: String) {
        //다이얼로그 해제 넣기
        Log.d("sky", "onPostInfoSecondFailure - api실패")

        runOnUiThread {
            mBinding.tvEmailCaptionInfoFirst.text = getString(R.string.tv_email_fail_info)
            mBinding.tvEmailCaptionInfoFirst.setTextColor(R.color.purple)
        }
    }
}