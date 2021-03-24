package com.doublejj.edit.ui.modules.main.myedit

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.databinding.MyeditFragmentBinding
import com.doublejj.edit.ui.modules.main.myedit.settings.SettingsActivity
import com.doublejj.edit.ui.modules.main.myedit.settings.profile.ProfileActivity

class MyeditFragment : Fragment() {
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

        when (ApplicationClass.sSharedPreferences.getString(ApplicationClass.USER_POSITION, "MENTEE")) {
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
            // TODO : 내 정보 페이지
            Log.d(TAG, "clicked profile")
            val sendIntent = Intent(activity, ProfileActivity::class.java)
            // TODO : 프로필 받아서 테스트 교체
            val nickName = "테스트"
            val userRole = "멘티님"
            val emotionName = "relief"
            val colorName = "purple"
            sendIntent.putExtra("nickName", nickName)
            sendIntent.putExtra("userRole", userRole)
            sendIntent.putExtra("emotionName", emotionName)
            sendIntent.putExtra("colorName", colorName)
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

}