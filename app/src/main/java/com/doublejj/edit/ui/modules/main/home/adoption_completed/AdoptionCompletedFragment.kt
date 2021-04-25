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

        /** get sentences from server **/
        getSentences()

        /** set adapter **/
        setAdapter()

        initScrollListener()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.ibRefresh.setOnClickListener {
            // TODO : refresh data
            onResume()
        }

        return binding.root
    }

    fun getSentences() {
        // TODO : 무한스크롤 처리
        AdoptionCompletedService(this).tryGetAdoptionCompletedSentence(page = page)
    }

    fun setAdapter() {
        adapter = SentenceFragmentAdapter(requireContext(), mutableListOf(), requireActivity().supportFragmentManager)
        binding.rvSentence.adapter = adapter
    }

    fun initScrollListener() {
        binding.rvSentence.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = binding.rvSentence.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = binding.rvSentence.adapter!!.itemCount - 1

                if (!isLoading) {
                    // 스크롤이 최하단에 도달하고 && 리스트의 마지막이라면
//                    if (layoutManager != null && lastVisibleItemPosition == itemTotalCount) {
                    if (!binding.rvSentence.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                        if (hasNext) {
                            page += 1
                            loadMore()
                            isLoading = true
                        }
                    }
                }
            }
        })
    }

    fun loadMore() {
        // progress bar 추가
        adapter.sentenceDataList.add(null)
        adapter.notifyItemInserted(adapter.sentenceDataList.lastIndex)

        val handler: Handler = Handler()
        handler.postDelayed({
            adapter.deleteLoading()
            adapter.notifyItemRemoved(adapter.sentenceDataList.lastIndex)

            // 아이템 더 가져오기
            getSentences()
            isLoading = false
        }, 1000)
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
    }

    override fun onGetAdoptionCompletedSentenceFailure(message: String) {
        CustomSnackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

        // 뷰 레이아웃을 모두 지워주고 어댑터를 다시 붙여서 새로고침 효과
        binding.rvSentence.removeAllViewsInLayout()
        binding.rvSentence.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}