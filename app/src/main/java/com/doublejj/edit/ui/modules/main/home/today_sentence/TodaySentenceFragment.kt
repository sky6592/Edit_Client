package com.doublejj.edit.ui.modules.main.home.today_sentence

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.doublejj.edit.data.models.sentence.SentenceData
import com.doublejj.edit.databinding.TodaySentenceFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.home.writing_sentence.WritingSentenceActivity
import com.doublejj.edit.ui.utils.dialog.CustomLoadingDialog
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class TodaySentenceFragment : Fragment(), TodaySentenceView, SentenceLimitView {
    private lateinit var binding: TodaySentenceFragmentBinding
    private lateinit var viewModel: TodaySentenceViewModel
    private lateinit var adapter: SentenceFragmentAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var page = 1
    private var hasNext = false
    private var isLoading = false

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
        initScrollListener()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.ibRefresh.setOnClickListener {
            // refresh data
            adapter.sentenceDataList.clear()
            page = 1
            hasNext = false
            isLoading = false
            getSentences()

            // ??? ??????????????? ?????? ???????????? ???????????? ?????? ????????? ???????????? ??????
            binding.rvSentence.removeAllViewsInLayout()
            binding.rvSentence.adapter = adapter
        }

        // ??? ????????? ???????????? ?????????
        val charColor = sSharedPreferences.getString(USER_COLOR, "purple").toString()
        val charEmotion = sSharedPreferences.getString(USER_EMOTION, "bigSmile").toString()
        val characterResId = (requireContext().applicationContext as ApplicationClass).getCharacterResId(charColor, charEmotion)
        binding.ivMyCharacter.setImageResource(characterResId)

        binding.etInputSentence.setOnClickListener {
            when (sSharedPreferences.getString(USER_POSITION, "MENTEE")) {
                "MENTEE" -> {
                    // ?????? ????????? ?????? ?????? ??????
                    SentenceLimitService(this).tryGetSentenceLimit()
                }
                "MENTOR" -> {
                    // ?????? ?????? ?????? ??????
                    // TODO : ????????? ?????? bnv ?????? ?????????
                    CustomSnackbar.make(binding.root, getString(R.string.snackbar_mentee_only), Snackbar.LENGTH_LONG).show()
                }
            }
        }

        return binding.root
    }

    // ????????????????????? ??? ????????? ???????????? ???????????? ??????
    fun getSentences() {
        // endless scrolling
        TodaySentenceService(this).tryGetTodaySentence(page = page)
    }

    fun setAdapter() {
        layoutManager = LinearLayoutManager(context)
        binding.rvSentence.layoutManager = layoutManager

        adapter = SentenceFragmentAdapter(requireContext(), mutableListOf(), requireActivity().supportFragmentManager)
        binding.rvSentence.adapter = adapter

        getSentences()
    }

    fun initScrollListener() {
        binding.rvSentence.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val itemTotalPosition = binding.rvSentence.adapter!!.itemCount - 1

                if (!isLoading) {
                    // ???????????? ???????????? ???????????? && ???????????? ??????????????????
                    if (!binding.rvSentence.canScrollVertically(1) && lastVisibleItemPosition == itemTotalPosition) {
                        if (hasNext && !isLoading) {
                            page += 1
                            isLoading = true
                            loadMore()
                        }
                    }
                }
            }
        })
    }

    fun loadMore() {
        // progress bar ??????
        Handler().post {
            adapter.setList(mutableListOf(null))
            adapter.notifyItemInserted(adapter.sentenceDataList.size - 1)
        }

        Handler().postDelayed({
            adapter.deleteLoading()
            adapter.notifyItemRemoved(adapter.sentenceDataList.size)

            // ????????? ??? ????????????
            getSentences()
        }, 500)

    }

    override fun onGetTodaySentenceSuccess(response: LookupSentenceResponse) {
        if (response.isSuccess) {
            val items : MutableList<SentenceData?> = response.result.coverLetters.toMutableList()
            hasNext = response.result.hasNext

            adapter.setList(items)
            adapter.notifyItemRangeInserted((page - 1) * 10, items.size)
        }
        else {
            CustomSnackbar.make(requireView(), response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        // ??? ????????? ?????? false??? ??????
        isLoading = false

        CustomLoadingDialog(requireContext()).dismiss()
    }

    override fun onGetTodaySentenceFailure(message: String) {
        CustomLoadingDialog(requireContext()).dismiss()
        CustomSnackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onGetSentenceLimitSuccess(response: ResultResponse) {
        if (response.isSuccess) {
            if (response.result < resources.getInteger(R.integer.length_limit_today_sentence)) {
                // ?????? ?????? ??????
                startActivity(Intent(activity, WritingSentenceActivity::class.java))
            }
            else {
                // ?????? ?????? ?????? ?????? ??????
                // TODO : ????????? ?????? bnv ?????? ?????????
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

        // ??? ??????????????? ?????? ???????????? ???????????? ?????? ????????? ???????????? ??????
        binding.rvSentence.removeAllViewsInLayout()
        binding.rvSentence.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }

}