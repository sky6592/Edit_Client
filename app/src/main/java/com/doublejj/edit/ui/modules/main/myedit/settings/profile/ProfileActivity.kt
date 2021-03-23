package com.doublejj.edit.ui.modules.main.myedit.settings.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityProfileBinding

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
//            val intent = Intent(this, ChangeProfileActivity::class.java)
            // TODO : 프로필 받아서 테스트 교체
            val nickName = "테스트"
            val userRole = "멘티님"
            val emotionName = "relief"
            val colorName = "purple"
            intent.putExtra("nickName", nickName)
            intent.putExtra("userRole", userRole)
            intent.putExtra("emotionName", emotionName)
            intent.putExtra("colorName", colorName)
            startActivity(intent)
        }
        binding.llBtnChangePassword.setOnClickListener {
            // TODO : 비밀번호 변경 페이지
//            val intent = Intent(this, ChangePasswordActivity::class.java)
            // TODO : 프로필 받아서 테스트 교체
            val nickName = "테스트"
            intent.putExtra("nickName", nickName)
            startActivity(intent)
        }
        binding.llBtnChangePosition.setOnClickListener {
            // TODO : 직군 변경 페이지
//            val intent = Intent(this, ChangePositionActivity::class.java)
            startActivity(intent)
        }
    }
}