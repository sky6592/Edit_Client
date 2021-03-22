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
import com.doublejj.edit.R
import com.doublejj.edit.databinding.MyeditFragmentBinding
import com.doublejj.edit.ui.modules.main.myedit.setting.SettingActivity
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class MyeditFragment : Fragment() {
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
            startActivity(Intent(activity, SettingActivity::class.java))
        }

        /** menu buttons **/
        binding.ibMenuProfile.setOnClickListener {
            // TODO : 내 정보 페이지
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
        binding.ibMenuProfile.setOnClickListener {
            // TODO : 임시 저장 페이지
        }

        return binding.root
    }

}