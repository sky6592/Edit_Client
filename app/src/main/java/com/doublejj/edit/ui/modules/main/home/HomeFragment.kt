package com.doublejj.edit.ui.modules.main.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.doublejj.edit.R
import com.doublejj.edit.databinding.HomeFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.home.today_sentence.TodaySentenceFragment

class HomeFragment : Fragment() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<HomeFragmentBinding>(inflater, R.layout.home_fragment, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this

        // MainActivty의 fragment 개수 관리 변수 증가
        (activity as MainActivity).increaseFragmentCount()

        // fragment안에서 새로운 fragment 전환
        binding.ibExpandTodaySentence.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fl_home, TodaySentenceFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        // MainActivty의 fragment 개수 관리 변수 감소
        (activity as MainActivity).decreaseFragmentCount()
    }
}