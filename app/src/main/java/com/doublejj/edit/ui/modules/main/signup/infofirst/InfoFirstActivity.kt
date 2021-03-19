package com.doublejj.edit.ui.modules.main.signup.infofirst

import android.annotation.SuppressLint
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

class InfoFirstActivity : AppCompatActivity(), InfoFirstView {
    private lateinit var mBinding: ActivityInfoFirstBinding

    private var mNameFlag: Boolean = false

    override fun onPostInfoFirstSuccess(response: InfoFirstResponse) {
        //닉네임 중복여부 성공
        Toast.makeText(this, "onGetInfoFirstSuccess - API성공", Toast.LENGTH_SHORT).show()
    }

    override fun onPostInfoFirstFailure(message: String) {
        //닉네임 중복여부 실패
        Toast.makeText(this, "onGetInfoFirst패Failure - API실", Toast.LENGTH_SHORT).show()

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_info_first)

        var namePattern = "^[a-zA-Z가-힣]*$"
        var nickNamePattern = "^[a-zA-Zㄱ-ㅎ가-힣0-9]*$"

        // 이름 입력
        mBinding.etNameInfoFirst.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //띄어쓰기 정규식
                try {
                    if (s.toString().substring(s.toString().length - 1) == (" ")) {
                        mBinding.tvNameCaptionInfoFirst.text =
                            getString(R.string.tv_caption_spacing_info)
                    }
                } catch (e: Exception) {
                    Log.d("exception", e.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //이름 정규식
                    if (s.matches(namePattern.toRegex())) {
                        mBinding.tvNameCaptionInfoFirst.text =
                            getString(R.string.tv_name_caption_result_info)
                        mNameFlag = true
                    }
                    //빈공란 정규식
                    if (s.toString().isEmpty()) {
                        mBinding.tvNameCaptionInfoFirst.text =
                            getString(R.string.tv_name_caption_info)
                    }
                }
            }
        })


        //닉네임 입력
        mBinding.etNickNameInfoFirst.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //띄어쓰기 정규식
                try {
                    if (s.toString().substring(s.toString().length - 1) == (" ")) {
                        mBinding.tvNameCaptionInfoFirst.text =
                            getString(R.string.tv_caption_spacing_info)
                    }
                } catch (e: Exception) {
                    Log.d("exception", e.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //닉네 정규식
                    if (s.matches(namePattern.toRegex())) {
                        mBinding.tvNameCaptionInfoFirst.text =
                            getString(R.string.tv_name_caption_result_info)
                        mNameFlag = true
                    }
                    //빈공란 정규식
                    if (s.toString().isEmpty()) {
                        mBinding.tvNameCaptionInfoFirst.text =
                            getString(R.string.tv_name_caption_info)
                    }
                }
            }

        })

        //닉네임 중복버튼
        mBinding.btnDoubleCheck.setOnClickListener {
            val nickName = mBinding.etNickNameInfoFirst.text.toString()
            val postRequest = InfoFirstRequest(nickName = nickName)
            //다이얼로그 넣어야함!
            InfoFirstService(this).tryPostNickName(postRequest)

        }


        //회원가입 버튼 클릭
        mBinding.btnInfoFirst.setOnClickListener {
            var name = mBinding.etNameInfoFirst.text.toString()

            // 1. 이름&닉네임 중복 클릭여부&휴대폰번호
            if (mNameFlag) {
                it.setBackgroundResource(R.color.purple)
                Toast.makeText(this, "성공", Toast.LENGTH_LONG).show()


            } else {
                it.setBackgroundResource(R.color.very_light_pink)
                Toast.makeText(this, "정규식에 맞지 않음", Toast.LENGTH_LONG).show()
            }
        }

        //임시코드 연결
//        mBinding.btnInfoFirst.setOnClickListener {
//            mBinding.btnInfoFirst.setBackgroundResource(R.color.purple)
//
//            startActivity(Intent(this, InfoSecondActivity::class.java))
//        }


    }


}