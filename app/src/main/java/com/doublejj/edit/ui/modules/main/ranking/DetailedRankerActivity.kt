package com.doublejj.edit.ui.modules.main.ranking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.ranking.RankerDetailService
import com.doublejj.edit.data.api.services.ranking.RankerDetailView
import com.doublejj.edit.data.models.ranking.RankerDetailResponse
import com.doublejj.edit.databinding.ActivityDetailedRankerBinding
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class DetailedRankerActivity : AppCompatActivity(), RankerDetailView {
    private lateinit var binding: ActivityDetailedRankerBinding

    var isMentor: Boolean = true
    var userId = 0L
    var rankId = 0L
    var nickName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_ranker)

        isMentor = intent.getStringExtra("role").toString() == "MENTOR"
        userId = intent.getLongExtra("userId", 0)
        rankId = intent.getLongExtra("rankId", 0)
        nickName = intent.getStringExtra("nickName").toString()

        binding.ibBack.setOnClickListener {
            finish()
        }

        if (isMentor) {
            RankerDetailService(this).tryGetRankerMentor(userId = userId, rankId = rankId)
        }
        else {
            RankerDetailService(this).tryGetRankerMentee(userId = userId, rankId = rankId)
        }

        // set visible by role
        if (isMentor) {
            binding.llDevMentor.visibility = View.VISIBLE
            binding.llDevMentee.visibility = View.GONE
        }
        else {
            binding.llDevMentor.visibility = View.GONE
            binding.llDevMentee.visibility = View.VISIBLE
        }

        // set click button
        binding.btnSubmit.setOnClickListener {
            var dialogTitle: Int
            var dialogContent: Int

            if (isMentor) {
                dialogTitle = R.string.tv_rank_dialog_title_mentor
                dialogContent = R.string.tv_rank_dialog_content_mentor
            }
            else {
                dialogTitle = R.string.tv_rank_dialog_title_mentee
                dialogContent = R.string.tv_rank_dialog_content_mentee
            }

            val dialog = CustomDialogFragment(
                dialogTitle,
                dialogContent,
                R.string.tv_dialog_check,
                null
            )
            dialog.show(supportFragmentManager, "CustomDialog")
        }
    }

    override fun onGetRankerDetailSuccess(response: RankerDetailResponse) {
        if (response.isSuccess) {
            // set text values
            var roleStr = ""
            if (isMentor) {
                roleStr = "??????"

                // ????????? ?????? ???
                binding.tvWriteText.text = getString(R.string.tv_rank_mentor_write)
                binding.tvWriteNumber.text = response.result.commentCount.toString()

                // ????????? ?????? ???
                binding.tvAdoptText.text = getString(R.string.tv_rank_mentor_adopt)
                binding.tvAdoptNumber.text = response.result.commentAdoptCount.toString()

                // ????????? ??????
                binding.btnSubmit.text = getString(R.string.btn_request_mentor)
            }
            else {
                roleStr = "??????"

                // ????????? ?????? ???
                binding.tvWriteText.text = getString(R.string.tv_rank_mentee_write)
                binding.tvWriteNumber.text = response.result.coverLetterCount.toString()

                // ????????? ?????? ???
                binding.tvAdoptText.text = getString(R.string.tv_rank_mentee_adopt)
                binding.tvAdoptNumber.text = response.result.coverLetterCompleteCount.toString()

                // ????????????
                binding.btnSubmit.text = getString(R.string.btn_praise_mentee)
            }

            // rank title
            binding.tvRankTitle.text = roleStr + " " + rankId + "???"

            // set profile image
            val profileRes = (applicationContext as ApplicationClass).getCharacterResId(response.result.colorName, response.result.emotionName)
            binding.ivCharacter.setImageResource(profileRes)

            // set user info
            binding.tvRankNickname.text = nickName
            binding.tvRankRole.text = roleStr + "???"
        }

    }

    override fun onGetRankerDetailFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}