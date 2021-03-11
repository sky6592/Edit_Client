package com.doublejj.edit.ui.modules.main.walkthrough

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityWalkThroughBinding
import com.doublejj.edit.ui.modules.main.login.LogInActivity
import com.doublejj.edit.ui.modules.main.signup.SignUpActivity

class WalkThroughActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityWalkThroughBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_walk_through)
        mBinding.walkThroughActivity = this

    }

    fun onMoveSignUp() {
        return startActivity(Intent(this, SignUpActivity::class.java))
    }

    fun onMoveLogIn() {
        return startActivity(Intent(this, LogInActivity::class.java))
    }
}