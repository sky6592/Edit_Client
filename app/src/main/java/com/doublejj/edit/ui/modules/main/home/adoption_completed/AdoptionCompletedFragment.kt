package com.doublejj.edit.ui.modules.main.home.adoption_completed

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
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.adoption_completed.AdoptionCompletedService
import com.doublejj.edit.data.api.services.adoption_completed.AdoptionCompletedView
import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse
import com.doublejj.edit.data.models.sentence.SentenceData
import com.doublejj.edit.databinding.AdoptionCompletedFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.home.today_sentence.SentenceFragmentAdapter
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class AdoptionCompletedFragment : Fragment(), AdoptionCompletedView {
    private lateinit var binding: AdoptionCompletedFragmentBinding
    private lateinit var viewModel: AdoptionCompletedViewModel
    private lateinit var adapter: SentenceFragmentAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var page = 1
    private var hasNext = false
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.adoption_completed_fragment, container, false)
        viewModel = ViewModelProvider(this).get(AdoptionCompletedViewModel::class.java)

        binding.adoptionCompletedViewModel = viewModel
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

        return binding.root
    }

    fun getSentences() {
        // endless scrolling
        AdoptionCompletedService(this).tryGetAdoptionCompletedSentence(page = page)
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

    override fun onGetAdoptionCompletedSentenceSuccess(response: LookupSentenceResponse) {
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
    }

    override fun onGetAdoptionCompletedSentenceFailure(message: String) {
        CustomSnackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}