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

        /** set adapter **/
        setAdapter()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.ibRefresh.setOnClickListener {
            // TODO : refresh data
        }

        return binding.root
    }

    fun setAdapter() {
        // TODO : 페이징 적용하기
        BestSympathyService(this).tryGetBestSympathySentence(page = 0)
        binding.rvSentence.layoutManager = LinearLayoutManager(context)
    }

    override fun onGetBestSympathySentenceSuccess(response: LookupSentenceResponse) {
        if (response.isSuccess) {
            binding.rvSentence.adapter = SentenceAdapter(requireContext(), response.result, requireActivity().supportFragmentManager)
        }
    }

    override fun onGetBestSympathySentenceFailure(message: String) {
        CustomSnackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}