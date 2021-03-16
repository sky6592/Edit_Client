package com.doublejj.edit.ui.modules.main.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityLogInBinding
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.data.api.services.LogInView

class LogInActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)


    }
}