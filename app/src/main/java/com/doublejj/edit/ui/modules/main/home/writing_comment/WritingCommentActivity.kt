package com.doublejj.edit.ui.modules.main.home.writing_comment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.comment.WritingCommentService
import com.doublejj.edit.data.api.services.comment.WritingCommentView
import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.writing_comment.WritingCommentRequest
import com.doublejj.edit.databinding.ActivityWritingCommentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.home.open_comment.OpenCommentFragment
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.dialog.CustomLoadingDialog
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class WritingCommentActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityWritingCommentBinding
    private var writingRequest = WritingCommentRequest("", "", "", "", "", -1L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_writing_comment)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        /** sentence **/
        writingRequest.coverLetterId = intent.getLongExtra("coverLetterId", -1L)
        binding.ivCharacter.setImageDrawable(ContextCompat.getDrawable(applicationContext, intent.getIntExtra("ivCharacter", R.drawable.selector_char_purple_active_0)))
        binding.tvSentenceWriter.text = intent.getStringExtra("tvSentenceWriter").toString()
        binding.tvOccupationType.text = intent.getStringExtra("tvOccupationType").toString()
        binding.tvSelfWritingType.text = intent.getStringExtra("tvSelfWritingType").toString()
        binding.tvSentenceContent.text = intent.getStringExtra("tvSentenceContent").toString()
        
        /** select evaluation **/
        // 문장에 대한 전체 평가 선택 값
        binding.rgEval0.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_leak_0 -> writingRequest.sentenceEvaluation = getString(R.string.tv_open_comment_evaluation_leak)
                R.id.rb_normal_0 -> writingRequest.sentenceEvaluation = getString(R.string.tv_open_comment_evaluation_normal)
                R.id.rb_good_0 -> writingRequest.sentenceEvaluation = getString(R.string.tv_open_comment_evaluation_good)
            }
        }
        // 활동성 선택 값
        binding.rgEval1.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_leak_1 -> writingRequest.activity = getString(R.string.tv_open_comment_evaluation_leak)
                R.id.rb_normal_1 -> writingRequest.activity = getString(R.string.tv_open_comment_evaluation_normal)
                R.id.rb_good_1 -> writingRequest.activity = getString(R.string.tv_open_comment_evaluation_good)
            }
        }
        // 성실성 선택 값
        binding.rgEval2.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_leak_2 -> writingRequest.sincerity = getString(R.string.tv_open_comment_evaluation_leak)
                R.id.rb_normal_2 -> writingRequest.sincerity = getString(R.string.tv_open_comment_evaluation_normal)
                R.id.rb_good_2 -> writingRequest.sincerity = getString(R.string.tv_open_comment_evaluation_good)
            }
        }
        // 구체성과 논리성 선택 값
        binding.rgEval3.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_leak_3 -> writingRequest.concretenessLogic = getString(R.string.tv_open_comment_evaluation_leak)
                R.id.rb_normal_3 -> writingRequest.concretenessLogic = getString(R.string.tv_open_comment_evaluation_normal)
                R.id.rb_good_3 -> writingRequest.concretenessLogic = getString(R.string.tv_open_comment_evaluation_good)
            }
        }

        /** Save sentence temporary API **/
        /*
        binding.btnSaveTemp.setOnClickListener {
            if (binding.etInputComment.text.toString().length < 40) {
                CustomSnackbar.make(binding.root, getString(R.string.tv_writing_comment_input_caption), Snackbar.LENGTH_SHORT).show()
            }
            else {
                val dialog = CustomDialogFragment(
                    R.string.tv_dialog_sentence_temp_title,
                    R.string.tv_dialog_sentence_temp_content,
                    R.string.tv_dialog_temp,
                    R.string.tv_dialog_delete
                )
                dialog.setDialogClickListener(object : CustomDialogClickListener, WritingSentenceView {
                    override fun onPositiveClick() {
                        writingRequest.coverLetterContent = binding.etInputSentence.text.toString()
                        if (writingRequest.coverLetterCategoryId != null && writingRequest.coverLetterContent != null) {
                            WritingSentenceService(this).tryPostWritingSentence(writingRequest)
                        }
                    }

                    override fun onNegativeClick() {
                        // TODO : 취소 할건지 삭제 할건지 확인할 것
                    }

                    override fun onPostWritingSentenceSuccess(response: ResultResponse) {
                        if (response.isSuccess) {
                            finish()
                            finish()
                        }
                    }

                    override fun onPostWritingSentenceFailure(message: String) {
                        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
                    }
                })
                dialog.show(supportFragmentManager, "CustomDialog")
            }
        }*/

        /** Submit sentence API **/
        binding.btnSubmit.setOnClickListener {
            var isCheckedAll = true
            var isOverMinimum = true

            // 모든 항목 평가했는지 체크
            if (writingRequest.sentenceEvaluation == "" ||
                writingRequest.activity == "" ||
                writingRequest.sincerity == "" ||
                writingRequest.concretenessLogic == "") {
                isCheckedAll = false
                CustomSnackbar.make(binding.root, getString(R.string.tv_writing_comment_input_caption_1), Snackbar.LENGTH_SHORT).show()
            }
            // 40자 이상 체크
            if (binding.etInputComment.text.toString().length < 40) {
                isOverMinimum = false
                CustomSnackbar.make(binding.root, getString(R.string.tv_writing_comment_input_caption_0), Snackbar.LENGTH_SHORT).show()
            }
            // request가 모두 유효한 값이라면 등록 다이얼로그 띄우기
            if (isCheckedAll && isOverMinimum) {
                val dialog = CustomDialogFragment(
                    R.string.tv_dialog_sentence_submit_title,
                    R.string.tv_dialog_sentence_submit_content,
                    R.string.tv_dialog_submit,
                    R.string.tv_dialog_dismiss
                )
                dialog.setDialogClickListener(object : CustomDialogClickListener, WritingCommentView {
                    override fun onPositiveClick() {
                        // 코멘트 등록하기 API 적용
                        writingRequest.content = binding.etInputComment.text.toString()
                        if (writingRequest.coverLetterId != 0L &&
                            writingRequest.content != "" &&
                            writingRequest.sentenceEvaluation != "" &&
                            writingRequest.activity != "" &&
                            writingRequest.sincerity != "" &&
                            writingRequest.concretenessLogic != "") {

                            WritingCommentService(this).tryPostWritingComment(writingRequest)

                            CustomLoadingDialog(this@WritingCommentActivity).show()
                        }
                    }

                    override fun onNegativeClick() {
                    }

                    override fun onWritingCommentSuccess(response: BaseResponse) {
                        if (response.isSuccess) {
                            finish()
                            finish()
                        }
                        else {
                            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
                        }

                        CustomLoadingDialog(this@WritingCommentActivity).dismiss()
                    }

                    override fun onWritingCommentFailure(message: String) {
                        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

                        CustomLoadingDialog(this@WritingCommentActivity).dismiss()
                    }
                })
                dialog.show(supportFragmentManager, "CustomDialog")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}