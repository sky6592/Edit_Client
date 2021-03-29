package com.doublejj.edit.ui.modules.main.home.best_sympathy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.best_sympathy.BestSympathyService
import com.doublejj.edit.data.api.services.best_sympathy.BestSympathyView
import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse
import com.doublejj.edit.databinding.BestSympathyFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.home.today_sentence.SentenceAdapter
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class BestSympathyFragment : Fragment(), BestSympathyView {
    private lateinit var binding: BestSympathyFragmentBinding
    private lateinit var viewModel: BestSympathyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.best_sympathy_fragment, container, false)
        viewModel = ViewModelProvider(this).get(BestSympathyViewModel::class.java)

        binding.bestSympathyViewModel = viewModel
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

        return binding.root
    }

    fun getSentences() {
        // TODO : 무한스크롤 처리
        BestSympathyService(this).tryGetBestSympathySentence(page = 1)
    }

    fun setAdapter() {
        binding.rvSentence.layoutManager = LinearLayoutManager(context)
    }

    override fun onGetBestSympathySentenceSuccess(response: LookupSentenceResponse) {
        if (response.isSuccess) {
            binding.rvSentence.adapter = SentenceAdapter(requireContext(), response.result, requireActivity().supportFragmentManager)
        }
    }

    override fun onGetBestSympathySentenceFailure(message: String) {
        CustomSnackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
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