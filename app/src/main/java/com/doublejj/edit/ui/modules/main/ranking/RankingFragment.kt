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
import com.doublejj.edit.ui.modules.main.ranking.clickfragment.ClickActivity

//import com.doublejj.edit.ui.modules.main.ranking.clickfragment.ClickActivity

class RankingFragment : Fragment() {
    private lateinit var binding: RankingFragmentBinding
    private lateinit var viewModel: RankingViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.ranking_fragment, container, false)
        viewModel = ViewModelProvider(this).get(RankingViewModel::class.java)
        binding.rankingViewModel = viewModel
        binding.lifecycleOwner = this
        /*val intent = Intent(requireActivity(), ClickActivity::class.java)
        binding.cvOneRank.setOnClickListener {
//            startActivity(intent)
        }
        binding.cvTwoRank.setOnClickListener {
//            startActivity(intent)
        }
        binding.cvThreeRank.setOnClickListener {
//            startActivity(intent)
        }*/
        return binding.root
    }
}