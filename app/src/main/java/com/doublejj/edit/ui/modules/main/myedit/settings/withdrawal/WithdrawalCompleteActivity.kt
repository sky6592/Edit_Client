package com.doublejj.edit.ui.modules.main.myedit.settings.withdrawal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityWithdrawalCompleteBinding
import com.doublejj.edit.ui.modules.main.splash.SplashActivity
import com.doublejj.edit.ui.utils.span.CustomSpannableString

class WithdrawalCompleteActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityWithdrawalCompleteBinding
    private lateinit var nickName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_withdrawal_complete)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            // 초기화면으로 가기
            val sendIntent = Intent(this, SplashActivity::class.java)
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(sendIntent)
        }

        // apply span to nickname
        nickName = intent.getStringExtra("nickName") ?: ""
        val textTitle = nickName + binding.tvCompleteTitle.text.toString()
        val spanStr = CustomSpannableString(applicationContext).getPurpleActiveColorText(textTitle, nickName, R.color.purple_active)
        binding.tvCompleteTitle.setText(spanStr)
        
        binding.btnInitial.setOnClickListener { 
            // 초기화면으로 가기
            val sendIntent = Intent(this, SplashActivity::class.java)
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(sendIntent)
        }
    }

}