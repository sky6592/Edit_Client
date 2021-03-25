package com.doublejj.edit.ui.modules.main.myedit.settings.profile.change_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass.Companion.sActivityList
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityChangePasswordCompleteBinding
import com.doublejj.edit.ui.utils.span.CustomSpannableString

class ChangePasswordCompleteActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityChangePasswordCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password_complete)

        sActivityList.add(this)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            // TODO : 내 정보로 돌아가기
            // TODO : 액티비티 여러개 종료
            sActivityList.actFinish()
        }

        // apply span to nickname
        val nickName = intent.getStringExtra("nickName") ?: ""
        val textTitle = nickName + binding.tvCompleteTitle.text.toString()
        val spanStr = CustomSpannableString(applicationContext).getPurpleActiveColorText(textTitle, nickName, R.color.purple_active)
        binding.tvCompleteTitle.setText(spanStr)
        
        binding.btnConfirm.setOnClickListener {
            // TODO : 내 정보로 돌아가기
            // TODO : 액티비티 여러개 종료
            sActivityList.actFinish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sActivityList.remove(this)
    }
}