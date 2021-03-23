package com.doublejj.edit.ui.modules.main.signup.slectjopgroup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityJobGroupBinding
import com.doublejj.edit.ui.modules.main.signup.read.ReadActivity

class JobGroupActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityJobGroupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_job_group)

        mBinding.btnJobGroup.isEnabled = true
        mBinding.btnJobGroup.isClickable = true

        mBinding.btnJobGroup.setOnClickListener {
            startActivity(Intent(this, ReadActivity::class.java))
        }
    }
}