package com.doublejj.edit.ui.modules.main.ranking

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class RankingPageAdapter(
    var fragmentList: ArrayList<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    public fun refreshFragment() {
        fragmentList.clear()

        val newFragmentList = arrayListOf<Fragment>(
            MentorRankingFragment(),
            MenteeRankingFragment()
        )
    }
}