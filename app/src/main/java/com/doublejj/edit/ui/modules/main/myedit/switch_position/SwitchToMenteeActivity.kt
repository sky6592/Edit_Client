package com.doublejj.edit.ui.modules.main.myedit.switch_position

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass.Companion.USER_NICKNAME
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivitySwitchToMenteeBinding
import com.doublejj.edit.ui.utils.span.CustomSpannableString

class SwitchToMenteeActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivitySwitchToMenteeBinding
    private lateinit var nickName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_switch_to_mentee)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        // apply span to nickname
        nickName = sSharedPreferences.getString(USER_NICKNAME, "").toString()
        val textTitle = nickName + binding.tvEncourageTitle.text.toString()
        val spanStr = CustomSpannableString(applicationContext).getPurpleActiveColorText(textTitle, nickName, R.color.purple_active)
        binding.tvEncourageTitle.setText(spanStr)

        binding.btnNext.setOnClickListener {
            val sendIntent = Intent(this, SwitchToMenteeReasonActivity::class.java)
            sendIntent.putExtra("nickName", nickName)
            startActivity(sendIntent)
        }
    }
}