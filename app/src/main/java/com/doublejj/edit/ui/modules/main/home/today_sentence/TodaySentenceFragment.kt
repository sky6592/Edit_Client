package com.doublejj.edit.ui.modules.main.home.today_sentence

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.doublejj.edit.ApplicationClass.Companion.MENTEE_SENTENCE_LIMIT
import com.doublejj.edit.ApplicationClass.Companion.USER_POSITION
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.databinding.TodaySentenceFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class TodaySentenceFragment : Fragment() {
    private lateinit var binding: TodaySentenceFragmentBinding
    private lateinit var viewModel: TodaySentenceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.today_sentence_fragment, container, false)
        viewModel = ViewModelProvider(this).get(TodaySentenceViewModel::class.java)

        binding.todaySentenceViewModel = viewModel
        binding.lifecycleOwner = this
        (activity as MainActivity).increaseFragmentCount()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.ibRefresh.setOnClickListener {
            // TODO : refresh data
        }

        binding.etInputSentence.setOnClickListener {
            when (sSharedPreferences.getString(USER_POSITION, "MENTEE")) {
                "MENTEE" -> {
                    // TODO : 문장 작성 가능 개수 남았다면 자소서 입력하기 화면
                    val todaySentenceCountTest = 11
                    if (todaySentenceCountTest < MENTEE_SENTENCE_LIMIT) {
                        // TODO : 자소서 입력하기로 이동
                    }
                    else {
                        // 문장 작성 가능 개수 초과
                        // TODO : 스낵바 위치 bnv 위로 올리기
                        CustomSnackbar.make(binding.root, getString(R.string.snackbar_limit_over), Snackbar.LENGTH_LONG).show()
                    }
                }
                "MENTOR" -> {
                    // 멘토 문장 작성 불가
                    // TODO : 스낵바 위치 bnv 위로 올리기
                    CustomSnackbar.make(binding.root, getString(R.string.snackbar_mentee_only), Snackbar.LENGTH_LONG).show()
                }
            }
        }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}