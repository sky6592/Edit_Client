package com.doublejj.edit.ui.modules.main.signup.read

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.read.ReadService
import com.doublejj.edit.data.api.services.read.ReadView
import com.doublejj.edit.data.models.infofirst.InfoFirstRequest
import com.doublejj.edit.data.models.read.ReadRequest
import com.doublejj.edit.data.models.read.ReadResponse
import com.doublejj.edit.databinding.ActivityReadBinding
import com.doublejj.edit.databinding.DialogEmailFindBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.login.LogInActivity
import com.doublejj.edit.ui.modules.main.walkthrough.WalkThroughActivity
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class ReadActivity : AppCompatActivity(), ReadView {
    private lateinit var mBinding: ActivityReadBinding
    private lateinit var mArrayList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_read)

        mArrayList = intent.getSerializableExtra("arrayList") as ArrayList<String>
        Log.d("sky", mArrayList.toString())

        //< 클릭
        mBinding.ivBackRead.setOnClickListener {
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

        //닉네임 세팅
        val nickname = mArrayList[1]
        //멘토,멘티 세팅
        var selectType = ""
        if (mArrayList[6] == "MENTEE") {
            selectType = "멘티"
        } else {
            selectType = "멘토"
        }

        //정보 선택
        mBinding.tvInfoOneRead.text =
            nickname + " " + selectType + getString(R.string.tv_mentor_info_read)
        val spannable = SpannableStringBuilder(mBinding.tvInfoOneRead.text)
        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#5a32dc")),
            0,
            nickname.length + 1 + selectType.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        mBinding.tvInfoOneRead.text = spannable

        //체크박스 상태 확인
        mBinding.checkboxRead.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mBinding.btnRead.setBackgroundResource(R.color.purple)
            } else {
                mBinding.btnRead.setBackgroundResource(R.color.very_light_pink)
            }
        }

        //버튼 클릭
        mBinding.btnRead.setOnClickListener {
            if (mBinding.checkboxRead.isChecked) {
                val name = mArrayList[0]
                val nickName = mArrayList[1]
                val phoneNumber = mArrayList[2]
                val email = mArrayList[3]
                val password = mArrayList[4]
                val authenticationPassword = mArrayList[5]
                val userRole = mArrayList[6]
                val jobName = mArrayList[7]
                val etcJobName = mArrayList[8]

                val postRequest = ReadRequest(
                    name = name,
                    nickName = nickName,
                    phoneNumber = phoneNumber,
                    email = email,
                    password = password,
                    authenticationPassword = authenticationPassword,
                    userRole = userRole,
                    jobName = jobName,
                    etcJobName = etcJobName
                )
                ReadService(this).tryPostUsers(postRequest)
            }
        }
    }

    override fun onPostReadSuccess(response: ReadResponse) {
        Log.d("sky", "onPostReadSuccess - IN")
        if (response.code == 1000) {
            val intentLogInActivity = Intent(this, LogInActivity::class.java)
            startActivity(intentLogInActivity)
            finishAffinity()
        } else {

            response.message?.let {
                CustomSnackbar.make(mBinding.root,
                    it, Snackbar.ANIMATION_MODE_SLIDE).show()
            }
        }
    }

    override fun onPostReadFailure(message: String) {
        Log.d("sky", "onPostReadFailure - $message")
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