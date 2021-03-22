package com.doublejj.edit.ui.modules.main.myedit.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivitySettingsBinding
import com.doublejj.edit.ui.modules.main.myedit.settings.withdrawal.WithdrawalActivity

class SettingsActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }
        
        /** setting buttons **/
        binding.llBtnNotice.setOnClickListener { 
            // TODO : 공지사항 페이지
        }
        binding.llBtnServiceCenter.setOnClickListener { 
            // TODO : 고객센터 페이지
        }
        binding.llBtnOpensource.setOnClickListener { 
            // TODO : 오픈소스 페이지
        }
        binding.llBtnAppVersion.setOnClickListener {
            // TODO : 버전정보 페이지
        }
        binding.llBtnAccountWithdrawal.setOnClickListener { 
            val intent = Intent(this, WithdrawalActivity::class.java)
            // TODO : 닉네임 받아서 테스트 교체
            val nickName = "테스트"
            intent.putExtra("nickName", nickName)
            startActivity(intent)
        }
    }
}