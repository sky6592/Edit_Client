package com.doublejj.edit.ui.modules.main.myedit.certificate_mentor
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.ApplicationClass.Companion.USER_NICKNAME
import com.doublejj.edit.ApplicationClass.Companion.sActivityList
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityCertificateMentorCompleteBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.splash.SplashActivity
import com.doublejj.edit.ui.utils.span.CustomSpannableString
class CertificateMentorCompleteActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityCertificateMentorCompleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_certificate_mentor_complete)
        // add activity at sActivityList
        ApplicationClass.sActivityList.add(this)
        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            // 메인화면으로 가기
            val sendIntent = Intent(this, MainActivity::class.java)
            startActivity(sendIntent)
            sActivityList.actFinish()
        }
        // apply span to nickname
        val nickName = sSharedPreferences.getString(USER_NICKNAME, "").toString()
        val textTitle = nickName + binding.tvCompleteTitle.text.toString()
        val spanStr = CustomSpannableString(applicationContext).getPurpleActiveColorText(textTitle, nickName, R.color.purple_active)
        binding.tvCompleteTitle.setText(spanStr)
        binding.btnMain.setOnClickListener {
            // 메인화면으로 가기
            val sendIntent = Intent(this, MainActivity::class.java)
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(sendIntent)
            sActivityList.actFinish()
        }
    }
}