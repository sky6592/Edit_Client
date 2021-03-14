package com.doublejj.edit.ui.modules.main.home.today_sentence

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.doublejj.edit.R
import com.doublejj.edit.databinding.TodaySentenceFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity

class TodaySentenceFragment : Fragment() {
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


        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}