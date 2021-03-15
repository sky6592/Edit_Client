package com.doublejj.edit.ui.modules.main.home.open_comment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.ApplicationClass.Companion.USER_POSITION
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.data.models.comment.CommentData
import com.doublejj.edit.databinding.OpenCommentFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class OpenCommentFragment : Fragment() {
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
        when (sSharedPreferences.getString(USER_POSITION, "MENTEE")) {
            "MENTEE" -> {
                binding.fabMentor.visibility = View.GONE
            }
            "MENTOR" -> {
                binding.fabMentor.visibility = View.VISIBLE
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
                    // TODO : 해당 카드 신고 처리
                    CustomSnackbar.make(it, getString(R.string.snackbar_report), Snackbar.LENGTH_LONG).show()
                }
                override fun onNegativeClick() {
                }
            })
            dialog.show(requireActivity().supportFragmentManager, "CustomDialog")
        }

        return binding.root
    }

    fun placeSentenceFromBundle() {
        binding.ivCharacter.setImageResource(requireArguments().getInt("ivCharacter"))
        binding.tvSentenceWriter.text = requireArguments().getString("tvSentenceWriter")
        binding.tvOccupationType.text = requireArguments().getString("tvOccupationType")
        binding.tvSelfWritingType.text = requireArguments().getString("tvSelfWritingType")
        binding.tvSentenceContent.text = requireArguments().getString("tvSentenceContent")
    }

    fun getComments() {
        var commentDataList = mutableListOf<CommentData>()

        // TODO : 테스트 코드 지우기
        val responseResultSizeTest = 3
//        if (response.result.size() >= 1) {
        if (responseResultSizeTest >= 1) {
            commentDataList.add(
                CommentData(
                    "purple/suprise",
                    0L,
                    "제인",
                    "개발",
                    "보통",
                    "좋음",
                    "부족",
                    "좋음",
                    "111부사는 사용하지 마시고 기술 용어를 사용해서 표현하면 내용이 명확해질 것 같습니다.",
                    "YES"
                )
            )
            commentDataList.add(
                CommentData(
                    "lightBlue/happy",
                    1L,
                    "콜트",
                    "개발",
                    "보통",
                    "좋음",
                    "부족",
                    "좋음",
                    "222부사는 사용하지 마시고 기술 용어를 사용해서 표현하면 내용이 명확해질 것 같습니다.",
                    "YES"
                )
            )
            commentDataList.add(
                CommentData(
                    "blue/wink",
                    2L,
                    "디비",
                    "개발",
                    "보통",
                    "좋음",
                    "부족",
                    "좋음",
                    "333부사는 사용하지 마시고 기술 용어를 사용해서 표현하면 내용이 명확해질 것 같습니다.",
                    "YES"
                )
            )
            binding.llZeroComment.visibility = View.GONE
            binding.rvComment.visibility = View.VISIBLE
            setAdapter(commentDataList)
        }
        else {
            binding.llZeroComment.visibility = View.VISIBLE
            binding.rvComment.visibility = View.GONE
        }
    }

    fun setAdapter(commentDataList: MutableList<CommentData>) {
        binding.rvComment.layoutManager = LinearLayoutManager(context)
        binding.rvComment.adapter = OpenCommentAdapter(requireContext(), commentDataList, requireActivity().supportFragmentManager)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}