package com.doublejj.edit.ui.modules.main.signup.emailcheck

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.emailcheck.EmailCheckService
import com.doublejj.edit.data.api.services.emailcheck.EmailCheckVIew
import com.doublejj.edit.data.models.emailcheck.EmailCheckRequest
import com.doublejj.edit.data.models.emailcheck.EmailCheckResponse
import com.doublejj.edit.databinding.ActivityEmailCheckBinding

class EmailCheckActivity : AppCompatActivity(), EmailCheckVIew {
    private val TAG = "sky"

    private lateinit var mBinding: ActivityEmailCheckBinding

    private var mEmailFlag: Boolean = false

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_email_check)

        val emailPattern = android.util.Patterns.EMAIL_ADDRESS

//        var arrayList = intent.getSerializableExtra("arrayList") as ArrayList<String>
//        Log.d(TAG, arrayList.toString())
//
//
//        //입력한 이메일 세팅
//        mBinding.etEmailCheck.setText(arrayList[3])
        mBinding.etEmailCheck.setText("789_skymert@naver.com")
        if (mBinding.etEmailCheck.text.isNotEmpty()) {
            Log.d(TAG, "main - if")
            mEmailFlag = true
        } else {
            Log.d(TAG, "main - else")
            mEmailFlag = false
        }


        mBinding.etEmailCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }


            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                //이메일 작성여부 확인
                if (s != null) {
                    //이메일 정규식 - 중복확인을 누르세요!
                    if (emailPattern.matcher(s.toString()).matches()) {
                        mEmailFlag = true
                        mBinding.btnEmailCheck.setBackgroundResource(R.color.purple)
                        mBinding.tvCaptionEmailCheck.text =
                            getString(R.string.tv_caption_email_check)
                    } else {
                        mEmailFlag = false
                        mBinding.tvCaptionEmailCheck.text =
                            getString(R.string.tv_email_result_wrong_info)
                        mBinding.btnEmailCheck.setBackgroundResource(R.color.very_light_pink)
                        mBinding.tvCaptionEmailCheck.text =
                            getString(R.string.tv_email_result_wrong_info)
                    }
                    Log.d(TAG, s.toString())

                }
            }

        })

        // 인증 메일 발송 - 버튼
        mBinding.btnEmailCheck.setOnClickListener {
            if (mEmailFlag) {
                it.setBackgroundResource(R.color.purple)
                val email = mBinding.etEmailCheck.text.toString()
                val postRequest = EmailCheckRequest(email = email)
                EmailCheckService(this).tryPostEmailCheck(postRequest)

            } else {
                it.setBackgroundResource(R.color.very_light_pink)

            }

        }
    }

    override fun onPostEmailCheckSuccess(response: EmailCheckResponse) {
        Log.d(TAG, response.message.toString())

    }

    override fun onPostEmailCheckFailure(message: String) {
        Log.d(TAG, message)
    }
}