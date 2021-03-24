package com.doublejj.edit.ui.modules.main.myedit.settings.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityProfileBinding
import com.doublejj.edit.ui.modules.main.myedit.settings.profile.change_profile.ChangeProfileActivity

class ProfileActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        /** setting buttons **/
        binding.llBtnChangeProfile.setOnClickListener {
            // TODO : 프로필 수정 페이지
            val sendIntent = Intent(this, ChangeProfileActivity::class.java)
            startActivity(sendIntent)
        }
        binding.llBtnChangePassword.setOnClickListener {
            // TODO : 비밀번호 변경 페이지
            /*val sendIntent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(sendIntent)*/
        }
        binding.llBtnChangePosition.setOnClickListener {
            // TODO : 직군 변경 페이지
            /*val sendIntent = Intent(this, ChangePositionActivity::class.java)
            startActivity(sendIntent)*/
        }
    }
}