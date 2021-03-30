package com.doublejj.edit.ui.modules.main.signup.slecttype

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivitySelectTypeBinding
import com.doublejj.edit.databinding.DialogEmailFindBinding
import com.doublejj.edit.ui.modules.main.signup.slectjopgroup.JobGroupActivity
import com.doublejj.edit.ui.modules.main.walkthrough.WalkThroughActivity

class SelectTypeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySelectTypeBinding
    private var mIvMentorFlag: Boolean = false
    private var mIvMenteeFlag: Boolean = false
    private lateinit var mArrayList: ArrayList<String>

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_type)

        //< 클릭
        mBinding.ivBackSelectType.setOnClickListener {
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

        mArrayList = intent.getSerializableExtra("arrayList") as ArrayList<String>
        val nickname = mArrayList[1]

        //닉네임 설명
        mBinding.tvInfoTwoSelectType.text = nickname + getString(R.string.tv_info_two_select_type)
        val spannable = SpannableStringBuilder(mBinding.tvInfoTwoSelectType.text)
        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#5a32dc")),
            0,
            nickname.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        mBinding.tvInfoTwoSelectType.text = spannable


        //멘토
        mBinding.ivMentorSelect.setOnClickListener {
            Log.d("sky", "멘토 클릭")
            mIvMentorFlag = true
            mIvMenteeFlag = false
            mBinding.ivMenteeSelect.setImageResource(R.drawable.icon_mentee)

            //선택에 따른 버튼 색상
            if (mIvMentorFlag || mIvMenteeFlag) {
                mBinding.btnSelectType.setBackgroundResource(R.color.purple)
                mBinding.ivMentorSelect.setImageResource(R.drawable.icon_mentor_act)
            } else {
                mBinding.btnSelectType.setBackgroundResource(R.color.very_light_pink)
                mBinding.ivMentorSelect.setImageResource(R.drawable.icon_mentor)
            }
        }

        //멘티
        mBinding.ivMenteeSelect.setOnClickListener {
            Log.d("sky", "멘티 클릭")
            mIvMenteeFlag = true
            mIvMentorFlag = false
            mBinding.ivMentorSelect.setImageResource(R.drawable.icon_mentor)

            //선택에 따른 버튼 색상
            if (mIvMentorFlag || mIvMenteeFlag) {
                mBinding.btnSelectType.setBackgroundResource(R.color.purple)
                mBinding.ivMenteeSelect.setImageResource(R.drawable.icon_mentee_act)
            } else {
                mBinding.btnSelectType.setBackgroundResource(R.color.very_light_pink)
                mBinding.ivMenteeSelect.setImageResource(R.drawable.icon_mentee)
            }
        }

        //버튼 : 다음으로
        mBinding.btnSelectType.setOnClickListener {
            Log.d("sky", "버튼클릭 - $mIvMentorFlag, $mIvMenteeFlag")

            val intentJobGroup = Intent(this, JobGroupActivity::class.java)
            if (mIvMenteeFlag) {
                val mentee = "MENTEE"
                mArrayList.add(mentee)
                intentJobGroup.putExtra("arrayList", mArrayList)
                startActivity(intentJobGroup)
                finish()
            }
            if (mIvMentorFlag) {
                val mentor = "MENTOR"
                mArrayList.add(mentor)
                intentJobGroup.putExtra("arrayList", mArrayList)
                startActivity(intentJobGroup)
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