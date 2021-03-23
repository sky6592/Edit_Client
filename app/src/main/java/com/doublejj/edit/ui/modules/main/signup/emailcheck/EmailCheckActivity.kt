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

    private lateinit var mBinding: ActivityEmailCheckBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_email_check)

        var arrayList = intent.getSerializableExtra("arrayList") as ArrayList<String>
        Log.d("sky", arrayList.toString())

        //입력한 이메일 세팅
        mBinding.etEmailCheck.setText(arrayList[3])

        mBinding.etEmailCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }


            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                //이메일 작성여부 확인
                if (s != null) {
                    mBinding.btnEmailCheck.setBackgroundColor(R.color.purple)
                } else {
                    mBinding.btnEmailCheck.setBackgroundColor(R.color.very_light_pink)
                }

//                if ()
            }

        })

        // 인증 메일 발송 - 버튼
        mBinding.btnEmailCheck.setOnClickListener {
            if (mBinding.etEmailCheck.text.toString() != null) {
                val email = mBinding.etEmailCheck.text.toString()
                val postRequest = EmailCheckRequest(email = email)
                EmailCheckService(this).tryPostEmailCheck(postRequest)
            } else {
                mBinding.btnEmailCheck.setBackgroundColor(R.color.very_light_pink)
            }

        }

    }

    override fun onPostEmailCheckSuccess(response: EmailCheckResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostEmailCheckFailure(message: String) {
        TODO("Not yet implemented")
    }
}