package com.doublejj.edit.ui.modules.main.home.writing_sentence

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.writing_sentence.WritingSentenceService
import com.doublejj.edit.data.api.services.writing_sentence.WritingSentenceView
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.writing_sentence.WritingSentenceRequest
import com.doublejj.edit.databinding.ActivityWritingSentenceBinding
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class WritingSentenceActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityWritingSentenceBinding
    private var writingRequest = WritingSentenceRequest(null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_writing_sentence)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        /** set adapter for spinner **/
        val typeAdapter = object : ArrayAdapter<String>(this, R.layout.item_spinner) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                if (position == count) {
                    val tvItemSpinner = view.findViewById<TextView>(R.id.tv_item_spinner)
                    // ????????? position??? textView??? hint??? ??????
                    tvItemSpinner.text = ""
                    // item??? ????????? ?????? ????????? hint??? ??????
                    tvItemSpinner.hint = getItem(count)
                }
                return view
            }

            override fun getCount(): Int {
                // ????????? item??? hint?????? ??????????????? 1 ??????
                return super.getCount() - 1
            }
        }
        // item??? hint ??????, adapter??? ??????
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
                // ????????? ?????? ???????????? ??? 1?????? ??????
                writingRequest.coverLetterCategoryId = (position+1).toLong()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                writingRequest.coverLetterCategoryId = null
            }
        }

        /** edittext **/
        binding.etInputSentence.addTextChangedListener(object : TextWatcher {
            // gets triggered immediately after something is typed
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val limitCount = resources.getInteger(R.integer.length_limit_sentence)
                val includeSpaces = s.toString().length
                val withoutSpaces = s.toString().replace(" ", "").length

                // include spaces
                binding.tvInputSentenceIncludeSpaceCount.text = includeSpaces.toString()
                // without spaces
                binding.tvInputSentenceWithoutSpaceCount.text = withoutSpaces.toString()

            }

            // gets triggered before the next input
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val limitCount = resources.getInteger(R.integer.length_limit_sentence)
                val includeSpaces = s.toString().length
                val withoutSpaces = s.toString().replace(" ", "").length

                // include spaces
                binding.tvInputSentenceIncludeSpaceCount.text = includeSpaces.toString()
                // without spaces
                binding.tvInputSentenceWithoutSpaceCount.text = withoutSpaces.toString()
            }

            // gets triggered during an input
            override fun afterTextChanged(s: Editable?) {
                val limitCount = resources.getInteger(R.integer.length_limit_sentence)
                val includeSpaces = s.toString().length
                val withoutSpaces = s.toString().replace(" ", "").length
                var colorResId: Int

                // include spaces
                if (includeSpaces > limitCount) colorResId = R.color.red_light
                else colorResId = R.color.purple_active
                binding.tvInputSentenceIncludeSpaceCount.setTextColor(ContextCompat.getColor(applicationContext, colorResId))
                binding.tvInputSentenceIncludeSpaceCount.text = includeSpaces.toString()

                // without spaces
                if (withoutSpaces > limitCount) colorResId = R.color.red_light
                else colorResId = R.color.purple_active
                binding.tvInputSentenceWithoutSpaceCount.setTextColor(ContextCompat.getColor(applicationContext, colorResId))
                binding.tvInputSentenceWithoutSpaceCount.text = withoutSpaces.toString()

                if (withoutSpaces > limitCount) {
                    if (binding.btnSubmit.isEnabled) Toast.makeText(applicationContext, getString(R.string.tv_self_writing_input_hint), Toast.LENGTH_SHORT).show()

                    // button
                    binding.btnSubmit.isEnabled = false
                    binding.btnSubmit.setTextColor(ContextCompat.getColor(applicationContext, R.color.mid_gray))
                }
                else {
                    binding.btnSubmit.isEnabled = true
                    binding.btnSubmit.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
                }
            }
        })

        /** Save sentence temporary API **/
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
                    // TODO : ?????? ????????? ?????? ????????? ????????? ???
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

        /** Submit sentence API **/
        binding.btnSubmit.setOnClickListener {
            val dialog = CustomDialogFragment(
                R.string.tv_dialog_sentence_submit_title,
                R.string.tv_dialog_sentence_submit_content,
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

    fun dipToPixels(dipValue: Float) : Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, resources.displayMetrics).toInt()
    }

}