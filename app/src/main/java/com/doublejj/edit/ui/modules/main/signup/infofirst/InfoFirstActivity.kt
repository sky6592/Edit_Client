package com.doublejj.edit.ui.modules.main.signup.infofirst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityInfoFirstBinding

class InfoFirstActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityInfoFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_info_first)



    }
}