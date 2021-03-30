package com.doublejj.edit.ui.modules.main.home

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.doublejj.edit.ApplicationClass.Companion.MENTOR_AUTH_CONFIRM
import com.doublejj.edit.ApplicationClass.Companion.USER_POSITION
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.main_oneshot.MainOneshotService
import com.doublejj.edit.data.api.services.main_oneshot.MainOneshotView
import com.doublejj.edit.data.api.services.writing_sentence.SentenceLimitService
import com.doublejj.edit.data.api.services.writing_sentence.SentenceLimitView
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.main_oneshot.MainOneshotResponse
import com.doublejj.edit.data.models.main_oneshot.MainSentenceData
import com.doublejj.edit.data.models.main_oneshot.MainSentences
import com.doublejj.edit.databinding.HomeFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.home.adoption_completed.AdoptionCompletedFragment
import com.doublejj.edit.ui.modules.main.home.best_sympathy.BestSympathyFragment
import com.doublejj.edit.ui.modules.main.home.today_sentence.TodaySentenceFragment
import com.doublejj.edit.ui.modules.main.home.waiting_for_comment.WaitingForCommentFragment
import com.doublejj.edit.ui.modules.main.home.writing_sentence.WritingSentenceActivity
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(), MainOneshotView, SentenceLimitView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<HomeFragmentBinding>(inflater, R.layout.home_fragment, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this

        // MainActivty의 fragment 개수 관리 변수 증가
        (activity as MainActivity).increaseFragmentCount()

        /** toolbar buttons **/
        binding.tvLogo.setOnClickListener {
            // TODO : scroll to top
            Log.d(TAG, "logo clicked")
        }
        binding.ibRefresh.setOnClickListener {
            MainOneshotService(this).tryGetMainSentences()
        }

        // fragment안에서 새로운 fragment 전환
        binding.tvTitleTodaySentence.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fl_home, TodaySentenceFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }
        binding.tvTitleWaitedComment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fl_home, WaitingForCommentFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }
        binding.tvTitleCompletedAdoption.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fl_home, AdoptionCompletedFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }
        binding.tvTitleSympathyComment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fl_home, BestSympathyFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }

        when (sSharedPreferences.getString(USER_POSITION, "MENTEE")) {
            "MENTEE" -> {
                binding.fabMentee.visibility = View.VISIBLE
            }
            "MENTOR" -> {
                binding.fabMentee.visibility = View.GONE
                // TODO : 첫 home 화면에서 아직 멘토 인증 안했다면 스낵바 띄우기 & 지우기
                if (!sSharedPreferences.getBoolean(MENTOR_AUTH_CONFIRM, false)) {
                    CustomSnackbar.make(binding.root, getString(R.string.snackbar_description_mentor), Snackbar.LENGTH_INDEFINITE).show()
                }
            }
        }

        binding.fabMentee.setOnClickListener {
            // 오늘 작성한 문장 개수 조회
            SentenceLimitService(this).tryGetSentenceLimit()
        }


        /** MainOneshot API **/
        MainOneshotService(this).tryGetMainSentences()
        
        return binding.root
    }

    fun setTodaySentence(todaySentences: MutableList<MainSentenceData>) {
        when (todaySentences.size) {
            0 -> {
                // 오늘의 문장 0개일 때 카드 처리
                binding.cvHomeTodaySentenceZero0.visibility = View.VISIBLE
                binding.cvHomeTodaySentenceZero1.visibility = View.VISIBLE
                binding.cvHomeTodaySentenceZero2.visibility = View.VISIBLE
                binding.cvHomeTodaySentenceZero3.visibility = View.VISIBLE
                binding.cvHomeTodaySentenceZero4.visibility = View.VISIBLE
                binding.cvHomeTodaySentence0.visibility = View.GONE
                binding.cvHomeTodaySentence1.visibility = View.GONE
                binding.cvHomeTodaySentence2.visibility = View.GONE
                binding.cvHomeTodaySentence3.visibility = View.GONE
                binding.cvHomeTodaySentence4.visibility = View.GONE
            }
            else -> {
                binding.cvHomeTodaySentenceZero0.visibility = View.GONE
                binding.cvHomeTodaySentenceZero1.visibility = View.GONE
                binding.cvHomeTodaySentenceZero2.visibility = View.GONE
                binding.cvHomeTodaySentenceZero3.visibility = View.GONE
                binding.cvHomeTodaySentenceZero4.visibility = View.GONE
                binding.cvHomeTodaySentence0.visibility = View.GONE
                binding.cvHomeTodaySentence1.visibility = View.GONE
                binding.cvHomeTodaySentence2.visibility = View.GONE
                binding.cvHomeTodaySentence3.visibility = View.GONE
                binding.cvHomeTodaySentence4.visibility = View.GONE

                if (todaySentences.size >= 1) {
                    binding.cvHomeTodaySentence0.visibility = View.VISIBLE
                    binding.tvTodaySentenceWriter0.text = todaySentences.get(0).nickName
                    binding.tvTodaySentenceContent0.text = todaySentences.get(0).coverLetterContent
                }
                if (todaySentences.size >= 2) {
                    binding.cvHomeTodaySentence1.visibility = View.VISIBLE
                    binding.tvTodaySentenceWriter1.text = todaySentences.get(1).nickName
                    binding.tvTodaySentenceContent1.text = todaySentences.get(1).coverLetterContent
                }
                if (todaySentences.size >= 3) {
                    binding.cvHomeTodaySentence2.visibility = View.VISIBLE
                    binding.tvTodaySentenceWriter2.text = todaySentences.get(2).nickName
                    binding.tvTodaySentenceContent2.text = todaySentences.get(2).coverLetterContent
                }
                if (todaySentences.size >= 4) {
                    binding.cvHomeTodaySentence3.visibility = View.VISIBLE
                    binding.tvTodaySentenceWriter3.text = todaySentences.get(3).nickName
                    binding.tvTodaySentenceContent3.text = todaySentences.get(3).coverLetterContent
                }
                if (todaySentences.size >= 5) {
                    binding.cvHomeTodaySentence4.visibility = View.VISIBLE
                    binding.tvTodaySentenceWriter4.text = todaySentences.get(4).nickName
                    binding.tvTodaySentenceContent4.text = todaySentences.get(4).coverLetterContent
                }
            }
        }
    }

    fun setSentenceBox(sentences: MainSentences) {
        if (sentences.waitingForCommentCoverLetters.size != 0) {
            for (idx in 0 until sentences.waitingForCommentCoverLetters.size) {
                binding.tvSentenceWriter0.text = sentences.waitingForCommentCoverLetters.get(idx).nickName
                binding.tvSentenceContent0.text = sentences.waitingForCommentCoverLetters.get(idx).coverLetterContent
            }
        }
        if (sentences.adoptedCoverLetters.size != 0) {
            for (idx in 0 until sentences.adoptedCoverLetters.size) {
                binding.tvSentenceWriter2.text = sentences.adoptedCoverLetters.get(idx).nickName
                binding.tvSentenceContent2.text = sentences.adoptedCoverLetters.get(idx).coverLetterContent
            }
        }
        if (sentences.sympathiesCoverLetters.size != 0) {
            for (idx in 0 until sentences.sympathiesCoverLetters.size) {
                binding.tvSentenceWriter4.text = sentences.sympathiesCoverLetters.get(idx).nickName
                binding.tvSentenceContent4.text = sentences.sympathiesCoverLetters.get(idx).coverLetterContent
            }
        }
    }

    override fun onResume() {
        super.onResume()
/*
        val isMentor = sSharedPreferences.getString(USER_POSITION, "MENTEE")
        val isMentorAuth = sSharedPreferences.getBoolean(MENTOR_AUTH_CONFIRM, false)
        Log.d("homeFragment", "isMentor: $isMentor, isMentorAuth: $isMentorAuth")

        when (sSharedPreferences.getString(USER_POSITION, "MENTEE")) {
            "MENTEE" -> {
                binding.fabMentee.visibility = View.VISIBLE
            }
            "MENTOR" -> {
                binding.fabMentee.visibility = View.GONE
                // TODO : 첫 home 화면에서 아직 멘토 인증 안했다면 스낵바 띄우기 & 지우기
                if (!sSharedPreferences.getBoolean(MENTOR_AUTH_CONFIRM, false)) {
//                    CustomSnackbar.make(binding.root, getString(R.string.snackbar_description_mentor), Snackbar.LENGTH_INDEFINITE).show()
                }
            }
        }*/

        /*// TODO : 멘티라면 fab 버튼 활성화, 스낵바 비활성화
        if (isMentor == "MENTEE") {
            binding.fabMentee.visibility = View.VISIBLE
            binding.fabMentee.isEnabled = true
            binding.
        }
        // TODO : 멘토라면 fab 버튼 비활성화, 스낵바 비활성화
        else {
            binding.fabMentee.visibility = View.GONE
            binding.fabMentee.isEnabled = false
            // TODO : 멘토인데 인증받은 멘토라면 스낵바 활성화
            if ()
        }*/

//        Log.d("fab", "isMentor: $isMentor, isMentorAuth: $isMentorAuth")
    }

    override fun onGetMainSentencesSuccess(response: MainOneshotResponse) {
        if (response.isSuccess) {
            setTodaySentence(response.result.todayCoverLetters)
            setSentenceBox(response.result)
        }
    }

    override fun onGetMainSentencesFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onGetSentenceLimitSuccess(response: ResultResponse) {
        if (response.isSuccess) {
            if (response.result < resources.getInteger(R.integer.length_limit_today_sentence)) {
                // 문장 작성 가능
                // TODO : 스낵바 위치 bnv 위로 올리기
                startActivity(Intent(activity, WritingSentenceActivity::class.java))
            }
            else {
                // 문장 작성 가능 개수 초과
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
    
    // TODO : onResume()일 때 sSharedPreferences 업데이트

    override fun onDetach() {
        super.onDetach()
        // MainActivty의 fragment 개수 관리 변수 감소
        (activity as MainActivity).decreaseFragmentCount()
    }
}