package com.doublejj.edit.ui.modules.main.myedit

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.ApplicationClass.Companion.USER_COLOR
import com.doublejj.edit.ApplicationClass.Companion.USER_EMOTION
import com.doublejj.edit.ApplicationClass.Companion.USER_NICKNAME
import com.doublejj.edit.ApplicationClass.Companion.USER_POSITION
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.profile.info.ProfileInfoService
import com.doublejj.edit.data.api.services.profile.info.ProfileInfoView
import com.doublejj.edit.data.models.profile.info.ProfileInfoResponse
import com.doublejj.edit.databinding.MyeditFragmentBinding
import com.doublejj.edit.ui.modules.main.myedit.settings.SettingsActivity
import com.doublejj.edit.ui.modules.main.myedit.settings.profile.ProfileActivity
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class MyeditFragment : Fragment(), ProfileInfoView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: MyeditFragmentBinding
    private lateinit var viewModel: MyeditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.myedit_fragment, container, false)
        viewModel = ViewModelProvider(this).get(MyeditViewModel::class.java)

        binding.myeditViewModel = viewModel
        binding.lifecycleOwner = this

        ProfileInfoService(this).tryGetProfileInfo()

        when (sSharedPreferences.getString(USER_POSITION, "MENTEE")) {
            "MENTEE" -> {
                binding.tvPosition.text = "멘티님"
                binding.llBtnMenuSentence.visibility = View.VISIBLE
                binding.llBtnMenuComplete.visibility = View.VISIBLE
                binding.llBtnMenuCoin.visibility = View.GONE
                binding.llBtnMenuCommentList.visibility = View.GONE
                binding.llBtnCertificateMentor.visibility = View.GONE
            }
            "MENTOR" -> {
                binding.tvPosition.text = "멘토님"
                binding.llBtnMenuCoin.visibility = View.VISIBLE
                binding.llBtnMenuCommentList.visibility = View.VISIBLE
                binding.llBtnMenuSentence.visibility = View.GONE
                binding.llBtnMenuComplete.visibility = View.GONE
                binding.llBtnCertificateMentor.visibility = View.VISIBLE
            }
        }

        /** toolbar buttons **/
        binding.tvLogo.setOnClickListener { 
            // TODO : 맨 위로 가기
        }
        binding.ibSettings.setOnClickListener {
            val sendIntent = Intent(activity, SettingsActivity::class.java)
            startActivity(sendIntent)
        }

        /** menu buttons **/
        binding.ibMenuProfile.setOnClickListener {
            // 내 정보 페이지
            val sendIntent = Intent(activity, ProfileActivity::class.java)
            startActivity(sendIntent)
        }
        binding.ibMenuCoin.setOnClickListener {
            // TODO : 코인 페이지 (멘토)
        }
        binding.ibMenuSympathy.setOnClickListener {
            // TODO : 공감 페이지
        }
        binding.ibMenuSentence.setOnClickListener {
            // TODO : 자소서 목록 페이지 (멘티)
        }
        binding.ibMenuComplete.setOnClickListener {
            // TODO : 자소서 완성 페이지 (멘티)
        }
        binding.ibMenuCommentList.setOnClickListener {
            // TODO : 코멘트 목록 페이지 (멘토)
        }
        binding.ibMenuTemp.setOnClickListener {
            // TODO : 임시 저장 페이지
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.tvNickname.text = sSharedPreferences.getString(USER_NICKNAME, null)
        binding.tvPosition.text = (requireContext().applicationContext as ApplicationClass).getPostionToString(
            sSharedPreferences.getString(USER_POSITION, null))
        binding.ivProfile.setImageResource((requireContext().applicationContext as ApplicationClass).getCharacterResId(
            sSharedPreferences.getString(USER_COLOR, null).toString(),
            sSharedPreferences.getString(USER_EMOTION, null).toString()
        ))
    }

    override fun onProfileInfoSuccess(response: ProfileInfoResponse) {
        if (response.isSuccess) {
            val editor = sSharedPreferences.edit()
            editor.putString(USER_NICKNAME, response.result.nickName)
            editor.putString(USER_EMOTION, response.result.emotionName)
            editor.putString(USER_COLOR, response.result.colorName)
            editor.putString(USER_POSITION, response.result.userRole)
            editor.commit()
            editor.apply()

            binding.tvNickname.text = response.result.nickName
            binding.tvPosition.text = (requireContext().applicationContext as ApplicationClass).getPostionToString(response.result.userRole)
            binding.ivProfile.setImageResource((requireContext().applicationContext as ApplicationClass).getCharacterResId(response.result.colorName, response.result.emotionName))
        }
    }

    override fun onProfileInfoFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
    }
}