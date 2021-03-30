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
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.ApplicationClass.Companion.USER_COLOR
import com.doublejj.edit.ApplicationClass.Companion.USER_EMOTION
import com.doublejj.edit.ApplicationClass.Companion.USER_POSITION
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.today_sentence.TodaySentenceService
import com.doublejj.edit.data.api.services.today_sentence.TodaySentenceView
import com.doublejj.edit.data.api.services.writing_sentence.SentenceLimitService
import com.doublejj.edit.data.api.services.writing_sentence.SentenceLimitView
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse
import com.doublejj.edit.databinding.TodaySentenceFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.home.writing_sentence.WritingSentenceActivity
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class TodaySentenceFragment : Fragment(), TodaySentenceView, SentenceLimitView {
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

        /** get sentences from server **/
        getSentences()

        /** set adapter **/
        setAdapter()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.ibRefresh.setOnClickListener {
            // refresh data
            onResume()
        }
        
        // 내 프로필 캐릭터로 바꾸기
        val charColor = sSharedPreferences.getString(USER_COLOR, "purple").toString()
        val charEmotion = sSharedPreferences.getString(USER_EMOTION, "bigSmile").toString()
        val characterResId = (requireContext().applicationContext as ApplicationClass).getCharacterResId(charColor, charEmotion)
        binding.ivMyCharacter.setImageResource(characterResId)

        binding.etInputSentence.setOnClickListener {
            when (sSharedPreferences.getString(USER_POSITION, "MENTEE")) {
                "MENTEE" -> {
                    // 오늘 작성한 문장 개수 조회
                    SentenceLimitService(this).tryGetSentenceLimit()
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

    fun getSentences() {
        // TODO : 무한스크롤 처리
        TodaySentenceService(this).tryGetTodaySentence(page = 1)
    }

    fun setAdapter() {
        binding.rvSentence.layoutManager = LinearLayoutManager(context)
    }

    override fun onGetTodaySentenceSuccess(response: LookupSentenceResponse) {
        if (response.isSuccess) {
            binding.rvSentence.adapter = SentenceAdapter(requireContext(), response.result, requireActivity().supportFragmentManager)
        }
    }

    override fun onGetTodaySentenceFailure(message: String) {
        CustomSnackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onGetSentenceLimitSuccess(response: ResultResponse) {
        if (response.isSuccess) {
            if (response.result < resources.getInteger(R.integer.length_limit_today_sentence)) {
                // 문장 작성 가능
                startActivity(Intent(activity, WritingSentenceActivity::class.java))
            }
            else {
                // 문장 작성 가능 개수 초과
                // TODO : 스낵바 위치 bnv 위로 올리기
                CustomSnackbar.make(binding.root, getString(R.string.snackbar_limit_over),Snackbar.LENGTH_LONG).show()
            }
        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onGetSentenceLimitFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        getSentences()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}