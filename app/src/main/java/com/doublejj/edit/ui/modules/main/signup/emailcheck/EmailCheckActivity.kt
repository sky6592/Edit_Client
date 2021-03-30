package com.doublejj.edit.ui.modules.main.signup.emailcheck

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.emailcheck.EmailCheckService
import com.doublejj.edit.data.api.services.emailcheck.EmailCheckVIew
import com.doublejj.edit.data.models.emailcheck.EmailCheckRequest
import com.doublejj.edit.data.models.emailcheck.EmailCheckResponse
import com.doublejj.edit.databinding.ActivityEmailCheckBinding
import com.doublejj.edit.databinding.DialogEmailFindBinding
import com.doublejj.edit.ui.modules.main.signup.entercode.EnterCodeActivity
import com.doublejj.edit.ui.modules.main.walkthrough.WalkThroughActivity

class EmailCheckActivity : AppCompatActivity(), EmailCheckVIew {
    private val TAG = "sky"

    private lateinit var mBinding: ActivityEmailCheckBinding

    private var mEmailFlag: Boolean = false

    private lateinit var mArrayList: ArrayList<String>


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_email_check)

        val emailPattern = android.util.Patterns.EMAIL_ADDRESS

        mArrayList = intent.getSerializableExtra("arrayList") as ArrayList<String>
        Log.d(TAG, mArrayList.toString())


        //< 클릭
        mBinding.ivBackEmailCheck.setOnClickListener {
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

        //입력한 이메일 세팅
        mBinding.etEmailCheck.setText(mArrayList!![3])
        if (mBinding.etEmailCheck.text.isNotEmpty()) {
            Log.d(TAG, "main - if")
            mEmailFlag = true
            mBinding.btnEmailCheck.setBackgroundResource(R.color.purple)
        } else {
            Log.d(TAG, "main - else")
            mEmailFlag = false
            mBinding.btnEmailCheck.setBackgroundResource(R.color.very_light_pink)
        }


        //이메일 입력
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
                        mBinding.tvCaptionEmailCheck.setTextColor(R.color.purple)
                        mBinding.tvCaptionEmailCheck.text =
                            getString(R.string.tv_caption_pattern_result_email_check)
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
        //변경한 이메일 정보를 다시 저장
        val email = mBinding.etEmailCheck.text.trim().toString()
        mArrayList[3] = email

        val intent = Intent(this, EnterCodeActivity::class.java)
        intent.putExtra("arrayList", mArrayList)
        startActivity(intent)
        finish()

    }

    override fun onPostEmailCheckFailure(message: String) {
        Log.d(TAG, message)
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