package com.doublejj.edit.ui.modules.main.myedit.settings.profile.change_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityChangeProfileBinding

class ChangeProfileActivity : AppCompatActivity() {
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

        binding.cvBtnPurple.setOnClickListener {
            binding.rbEmotion0.background = resources.getDrawable(R.drawable.selector_char_purple_active_0)
            binding.rbEmotion1.background = resources.getDrawable(R.drawable.selector_char_purple_active_1)
            binding.rbEmotion2.background = resources.getDrawable(R.drawable.selector_char_purple_active_2)
            binding.rbEmotion3.background = resources.getDrawable(R.drawable.selector_char_purple_active_3)
            binding.rbEmotion4.background = resources.getDrawable(R.drawable.selector_char_purple_active_4)
            binding.rbEmotion5.background = resources.getDrawable(R.drawable.selector_char_purple_active_5)
        }
        binding.cvBtnLightPurple.setOnClickListener {
            binding.rbEmotion0.background = resources.getDrawable(R.drawable.selector_char_mid_purple_0)
            binding.rbEmotion1.background = resources.getDrawable(R.drawable.selector_char_mid_purple_1)
            binding.rbEmotion2.background = resources.getDrawable(R.drawable.selector_char_mid_purple_2)
            binding.rbEmotion3.background = resources.getDrawable(R.drawable.selector_char_mid_purple_3)
            binding.rbEmotion4.background = resources.getDrawable(R.drawable.selector_char_mid_purple_4)
            binding.rbEmotion5.background = resources.getDrawable(R.drawable.selector_char_mid_purple_5)
        }
        binding.cvBtnLightBlue.setOnClickListener {
            binding.rbEmotion0.background = resources.getDrawable(R.drawable.selector_char_sky_0)
            binding.rbEmotion1.background = resources.getDrawable(R.drawable.selector_char_sky_1)
            binding.rbEmotion2.background = resources.getDrawable(R.drawable.selector_char_sky_2)
            binding.rbEmotion3.background = resources.getDrawable(R.drawable.selector_char_sky_3)
            binding.rbEmotion4.background = resources.getDrawable(R.drawable.selector_char_sky_4)
            binding.rbEmotion5.background = resources.getDrawable(R.drawable.selector_char_sky_5)
        }
        binding.cvBtnBlue.setOnClickListener {
            binding.rbEmotion0.background = resources.getDrawable(R.drawable.selector_char_navy_0)
            binding.rbEmotion1.background = resources.getDrawable(R.drawable.selector_char_navy_1)
            binding.rbEmotion2.background = resources.getDrawable(R.drawable.selector_char_navy_2)
            binding.rbEmotion3.background = resources.getDrawable(R.drawable.selector_char_navy_3)
            binding.rbEmotion4.background = resources.getDrawable(R.drawable.selector_char_navy_4)
            binding.rbEmotion5.background = resources.getDrawable(R.drawable.selector_char_navy_5)
        }

        binding.btnSave.setOnClickListener {
            // TODO : 위에 프로필 캐릭터도 변경
            // TODO : 프로필 변경 API 적용
        }
    }
}