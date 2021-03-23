package com.doublejj.edit.ui.modules.main.ranking

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.RankingFragmentBinding

class RankingFragment : Fragment() {
    private lateinit var binding: RankingFragmentBinding
    private lateinit var viewModel: RankingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.ranking_fragment, container, false)
        viewModel = ViewModelProvider(this).get(RankingViewModel::class.java)

        binding.rankingViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

}