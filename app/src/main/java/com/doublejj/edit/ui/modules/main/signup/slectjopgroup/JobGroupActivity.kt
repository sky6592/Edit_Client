package com.doublejj.edit.ui.modules.main.signup.slectjopgroup

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
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityJobGroupBinding
import com.doublejj.edit.databinding.DialogEmailFindBinding
import com.doublejj.edit.databinding.DialogJobGroupBinding
import com.doublejj.edit.ui.modules.main.signup.read.ReadActivity
import com.doublejj.edit.ui.modules.main.walkthrough.WalkThroughActivity

class JobGroupActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityJobGroupBinding
    private lateinit var mArrayList: ArrayList<String>
    private var mSelectRadioFlag: Boolean = false
    private var mEnterRadioFlag: Boolean = false

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_job_group)
        mArrayList = intent.getSerializableExtra("arrayList") as ArrayList<String>
        Log.d("sky", mArrayList.toString())

        //< 클릭
        mBinding.ivBackJobGroup.setOnClickListener {
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
        //멘토,멘티 세팅준비
        var selectType = ""
        var endLength = 0

        //멘티 세팅
        if (mArrayList[6] == "MENTEE") {
            selectType = "멘티"
            endLength = nickname.length + selectType.length
            mBinding.tvInfoTwoJobGroup.text =
                nickname + " " + selectType + getString(R.string.tv_mentee_info_job_group)
            val spannable = SpannableStringBuilder(mBinding.tvInfoTwoJobGroup.text)
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor("#5a32dc")),
                0,
                endLength + 1,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            mBinding.tvInfoTwoJobGroup.text = spannable
        }
        //멘토 세팅
        else {
            selectType = "멘토"
            endLength = nickname.length + selectType.length
            mBinding.tvInfoTwoJobGroup.text =
                nickname + " " + selectType + getString(R.string.tv_mentor_info_job_group)
            val spannable = SpannableStringBuilder(mBinding.tvInfoTwoJobGroup.text)
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor("#5a32dc")),
                0,
                endLength + 1,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            mBinding.tvInfoTwoJobGroup.text = spannable
        }


        //직군 선택하기
        mBinding.tvInfoSelectJobGroup.setOnClickListener {
            //다이얼로그(라디오버튼)
            val builder = AlertDialog.Builder(this)
            val binding: DialogJobGroupBinding = DialogJobGroupBinding.inflate(layoutInflater)

            //멘티 : 희망직군 선택
            if (selectType == "멘티") {
                binding.tvDialogTitleJobGroup.text = getString(R.string.tv_mentee_change_job_select)
            }

            //라디오버튼 선택
            binding.rgDialogJobGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.rb_job_group_0 -> {
                        mBinding.tvInfoSelectJobGroup.text = binding.rbJobGroup0.text
                        mSelectRadioFlag = true
                    }
                    R.id.rb_job_group_0 -> {
                        mBinding.tvInfoSelectJobGroup.text =
                            binding.rbJobGroup0.text
                        mSelectRadioFlag = true
                    }
                    R.id.rb_job_group_1 -> {
                        mBinding.tvInfoSelectJobGroup.text =
                            binding.rbJobGroup1.text
                        mSelectRadioFlag = true
                    }
                    R.id.rb_job_group_2 -> {
                        mBinding.tvInfoSelectJobGroup.text =
                            binding.rbJobGroup2.text
                        mSelectRadioFlag = true
                    }
                    R.id.rb_job_group_3 -> {
                        mBinding.tvInfoSelectJobGroup.text =
                            binding.rbJobGroup3.text
                        mSelectRadioFlag = true
                    }
                    R.id.rb_job_group_4 -> {
                        mBinding.tvInfoSelectJobGroup.text =
                            binding.rbJobGroup4.text
                        mSelectRadioFlag = true
                    }
                    R.id.rb_job_group_5 -> {
                        mBinding.tvInfoSelectJobGroup.text =
                            binding.rbJobGroup5.text
                        mSelectRadioFlag = true
                    }
                    R.id.rb_job_group_6 -> {
                        mBinding.tvInfoSelectJobGroup.text =
                            binding.rbJobGroup6.text
                        mSelectRadioFlag = true
                    }
                    R.id.rb_job_group_7 -> {
                        mBinding.tvInfoSelectJobGroup.text =
                            binding.rbJobGroup7.text
                        mSelectRadioFlag = true
                    }
                    R.id.rb_job_group_8 -> {
                        mBinding.tvInfoSelectJobGroup.text =
                            binding.rbJobGroup8.text
                        mSelectRadioFlag = true
                    }
                    R.id.rb_job_group_9 -> {
                        mBinding.tvInfoSelectJobGroup.text =
                            binding.rbJobGroup9.text
                        mSelectRadioFlag = true
                    }
                    R.id.rb_job_group_10 -> {
                        mBinding.tvInfoSelectJobGroup.text =
                            binding.rbJobGroup10.text
                        mSelectRadioFlag = true
                    }
                    R.id.rb_job_group_11 -> {
                        mBinding.tvInfoSelectJobGroup.text =
                            binding.rbJobGroup11.text
                        mSelectRadioFlag = true
                    }
                    //직접입력
                    R.id.rb_job_group_12 -> {
                        mBinding.tvInfoSelectJobGroup.text = binding.rbJobGroup12.text
                        mBinding.etEnterJobGroup.visibility = View.VISIBLE
                        mBinding.tvCaptionEnterJobGroup.visibility = View.VISIBLE
                    }
                }
            }

            //취소
            builder.setNegativeButton(getString(R.string.tv_dialog_dismiss)) { _, _ ->
                //모두 초기
                mBinding.tvInfoSelectJobGroup.text = getString(R.string.spinner_hint)
                mBinding.etEnterJobGroup.visibility = View.GONE
                mBinding.tvCaptionEnterJobGroup.visibility = View.GONE
                mBinding.btnJobGroup.setBackgroundResource(R.color.very_light_pink)
                mSelectRadioFlag = false
                mEnterRadioFlag = false
            }
            //선택하기
            builder.setPositiveButton(getString(R.string.tv_dialog_confirm)) { _, _ ->
                if (mSelectRadioFlag || mEnterRadioFlag) {
                    mBinding.btnJobGroup.setBackgroundResource(R.color.purple)
                    mEnterRadioFlag = true
                } else {
                    mBinding.btnJobGroup.setBackgroundResource(R.color.very_light_pink)
                }
            }
            builder.setView(binding.root).show()
        }

        //직접 입력하기 세팅
        mBinding.etEnterJobGroup.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                //직군 정규식
                var jobPattern = "^[a-zA-Z가-힣]$"
                if (jobPattern.matches(s.toString().toRegex())) {
                    mEnterRadioFlag = true
                    mBinding.tvCaptionEnterJobGroup.text = getString(R.string.tv_result_job_group)
                    mBinding.tvCaptionEnterJobGroup.setTextColor(R.color.purple)
                    mBinding.btnJobGroup.setBackgroundResource(R.color.purple)
                } else {
                    mBinding.tvCaptionEnterJobGroup.text =
                        getString(R.string.tv_change_job_etc_caption)
                    mBinding.tvCaptionEnterJobGroup.setTextColor(R.color.purple)
                }
            }
        })

        //다음으로 버튼
        mBinding.btnJobGroup.setOnClickListener {
            val selectJobGroup = mBinding.tvInfoSelectJobGroup.text.trim().toString()
            val enterJobGroup = mBinding.etEnterJobGroup.text.trim().toString()
            val etcJobName = "NONE"
            val intentReadActivity = Intent(this, ReadActivity::class.java)

            //라디오 선택
            if (mSelectRadioFlag) {
                mArrayList.add(selectJobGroup)
                mArrayList.add(etcJobName)
                intentReadActivity.putExtra("arrayList", mArrayList)
                startActivity(intentReadActivity)
                finish()
            }
            if (mEnterRadioFlag) {
                mArrayList.add(selectJobGroup)
                mArrayList.add(enterJobGroup)
                intentReadActivity.putExtra("arrayList", mArrayList)
                startActivity(intentReadActivity)
                finish()
            }
        }
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