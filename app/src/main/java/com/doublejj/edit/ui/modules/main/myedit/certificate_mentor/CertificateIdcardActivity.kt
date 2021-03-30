package com.doublejj.edit.ui.modules.main.myedit.certificate_mentor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityCertificateIdcardBinding
import com.doublejj.edit.ui.utils.span.CustomSpannableString

class CertificateIdcardActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityCertificateIdcardBinding

    val GET_GALLERY_IMAGE: Int = 2000
    private var isToggled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_certificate_idcard)

        // add activity at sActivityList
        ApplicationClass.sActivityList.add(this)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        // apply span to nickname
        val nickName = ApplicationClass.sSharedPreferences.getString(ApplicationClass.USER_NICKNAME, "").toString()
        val textSelectTitle = nickName + binding.tvCertificateTitle.text.toString()
        val textImageTitle = nickName + binding.tvCertificateImageTitle.text.toString()
        val spanSelectStr = CustomSpannableString(applicationContext).getPurpleActiveColorText(textSelectTitle, nickName, R.color.purple_active)
        val spanImageStr = CustomSpannableString(applicationContext).getPurpleActiveColorText(textImageTitle, nickName, R.color.purple_active)
        binding.tvCertificateTitle.setText(spanSelectStr)
        binding.tvCertificateImageTitle.setText(spanImageStr)

        binding.cvBtnIdcard.setOnClickListener {
            isToggled = !isToggled
            toggleButton(isToggled)
        }

        binding.llBtnGetIdcard.setOnClickListener {
            if (isToggled) {
                // 사진 띄울 이미지뷰 보이기
                binding.ivImportedIdcard.visibility = View.VISIBLE

                // 갤러리에서 사진 가져오기 띄우기
                val sendIntent = Intent(Intent.ACTION_PICK)
                sendIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                startActivityForResult(intent, GET_GALLERY_IMAGE)
            }
        }
        binding.cvBtnImportedIdcard.setOnClickListener {
            if (isToggled) Log.d("lala", "cv on")
            else Log.d("lala", "cv off")
        }
        binding.ivImportedIdcard.setOnClickListener {
            if (isToggled) Log.d("lala", "iv on")
            else Log.d("lala", "iv off")
        }

        binding.btnReselect.setOnClickListener {
            if (binding.btnReselect.isEnabled) {
                val sendIntent = Intent(this, CertificateMentorCompleteActivity::class.java)
                startActivity(sendIntent)
            }
        }
        binding.btnSelect.setOnClickListener {
            if (binding.btnSelect.isEnabled) {
                val sendIntent = Intent(this, CertificateMentorCompleteActivity::class.java)
                startActivity(sendIntent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            val selectedImageUri = data.data
            binding.ivImportedIdcard.setImageURI(selectedImageUri)
        }
    }

    fun toggleButton(on: Boolean) {
        if (!on) {
            binding.tvCertificateTitle.visibility = View.VISIBLE
            binding.tvCertificateImageTitle.visibility = View.INVISIBLE
            binding.ivGraphic.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.icon_char_gray_5))
            binding.llBtnGetIdcard.visibility = View.GONE
            binding.cvBtnImportedIdcard.visibility = View.INVISIBLE
            binding.btnReselect.visibility = View.GONE
            binding.btnSelect.visibility = View.GONE
        } else {
            binding.tvCertificateTitle.visibility = View.INVISIBLE
            binding.tvCertificateImageTitle.visibility = View.VISIBLE
            binding.ivGraphic.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.icon_char_purple_active_5))
            binding.llBtnGetIdcard.visibility = View.VISIBLE
            binding.btnReselect.visibility = View.VISIBLE
            binding.btnSelect.visibility = View.VISIBLE
            binding.ivImportedIdcard.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ApplicationClass.sActivityList.remove(this)
    }
}