package com.doublejj.edit.ui.modules.main.myedit.settings.profile.change_profile

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.ApplicationClass.Companion.USER_COLOR
import com.doublejj.edit.ApplicationClass.Companion.USER_EMOTION
import com.doublejj.edit.ApplicationClass.Companion.USER_NICKNAME
import com.doublejj.edit.ApplicationClass.Companion.USER_POSITION
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.profile.change_profile.ChangeProfileService
import com.doublejj.edit.data.api.services.profile.change_profile.ChangeProfileView
import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.profile.change_profile.ChangeProfileRequest
import com.doublejj.edit.databinding.ActivityChangeProfileBinding
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class ChangeProfileActivity : AppCompatActivity(), ChangeProfileView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityChangeProfileBinding
    private var selectedColor = ""
    private var selectedEmotion = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_profile)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        // 이름, 역할 출력
        binding.tvNickname.text = sSharedPreferences.getString(USER_NICKNAME, null)
        binding.tvPosition.text = (applicationContext as ApplicationClass).getPostionToString(sSharedPreferences.getString(USER_POSITION, null))

        // 캐릭터 표정 및 색상 선택에 따른 이미지 출력 변경
        selectedColor = sSharedPreferences.getString(USER_COLOR, "purple").toString()
        selectedEmotion = sSharedPreferences.getString(USER_EMOTION, "relief").toString()
        binding.ivProfile.setImageResource((applicationContext as ApplicationClass).getCharacterResId(selectedColor, selectedEmotion))

        // 기존 캐릭터 리소스 배치
        setSelectedResource(selectedColor)
        binding.rgEmotion.check(getSelectedId(selectedEmotion))

        // 색상 선택
        binding.cvBtnPurple.setOnClickListener {
            selectedColor = "purple"
            setSelectedResource(selectedColor)
        }
        binding.cvBtnLightPurple.setOnClickListener {
            selectedColor = "lightPurple"
            setSelectedResource(selectedColor)
        }
        binding.cvBtnLightBlue.setOnClickListener {
            selectedColor = "lightBlue"
            setSelectedResource(selectedColor)
        }
        binding.cvBtnBlue.setOnClickListener {
            selectedColor = "blue"
            setSelectedResource(selectedColor)
        }
        
        // 표정 선택
        binding.rgEmotion.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_emotion_0 -> selectedEmotion = "relief"
                R.id.rb_emotion_1 -> selectedEmotion = "bigSmile"
                R.id.rb_emotion_2 -> selectedEmotion = "surprise"
                R.id.rb_emotion_3 -> selectedEmotion = "happy"
                R.id.rb_emotion_4 -> selectedEmotion = "smallSmile"
                R.id.rb_emotion_5 -> selectedEmotion = "wink"
            }
            binding.ivProfile.setImageResource((applicationContext as ApplicationClass).getCharacterResId(selectedColor, selectedEmotion))
        }

        // 저장하기 버튼
        binding.btnSave.setOnClickListener {
            // 프로필 변경 API
            ChangeProfileService(this).tryPatchChangeProfile(
                ChangeProfileRequest(
                    colorName = selectedColor,
                    emotionName = selectedEmotion
                )
            )
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setSelectedResource(color: String) {
        when (color) {
            "purple" -> {
                binding.rbEmotion0.background = resources.getDrawable(R.drawable.selector_char_purple_active_0)
                binding.rbEmotion1.background = resources.getDrawable(R.drawable.selector_char_purple_active_1)
                binding.rbEmotion2.background = resources.getDrawable(R.drawable.selector_char_purple_active_2)
                binding.rbEmotion3.background = resources.getDrawable(R.drawable.selector_char_purple_active_3)
                binding.rbEmotion4.background = resources.getDrawable(R.drawable.selector_char_purple_active_4)
                binding.rbEmotion5.background = resources.getDrawable(R.drawable.selector_char_purple_active_5)
            }
            "lightPurple" -> {
                binding.rbEmotion0.background = resources.getDrawable(R.drawable.selector_char_mid_purple_0)
                binding.rbEmotion1.background = resources.getDrawable(R.drawable.selector_char_mid_purple_1)
                binding.rbEmotion2.background = resources.getDrawable(R.drawable.selector_char_mid_purple_2)
                binding.rbEmotion3.background = resources.getDrawable(R.drawable.selector_char_mid_purple_3)
                binding.rbEmotion4.background = resources.getDrawable(R.drawable.selector_char_mid_purple_4)
                binding.rbEmotion5.background = resources.getDrawable(R.drawable.selector_char_mid_purple_5)
            }
            "lightBlue" -> {
                binding.rbEmotion0.background = resources.getDrawable(R.drawable.selector_char_sky_0)
                binding.rbEmotion1.background = resources.getDrawable(R.drawable.selector_char_sky_1)
                binding.rbEmotion2.background = resources.getDrawable(R.drawable.selector_char_sky_2)
                binding.rbEmotion3.background = resources.getDrawable(R.drawable.selector_char_sky_3)
                binding.rbEmotion4.background = resources.getDrawable(R.drawable.selector_char_sky_4)
                binding.rbEmotion5.background = resources.getDrawable(R.drawable.selector_char_sky_5)
            }
            "blue" -> {
                binding.rbEmotion0.background = resources.getDrawable(R.drawable.selector_char_navy_0)
                binding.rbEmotion1.background = resources.getDrawable(R.drawable.selector_char_navy_1)
                binding.rbEmotion2.background = resources.getDrawable(R.drawable.selector_char_navy_2)
                binding.rbEmotion3.background = resources.getDrawable(R.drawable.selector_char_navy_3)
                binding.rbEmotion4.background = resources.getDrawable(R.drawable.selector_char_navy_4)
                binding.rbEmotion5.background = resources.getDrawable(R.drawable.selector_char_navy_5)
            }
        }

        binding.ivProfile.setImageResource((applicationContext as ApplicationClass).getCharacterResId(selectedColor, selectedEmotion))
    }
    fun getSelectedId(emotion: String) : Int {
        var idPath = "rb_emotion_"
        when (emotion) {
            "relief" -> idPath += "0"
            "bigSmile" -> idPath += "1"
            "surprise" -> idPath += "2"
            "happy" -> idPath += "3"
            "smallSmile" -> idPath += "4"
            "wink" -> idPath += "5"
        }
        return applicationContext.resources!!.getIdentifier(idPath, "id", packageName)
    }

    override fun onChangeProfileSuccess(response: BaseResponse) {
        if (response.isSuccess) {
            // sSharedPreferences에 캐릭터 갱신
            val editor = sSharedPreferences.edit()
            editor.putString(USER_COLOR, selectedColor)
            editor.putString(USER_EMOTION, selectedEmotion)
            editor.commit()
            editor.apply()
            
            // ivProfile에 해당 캐릭터 반영
            binding.ivProfile.setImageResource((applicationContext as ApplicationClass).getCharacterResId(selectedColor, selectedEmotion))

            // 변경 성공 스낵바
            finish()
        }
    }

    override fun onChangeProfileFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
    }
}