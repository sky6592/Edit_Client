package com.doublejj.edit.ui.modules.main.signup.read

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityReadBinding
import com.doublejj.edit.ui.modules.main.MainActivity

class ReadActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityReadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_read)

        mBinding.btnJobGroup.isEnabled = true
        mBinding.btnJobGroup.isClickable = true

        mBinding.btnJobGroup.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}