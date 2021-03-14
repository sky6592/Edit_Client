package com.doublejj.edit.ui.modules.main.home.best_sympathy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.doublejj.edit.R
import com.doublejj.edit.databinding.BestSympathyFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity

class BestSympathyFragment : Fragment() {
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

        // TODO : toolbar tv_title 값 바꾸기

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}