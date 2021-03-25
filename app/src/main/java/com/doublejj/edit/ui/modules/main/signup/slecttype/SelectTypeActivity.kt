package com.doublejj.edit.ui.modules.main.signup.slecttype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivitySelectTypeBinding

class SelectTypeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySelectTypeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_type)


    }
}