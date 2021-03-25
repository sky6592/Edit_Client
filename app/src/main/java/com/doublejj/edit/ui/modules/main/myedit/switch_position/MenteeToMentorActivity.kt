package com.doublejj.edit.ui.modules.main.myedit.switch_position

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass.Companion.USER_NICKNAME
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityMenteeToMentorBinding
import com.doublejj.edit.ui.utils.span.CustomSpannableString

class MenteeToMentorActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityMenteeToMentorBinding
    private lateinit var nickName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mentee_to_mentor)

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
            val sendIntent = Intent(this, MenteeToMentorReasonActivity::class.java)
            sendIntent.putExtra("nickName", nickName)
            startActivity(sendIntent)
        }
    }
}