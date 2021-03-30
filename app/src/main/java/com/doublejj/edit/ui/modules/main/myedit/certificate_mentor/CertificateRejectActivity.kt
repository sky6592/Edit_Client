package com.doublejj.edit.ui.modules.main.myedit.certificate_mentor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass.Companion.sActivityList
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityCertificateRejectBinding

class CertificateRejectActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityCertificateRejectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_certificate_reject)

        // add activity at sActivityList
        sActivityList.add(this)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.btnReapply.setOnClickListener {
            // 신분증 첨부 화면으로 이동
            val sendIntent = Intent(this, CertificateIdcardActivity::class.java)
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(sendIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sActivityList.remove(this)
    }
}