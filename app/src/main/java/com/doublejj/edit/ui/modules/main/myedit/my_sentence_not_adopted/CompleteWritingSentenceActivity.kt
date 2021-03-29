package com.doublejj.edit.ui.modules.main.myedit.my_sentence_not_adopted

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.writing_sentence.WritingSentenceService
import com.doublejj.edit.data.api.services.writing_sentence.WritingSentenceView
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.writing_sentence.WritingSentenceRequest
import com.doublejj.edit.databinding.ActivityCompleteWritingSentenceBinding
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class CompleteWritingSentenceActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityCompleteWritingSentenceBinding
    private var writingRequest = WritingSentenceRequest(null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complete_writing_sentence)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        /** set sentence, comment **/
        val originalCoverLetterId = intent.getLongExtra("originalCoverLetterId", 0L)
        binding.tvSelfWritingType.text = intent.getStringExtra("originalCoverLetterCategoryName")
        binding.tvBeforeSentenceTitle.text = intent.getStringExtra("originalCoverLetterContent")
        binding.tvBeforeCommentTitle.text = intent.getStringExtra("adoptedCommentContent")

        /** edittext **/
        binding.etInputSentence.filters = arrayOf(
            InputFilter.LengthFilter(resources.getInteger(R.integer.length_limit_sentence))
        )

        binding.etInputSentence.addTextChangedListener(object : TextWatcher {
            // gets triggered immediately after something is typed
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // include spaces
                binding.tvInputSentenceIncludeSpaceCount.text = s.toString().length.toString()
                // without spaces
                binding.tvInputSentenceWithoutSpaceCount.text = s.toString().replace(" ", "").length.toString()
            }

            // gets triggered before the next input
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // include spaces
                binding.tvInputSentenceIncludeSpaceCount.text = s.toString().length.toString()
                // without spaces
                binding.tvInputSentenceWithoutSpaceCount.text = s.toString().replace(" ", "").length.toString()
            }

            // gets triggered during an input
            override fun afterTextChanged(s: Editable?) {
                val limitCount = resources.getInteger(R.integer.length_limit_sentence)
                val includeSpaces = s.toString().length
                val withoutSpaces = s.toString().replace(" ", "").length
                var colorResId: Int

                // include spaces
                if (includeSpaces >= limitCount) colorResId = R.color.red_light
                else colorResId = R.color.purple_active
                binding.tvInputSentenceIncludeSpaceCount.setTextColor(ContextCompat.getColor(applicationContext, colorResId))
                binding.tvInputSentenceIncludeSpaceCount.text = includeSpaces.toString()

                // without spaces
                if (withoutSpaces >= limitCount) colorResId = R.color.red_light
                else colorResId = R.color.purple_active
                binding.tvInputSentenceWithoutSpaceCount.setTextColor(ContextCompat.getColor(applicationContext, colorResId))
                binding.tvInputSentenceWithoutSpaceCount.text = withoutSpaces.toString()
            }
        })

        /** Save sentence temporary API **//*
        binding.btnSaveTemp.setOnClickListener {
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
        }*/

        /** Submit sentence API **/
        binding.btnSubmit.setOnClickListener {
            val dialog = CustomDialogFragment(
                R.string.tv_dialog_sentence_complete_title,
                R.string.tv_dialog_sentence_complete_content,
                R.string.tv_dialog_submit,
                R.string.tv_dialog_dismiss
            )
            dialog.setDialogClickListener(object : CustomDialogClickListener, WritingSentenceView {
                override fun onPositiveClick() {
                    writingRequest.coverLetterContent = binding.etInputSentence.text.toString()
                    if (writingRequest.coverLetterCategoryId != null && writingRequest.coverLetterContent != null) {
                        WritingSentenceService(this).tryPostWritingSentence(writingRequest)
                    }
                }

                override fun onNegativeClick() {
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
    }

}