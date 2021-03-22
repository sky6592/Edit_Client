package com.doublejj.edit.ui.modules.main.myedit.settings.withdrawal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityWithdrawalCompleteBinding
import com.doublejj.edit.ui.utils.span.CustomSpannableString

class WithdrawalCompleteActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityWithdrawalCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_withdrawal_complete)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            // TODO : 초기화면으로 가기
        }

        // apply span to nickname
        val nickName = intent.getStringExtra("nickName") ?: ""
        val textTitle = nickName + binding.tvCompleteTitle.text.toString()
        val spanStr = CustomSpannableString(applicationContext).getPurpleActiveColorText(textTitle, nickName, R.color.purple_active)
        binding.tvCompleteTitle.setText(spanStr)
        
        binding.btnInitial.setOnClickListener { 
            // TODO : 초기화면으로 가기
        }
    }
}