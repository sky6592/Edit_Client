package com.doublejj.edit.ui.modules.main.home.writing_comment

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.writing_sentence.WritingSentenceService
import com.doublejj.edit.data.api.services.writing_sentence.WritingSentenceView
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.databinding.ActivityWritingCommentBinding
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class WritingCommentActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityWritingCommentBinding
//    private var writingRequest = WritingSentenceRequest(null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_writing_comment)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }
/*
        *//** set adapter for spinner **//*
        val typeAdapter = object : ArrayAdapter<String>(this, R.layout.item_spinner) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                if (position == count) {
                    val tvItemSpinner = view.findViewById<TextView>(R.id.tv_item_spinner)
                    // 마지막 position의 textView를 hint로 사용
                    tvItemSpinner.text = ""
                    // item의 마지막 값을 불러와 hint로 추가
                    tvItemSpinner.hint = getItem(count)
                }
                return view
            }

            override fun getCount(): Int {
                // 마지막 item은 hint로만 사용하기에 1 빼기
                return super.getCount() - 1
            }
        }
        // item과 hint 추가, adapter에 연결
        val typeStringArray = resources.getStringArray(R.array.array_sentence_type)
        typeAdapter.addAll(typeStringArray.toMutableList())
        typeAdapter.add(getString(R.string.spinner_hint))
        binding.spinnerSelectSentenceType.adapter = typeAdapter

        // settings for adapter
        binding.spinnerSelectSentenceType.setSelection(typeAdapter.count)
        binding.spinnerSelectSentenceType.setPopupBackgroundResource(R.drawable.shape_white_bg_round_4dp)

        // convert dp to px
        binding.spinnerSelectSentenceType.dropDownVerticalOffset = dipToPixels(40f)

        binding.spinnerSelectSentenceType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 서버에 넣을 카테고리 값 1부터 시작
                writingRequest.coverLetterCategoryId = (position+1).toLong()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                writingRequest.coverLetterCategoryId = null
            }
        }

        */
        /** edittext **/
        // TODO : 40자 이상 작성
//        binding.etInputComment.filters = arrayOf(
//            InputFilter.LengthFilter(resources.getInteger(R.integer.length_limit_sentence))
//        )
        /*binding.etInputComment.addTextChangedListener(object : TextWatcher {
            // gets triggered immediately after something is typed
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // gets triggered before the next input
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            // gets triggered during an input
            override fun afterTextChanged(s: Editable?) {
//                val limitCount = resources.getInteger(R.integer.length_limit_sentence)

            }
        })*/


        /** Save sentence temporary API **/
        /*
        binding.btnSaveTemp.setOnClickListener {
            if (binding.etInputComment.text.toString().length < 40) {
                CustomSnackbar.make(binding.root, getString(R.string.tv_writing_comment_input_caption), Snackbar.LENGTH_SHORT)
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
                        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
                    }
                })
                dialog.show(supportFragmentManager, "CustomDialog")
            }
        }

        *//** Submit sentence API **/
        binding.btnSubmit.setOnClickListener {
            if (binding.etInputComment.text.toString().length < 40) {
                CustomSnackbar.make(binding.root, getString(R.string.tv_writing_comment_input_caption), Snackbar.LENGTH_SHORT)
            }
            else {
                val dialog = CustomDialogFragment(
                    R.string.tv_dialog_sentence_submit_title,
                    R.string.tv_dialog_sentence_submit_content,
                    R.string.tv_dialog_submit,
                    R.string.tv_dialog_dismiss
                )
                dialog.setDialogClickListener(object : CustomDialogClickListener, WritingSentenceView {
                    override fun onPositiveClick() {
                        // TODO : 코멘트 등록하기 API 적용
//                        writingRequest.coverLetterContent = binding.etInputSentence.text.toString()
//                        if (writingRequest.coverLetterCategoryId != null && writingRequest.coverLetterContent != null) {
//                            WritingSentenceService(this).tryPostWritingSentence(writingRequest)
//                        }
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
                        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
                    }
                })
                dialog.show(supportFragmentManager, "CustomDialog")
            }
        }
    }

    fun dipToPixels(dipValue: Float) : Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, resources.displayMetrics).toInt()
    }
}