package com.doublejj.edit.ui.modules.main.myedit.certificate_mentor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.data.api.retrofitinterfaces.certificate_mentor.ImageRequest
import com.doublejj.edit.data.api.services.certificate_mentor.AuthMentorService
import com.doublejj.edit.data.api.services.certificate_mentor.AuthMentorView
import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.databinding.ActivityCertificateIdcardBinding
import com.doublejj.edit.ui.utils.dialog.CustomLoadingDialog
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.doublejj.edit.ui.utils.span.CustomSpannableString
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream


class CertificateIdcardActivity : AppCompatActivity(), AuthMentorView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityCertificateIdcardBinding

    val GET_GALLERY_IMAGE: Int = 2000
    private var isToggled = false
    private var selectedImageUri: Uri? = null
    private var fileName: String? = null
    private var bitmap: Bitmap? = null

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
        val nickName = ApplicationClass.sSharedPreferences.getString(
            ApplicationClass.USER_NICKNAME,
            ""
        ).toString()
        val textSelectTitle = nickName + binding.tvCertificateTitle.text.toString()
        val textImageTitle = nickName + binding.tvCertificateImageTitle.text.toString()
        val spanSelectStr = CustomSpannableString(applicationContext).getPurpleActiveColorText(
            textSelectTitle,
            nickName,
            R.color.purple_active
        )
        val spanImageStr = CustomSpannableString(applicationContext).getPurpleActiveColorText(
            textImageTitle,
            nickName,
            R.color.purple_active
        )
        binding.tvCertificateTitle.setText(spanSelectStr)
        binding.tvCertificateImageTitle.setText(spanImageStr)

        binding.cvToggleIdcard.setOnClickListener {
            isToggled = !isToggled
            toggleButton(isToggled)
        }

        binding.llBtnGetIdcard.setOnClickListener {
            if (isToggled) {
                // permission
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            // 권한 설정 완료

                            // 갤러리에서 사진 가져오기 띄우기
                            val sendIntent = Intent(Intent.ACTION_PICK)
                            sendIntent.setDataAndType(
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*"
                            )
                            startActivityForResult(sendIntent, GET_GALLERY_IMAGE)

                        }
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            // 권한 설정 요청
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf<kotlin.String?>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
                        }
                    }
            }
        }

        binding.btnReselect.setOnClickListener {
            if (binding.btnReselect.isEnabled) {
                // 사진 불러올 이미지 카드 비활성화
                binding.cvImportedIdcard.visibility = View.INVISIBLE

                // 불러오기 버튼 활성화
                binding.llBtnGetIdcard.visibility = View.VISIBLE
                binding.llBtnGetIdcard.isEnabled = true

                // 이미지뷰 초기화
                selectedImageUri = null
                binding.ivImportedIdcard.setImageURI(selectedImageUri)
            }
        }
        binding.btnSelect.setOnClickListener {
            if (binding.btnSelect.isEnabled && selectedImageUri != null) {
                val encodedImage = bitmapToByteArray()

                // apply auth mentor API
                AuthMentorService(this).tryPostAuthMentor(ImageRequest(encodedImage))
            }
        }
    }

    fun bitmapToByteArray() : String {
        val stream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        val serialized = Base64.encodeToString(byteArray, Base64.NO_WRAP)
        Log.d("tag", "bitmap converted base64: ${serialized}")

        return serialized
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            // 사진 불러올 이미지 카드 활성화
            binding.cvImportedIdcard.visibility = View.VISIBLE

            // 불러오기 버튼 비활성화
            binding.llBtnGetIdcard.visibility = View.INVISIBLE
            binding.llBtnGetIdcard.isEnabled = false

            // 이미지뷰에 선택한 사진 할당
            selectedImageUri = data.data
            binding.ivImportedIdcard.setImageURI(selectedImageUri)

            // Uri에서 이미지 이름 추출
            fileName = getImageNameToUri(selectedImageUri!!)

            Log.d("tag", "fileName: $fileName")

            // 이미지 데이터를 비트맵으로 받기
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
            // 비트맵 리사이즈 (800x600)
            bitmap = Bitmap.createScaledBitmap(bitmap!!, 800, 600, true)
        }
    }

    fun getImageNameToUri(uri: Uri) : String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, proj, null, null, null)
        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()

        val imgPath = cursor.getString(columnIndex)
//        val imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1)

        return imgPath
    }

    fun toggleButton(on: Boolean) {
        if (!on) {
            binding.tvCertificateTitle.visibility = View.VISIBLE
            binding.tvCertificateImageTitle.visibility = View.INVISIBLE
            binding.ivGraphic.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.icon_char_gray_5
                )
            )
            binding.llBtnGetIdcard.visibility = View.GONE
            binding.cvImportedIdcard.visibility = View.INVISIBLE
            binding.btnReselect.visibility = View.GONE
            binding.btnSelect.visibility = View.GONE
        } else {
            binding.tvCertificateTitle.visibility = View.INVISIBLE
            binding.tvCertificateImageTitle.visibility = View.VISIBLE
            binding.ivGraphic.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.icon_char_purple_active_5
                )
            )
            binding.llBtnGetIdcard.visibility = View.VISIBLE
            binding.cvImportedIdcard.visibility = View.INVISIBLE
            binding.btnReselect.visibility = View.VISIBLE
            binding.btnSelect.visibility = View.VISIBLE
        }
    }

    override fun onAuthMentorSuccess(response: BaseResponse) {
        if (response.isSuccess) {
            val sendIntent = Intent(this, CertificateMentorCompleteActivity::class.java)
            startActivity(sendIntent)
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        CustomLoadingDialog(this).dismiss()
    }

    override fun onAuthMentorFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

        CustomLoadingDialog(this).dismiss()
    }

//    override fun onResume() {
//        super.onResume()
//
//        selectedImageUri = null
//    }

    override fun onDestroy() {
        super.onDestroy()
        ApplicationClass.sActivityList.remove(this)
    }


}