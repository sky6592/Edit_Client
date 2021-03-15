package com.doublejj.edit.ui.modules.main.home.adoption_completed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.doublejj.edit.R
import com.doublejj.edit.databinding.AdoptionCompletedFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity

class AdoptionCompletedFragment : Fragment() {
    private lateinit var binding: AdoptionCompletedFragmentBinding
    private lateinit var viewModel: AdoptionCompletedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.best_sympathy_fragment, container, false)
        viewModel = ViewModelProvider(this).get(AdoptionCompletedViewModel::class.java)

        binding.adoptionCompletedViewModel = viewModel
        binding.lifecycleOwner = this
        (activity as MainActivity).increaseFragmentCount()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.ibRefresh.setOnClickListener {
            // TODO : refresh data
        }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}