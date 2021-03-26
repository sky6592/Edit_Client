package com.doublejj.edit.ui.modules.main.signup.slecttype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivitySelectTypeBinding
import com.doublejj.edit.ui.modules.main.signup.slectjopgroup.JobGroupActivity

class SelectTypeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySelectTypeBinding
    private lateinit var mArrayList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_type)
        mArrayList = intent.getSerializableExtra("arrayList") as ArrayList<String>

        mBinding.btnSelectType.setOnClickListener {
            Log.d("sky",mBinding.ivMenteeSelect.isPressed.toString())
            Log.d("sky",mBinding.ivMentorSelect.isPressed.toString())

            val intentJobGroup = Intent(this, JobGroupActivity::class.java)
            if (mBinding.ivMentorSelect.isPressed) {
                val mentee = "MENTEE"
                mArrayList.add(mentee)
                intentJobGroup.putExtra("arrayList", mArrayList)
                startActivity(intentJobGroup)
                finish()
            }
            if (mBinding.ivMentorSelect.isPressed) {
                val mentor = "MENTOR"
                mArrayList.add(mentor)
                intentJobGroup.putExtra("arrayList", mArrayList)
                startActivity(intentJobGroup)
                finish()
            }
        }


    }
}