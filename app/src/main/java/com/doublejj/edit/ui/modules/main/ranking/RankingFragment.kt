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
import com.google.android.material.tabs.TabLayoutMediator


class RankingFragment : Fragment() {
    private lateinit var binding: RankingFragmentBinding
    private lateinit var viewModel: RankingViewModel

    private var fragmentList: ArrayList<Fragment> = arrayListOf(
        MentorRankingFragment(),
        MenteeRankingFragment()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.ranking_fragment, container, false)
        viewModel = ViewModelProvider(this).get(RankingViewModel::class.java)
        binding.rankingViewModel = viewModel
        binding.lifecycleOwner = this

        binding.vpRanking.isSaveEnabled = false
        binding.vpRanking.adapter = RankingPageAdapter(fragmentList, childFragmentManager, lifecycle)

        TabLayoutMediator(binding.tlRanking, binding.vpRanking) {tab, position ->
            tab.text = resources.getStringArray(R.array.array_ranking_tab)[position]
        }.attach()

        /** toolbar buttons **/
//        binding.tvLogo.setOnClickListener {
//            // scroll to top
//            binding.nsvHome.smoothScrollTo(0, 0)
//        }
        binding.ibRefresh.setOnClickListener {
            // refresh data
            binding.vpRanking.adapter = RankingPageAdapter(fragmentList, childFragmentManager, lifecycle)
        }

        return binding.root
    }
}