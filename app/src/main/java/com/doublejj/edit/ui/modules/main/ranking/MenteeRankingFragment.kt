package com.doublejj.edit.ui.modules.main.ranking

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.ranking.UserRankingService
import com.doublejj.edit.data.api.services.ranking.UserRankingView
import com.doublejj.edit.data.models.ranking.RankingResponse
import com.doublejj.edit.data.models.ranking.UserRankingResult
import com.doublejj.edit.databinding.FragmentMenteeRankingBinding
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class MenteeRankingFragment : Fragment(), UserRankingView {
    private lateinit var binding: FragmentMenteeRankingBinding
    private val role = "MENTEE"
    private var rankerList: MutableList<UserRankingResult> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mentee_ranking, container, false)
        binding.lifecycleOwner = this

        UserRankingService(this).tryGetUserRanking(role)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvTop1.setOnClickListener {
            if (rankerList.size >= 1) {
                val sendIntent = Intent(context, DetailedRankerActivity::class.java)
                sendIntent.putExtra("role", role)
                sendIntent.putExtra("userId", rankerList.get(0).userId)
                sendIntent.putExtra("rankId", rankerList.get(0).rankId)
                sendIntent.putExtra("nickName", rankerList.get(0).nickName)
                startActivity(sendIntent)
            }
        }

        binding.cvTop2.setOnClickListener {
            if (rankerList.size >= 2) {
                val sendIntent = Intent(context, DetailedRankerActivity::class.java)
                sendIntent.putExtra("role", role)
                sendIntent.putExtra("userId", rankerList.get(1).userId)
                sendIntent.putExtra("rankId", rankerList.get(1).rankId)
                sendIntent.putExtra("nickName", rankerList.get(1).nickName)
                startActivity(sendIntent)
            }
        }

        binding.cvTop3.setOnClickListener {
            if (rankerList.size >= 3) {
                val sendIntent = Intent(context, DetailedRankerActivity::class.java)
                sendIntent.putExtra("role", role)
                sendIntent.putExtra("userId", rankerList.get(2).userId)
                sendIntent.putExtra("rankId", rankerList.get(2).rankId)
                sendIntent.putExtra("nickName", rankerList.get(2).nickName)
                startActivity(sendIntent)
            }
        }
    }


    override fun onGetUserRankingSuccess(response: RankingResponse) {
        if (response.isSuccess) {
            rankerList.clear()
            rankerList.addAll(response.result.getUserRankResList)

            if (response.result.getUserRankResList.size == 0) {
                return // do nothing
            }

            if (response.result.getUserRankResList.size >= 4) {
                // set values into rv
                val moreRankerList = response.result.getUserRankResList.subList(3, response.result.getUserRankResList.size)

                binding.rvRank.layoutManager = LinearLayoutManager(requireContext())
                binding.rvRank.adapter = MoreRankerAdapter(requireContext(), moreRankerList, role)
            }

            if (response.result.getUserRankResList.size >= 3) {
                val userRank = response.result.getUserRankResList[2]
                // set character image
                val characterResId = (requireContext().applicationContext as ApplicationClass).getCharacterResId(userRank.colorName, userRank.emotionName)
                binding.ivProfileRank3.setImageResource(characterResId)
                // set nickname text
                binding.tvNicknameRank3.text = userRank.nickName
                // set job position text
                binding.tvPositionRank3.text = userRank.jobName
            }

            if (response.result.getUserRankResList.size >= 2) {
                val userRank = response.result.getUserRankResList[1]
                // set character image
                val characterResId = (requireContext().applicationContext as ApplicationClass).getCharacterResId(userRank.colorName, userRank.emotionName)
                binding.ivProfileRank2.setImageResource(characterResId)
                // set nickname text
                binding.tvNicknameRank2.text = userRank.nickName
                // set job position text
                binding.tvPositionRank2.text = userRank.jobName
            }

            if (response.result.getUserRankResList.size >= 1) {
                val userRank = response.result.getUserRankResList[0]
                // set character image
                val characterResId = (requireContext().applicationContext as ApplicationClass).getCharacterResId(userRank.colorName, userRank.emotionName)
                binding.ivProfileRank1.setImageResource(characterResId)
                // set nickname text
                binding.tvNicknameRank1.text = userRank.nickName
                // set job position text
                binding.tvPositionRank1.text = userRank.jobName
            }
        } else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onGetUserRankingFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}