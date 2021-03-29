package com.doublejj.edit.ui.modules.main.home.open_comment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.ApplicationClass.Companion.MENTOR_AUTH_CONFIRM
import com.doublejj.edit.ApplicationClass.Companion.USER_POSITION
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.lookup_comments_of_sentence.CommentsOfSentenceService
import com.doublejj.edit.data.api.services.lookup_comments_of_sentence.CommentsOfSentenceView
import com.doublejj.edit.data.models.comment.CommentData
import com.doublejj.edit.data.models.lookup_comments_of_sentence.LookupCommentResponse
import com.doublejj.edit.databinding.OpenCommentFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.home.writing_comment.WritingCommentActivity
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class OpenCommentFragment : Fragment(), CommentsOfSentenceView {
    private lateinit var binding: OpenCommentFragmentBinding
    private lateinit var viewModel: OpenCommentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.open_comment_fragment, container, false)
        viewModel = ViewModelProvider(this).get(OpenCommentViewModel::class.java)

        binding.openCommentViewModel = viewModel
        binding.lifecycleOwner = this
        (activity as MainActivity).increaseFragmentCount()

        /** get comments from server **/
        getComments()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.ibRefresh.setOnClickListener {
            // TODO : refresh data
        }

        /** floating button **/
        val mentorAuth = sSharedPreferences.getBoolean(MENTOR_AUTH_CONFIRM, false)
        when (sSharedPreferences.getString(USER_POSITION, "MENTEE")) {
            "MENTEE" -> {
                binding.fabMentor.visibility = View.GONE
                binding.fabMentor.isEnabled = false
            }
            "MENTOR" -> {
                if (mentorAuth) {
                    binding.fabMentor.visibility = View.VISIBLE
                    binding.fabMentor.isEnabled = true
                }
                else {
                    binding.fabMentor.visibility = View.GONE
                    binding.fabMentor.isEnabled = false
                }
            }
        }

        /** mentee's sentence **/
        placeSentenceFromBundle()
        binding.ibMenu.setOnClickListener {
            val dialog = CustomDialogFragment(
                R.string.tv_dialog_sentence_report_title,
                R.string.tv_dialog_sentence_report_content,
                R.string.tv_dialog_report,
                R.string.tv_dialog_dismiss
            )
            dialog.setDialogClickListener(object : CustomDialogClickListener {
                override fun onPositiveClick() {
                    // 해당 카드 신고 처리
                    CustomSnackbar.make(it, getString(R.string.snackbar_report), Snackbar.LENGTH_LONG).show()
                }
                override fun onNegativeClick() {
                }
            })
            dialog.show(requireActivity().supportFragmentManager, "CustomDialog")
        }

        binding.fabMentor.setOnClickListener {
            if (binding.fabMentor.isEnabled) {
                val sendIntent = Intent(activity, WritingCommentActivity::class.java)
                sendIntent.putExtra("ivCharacter", requireArguments().getInt("ivCharacter"))
                sendIntent.putExtra("coverLetterId", requireArguments().getLong("coverLetterId"))
                sendIntent.putExtra("tvSentenceWriter", requireArguments().getString("tvSentenceWriter"))
                sendIntent.putExtra("tvOccupationType", requireArguments().getString("tvOccupationType"))
                sendIntent.putExtra("tvSelfWritingType", requireArguments().getString("tvSelfWritingType"))
                sendIntent.putExtra("tvSentenceContent", requireArguments().getString("tvSentenceContent"))
                sendIntent.putExtra("isMine", requireArguments().getBoolean("isMine"))
                startActivity(sendIntent)
            }
        }

        return binding.root
    }

    fun placeSentenceFromBundle() {
        binding.ivCharacter.setImageResource(requireArguments().getInt("ivCharacter"))
        binding.tvSentenceWriter.text = requireArguments().getString("tvSentenceWriter")
        binding.tvOccupationType.text = requireArguments().getString("tvOccupationType")
        binding.tvSelfWritingType.text = requireArguments().getString("tvSelfWritingType")
        binding.tvSentenceContent.text = requireArguments().getString("tvSentenceContent")
        val isMine = requireArguments().getBoolean("isMine")
        if (isMine) binding.ibMenu.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_delete))
    }

    fun getComments() {
        // TODO : 무한스크롤 처리
        val sentenceId = requireArguments().getLong("coverLetterId")

        CommentsOfSentenceService(this).tryGetCommentsOfSentence(
            sentenceId = sentenceId,
            page = 1
        )
    }

    fun setAdapter(commentDataList: MutableList<CommentData>) {
        binding.rvComment.layoutManager = LinearLayoutManager(context)
        binding.rvComment.adapter = OpenCommentAdapter(requireContext(), commentDataList, requireActivity().supportFragmentManager)
    }

    override fun onGetCommentsOfSentenceSuccess(response: LookupCommentResponse) {
        if (response.isSuccess) {
            val commentDataList = response.result.commentInfos
            if (commentDataList.size > 0) {
                binding.llZeroComment.visibility = View.INVISIBLE
                binding.rvComment.visibility = View.VISIBLE
                setAdapter(commentDataList)
            }
            else {
                binding.llZeroComment.visibility = View.VISIBLE
                binding.rvComment.visibility = View.INVISIBLE
            }
        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onGetCommentsOfSentenceFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}