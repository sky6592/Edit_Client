package com.doublejj.edit.ui.modules.main.home.today_sentence

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.ApplicationClass.Companion.USER_POSITION
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.todaysentence.TodaySentenceService
import com.doublejj.edit.data.api.services.todaysentence.TodaySentenceView
import com.doublejj.edit.data.models.todaysentence.TodaySentenceResponse
import com.doublejj.edit.databinding.TodaySentenceFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.home.writing_sentence.WritingSentenceActivity
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class TodaySentenceFragment : Fragment(), TodaySentenceView {
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

        /** set adapter **/
        setAdapter()

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
                    val todaySentenceCountTest = 4 // TODO : 테스트 임시 코드 지우기
                    if (todaySentenceCountTest < resources.getInteger(R.integer.length_limit_mentee_sentence)) {
                        startActivity(Intent(activity, WritingSentenceActivity::class.java))
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

    fun setAdapter() {
        // TODO : 페이징 적용하기
        TodaySentenceService(this).tryGetTodaySentence(page = 0)
        binding.rvSentence.layoutManager = LinearLayoutManager(context)
    }

    override fun onGetTodaySentenceSuccess(response: TodaySentenceResponse) {
        if (response.isSuccess) {

            binding.rvSentence.adapter = SentenceAdapter(requireContext(), response.result, requireActivity().supportFragmentManager)
        }
    }

    override fun onGetTodaySentenceFailure(message: String) {
        CustomSnackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}