package com.doublejj.edit.ui.modules.main.signup.infosecond

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.infosecond.InfoSecondService
import com.doublejj.edit.data.api.services.infosecond.InfoSecondView
import com.doublejj.edit.data.models.infosecond.InfoSecondRequest
import com.doublejj.edit.data.models.infosecond.InfoSecondResponse
import com.doublejj.edit.databinding.ActivityInfoSecondBinding
import com.doublejj.edit.ui.modules.main.signup.slectjopgroup.JobGroupActivity

class InfoSecondActivity : AppCompatActivity(), InfoSecondView {
    private lateinit var mBinding: ActivityInfoSecondBinding

    private var mEmailFlag: Boolean = false
    private var mPwFlag: Boolean = false
    private var mRePwFlag: Boolean = false

    private var mEmailSpacingFlag: Boolean = false
    private var mPwSpacingFlag: Boolean = false
    private var mRePwSpacingFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_info_second)

        val emailPattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+$"
        //문자(한글,영어), 숫자, 특수문자 중 2가지 포함(8-15자)
        val pwPattern = "^(?=.*[a-zA-Z0-9가-힣])(?=.*[a-zA-Z!@#\$%^&*])(?=.*[0-9!@#\$%^&*]).{8,15}$"

        //이메일 입력
        mBinding.etEmailInfoSecond.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {


                } catch (e: Exception) {
                    Log.d("exception", e.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //띄어쓰기 정규식
                    if (s.toString().contains(" ")) {
                        mBinding.tvEmailCaptionInfoFirst.text =
                            getString(R.string.tv_caption_spacing_info)
                        mEmailSpacingFlag = true
                    } else {
                        mEmailSpacingFlag = false
                    }

                    //이메일 정규식 - 중복확인을 누르세요!
                    if (s.matches(emailPattern.toRegex()) && !mEmailSpacingFlag) {
                        mBinding.tvEmailCaptionInfoFirst.text =
                            getString(R.string.tv_click_caption_info)
                    }
                    //입력 완료여부 정규식
                    if (mEmailFlag && mPwFlag && mRePwFlag) {
                        mBinding.btnInfoSecond.setBackgroundResource(R.color.purple)
                    }
                }
            }
        })

        //비밀번호 입력
        mBinding.etPwInfoSecond.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //띄어쓰기 정규식
                try {
                    if (s.toString().contains(" ")) {
                        mBinding.tvPwCaptionInfoSecond.text =
                            getString(R.string.tv_caption_spacing_info)
                        mPwSpacingFlag = true
                    }
                    if (!s.toString().contains(" ")) {
                        mPwSpacingFlag = false
                    }

                } catch (e: Exception) {
                    Log.d("exception", e.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //비밀번호 정규식
                    if (s.toString().matches(pwPattern.toRegex()) && !mEmailSpacingFlag) {
                        mBinding.tvPwCaptionInfoSecond.text = ""
                        mPwFlag = true
                    } else {
                        mBinding.tvPwCaptionInfoSecond.text =
                            getString(R.string.tv_pw_wrong_caption_info)
                    }
                    //입력 완료여부 정규식
                    if (mEmailFlag && mPwFlag && mRePwFlag) {
                        mBinding.btnInfoSecond.setBackgroundResource(R.color.purple)
                    }
                }
            }
        })

        //비밀번호 재입력
        mBinding.etRePwInfoFirst.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //띄어쓰기 정규식
                try {
                    if (s.toString().contains(" ")) {
                        mBinding.tvRePwCaptionInfoFirst.text =
                            getString(R.string.tv_caption_spacing_info)
                        mRePwSpacingFlag = true
                    }
                    if (!s.toString().contains(" ")) {
                        mRePwSpacingFlag = false
                    }

                } catch (e: Exception) {
                    Log.d("exception", e.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //비밀번호 일치여부
                    if (mBinding.etPwInfoSecond.text == s) {
                        mBinding.tvRePwCaptionInfoFirst.text =
                            getString(R.string.tv_re_pw_caption_info)
                        mRePwFlag = true

                    } else {
                        mBinding.tvRePwCaptionInfoFirst.text = getString(R.string.hint_re_pw_info)
                    }
                    //입력 완료여부 정규식
                    if (mEmailFlag && mPwFlag && mRePwFlag) {
                        mBinding.btnInfoSecond.setBackgroundResource(R.color.purple)
                    }
                }
            }

        })


        //이메일 중복확인
        mBinding.btnDoubleCheck.setOnClickListener {
            val email = mBinding.etEmailInfoSecond.text.toString()
            val postRequest = InfoSecondRequest(email = email)
            //다이얼로그 넣어야함
            InfoSecondService(this).tryPostEmail(postRequest)
        }

        //다음으로 버튼 클릭
        mBinding.btnInfoSecond.setOnClickListener {
            if (mEmailFlag && mPwFlag && mRePwFlag) {
                mEmailFlag = false
                mPwFlag = false
                mRePwFlag = false
                startActivity(Intent(this, JobGroupActivity::class.java))
            } else {
                //다이얼로그 : 입력을 다시 확인해주세요!
            }
        }
    }

    //네트워크 통신
    override fun onPostInfoSecondSuccess(response: InfoSecondResponse) {
        //다이얼로그 해제 넣기
        Log.d("sky", "onPostInfoSecondSuccess - api성공")
        mEmailFlag = true

        runOnUiThread {
            Toast.makeText(this, "onPostInfoSecondSuccess - API성공", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPostInfoSecondFailure(message: String) {
        //다이얼로그 해제 넣기
        Log.d("sky", "onPostInfoSecondFailure - api실")

        Toast.makeText(this, "onPostInfoSecondFailure - API실패", Toast.LENGTH_SHORT).show()
    }

}