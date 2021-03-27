package com.doublejj.edit.ui.modules.main.home.adoption_completed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.adoption_completed.AdoptionCompletedService
import com.doublejj.edit.data.api.services.adoption_completed.AdoptionCompletedView
import com.doublejj.edit.data.models.lookup_sentences_home.LookupSentenceResponse
import com.doublejj.edit.databinding.AdoptionCompletedFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.home.today_sentence.SentenceAdapter
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class AdoptionCompletedFragment : Fragment(), AdoptionCompletedView {
    private lateinit var binding: AdoptionCompletedFragmentBinding
    private lateinit var viewModel: AdoptionCompletedViewModel

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
        AdoptionCompletedService(this).tryGetAdoptionCompletedSentence(page = 1)
        binding.rvSentence.layoutManager = LinearLayoutManager(context)
    }

    override fun onGetAdoptionCompletedSentenceSuccess(response: LookupSentenceResponse) {
        if (response.isSuccess) {
            binding.rvSentence.adapter = SentenceAdapter(requireContext(), response.result, requireActivity().supportFragmentManager)
        }
    }

    override fun onGetAdoptionCompletedSentenceFailure(message: String) {
        CustomSnackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}