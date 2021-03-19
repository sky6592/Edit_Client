package com.doublejj.edit.ui.modules.main.signup.infosecond

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityInfoSecondBinding
import com.doublejj.edit.ui.modules.main.signup.slectjopgroup.JobGroupActivity

class InfoSecondActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityInfoSecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_info_second)

        mBinding.btnInfoSecond.isEnabled = true
        mBinding.btnInfoSecond.isClickable = true
        mBinding.btnInfoSecond.setOnClickListener {
            startActivity(Intent(this, JobGroupActivity::class.java))
        }

    }
}