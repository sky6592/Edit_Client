package com.doublejj.edit.ui.modules.main.myedit.switch_position

import android.content.Intent
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
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.switch_position.SwitchPositionView
import com.doublejj.edit.data.api.services.switch_position.SwitchToMentorService
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.switch_position.SwitchPositionRequest
import com.doublejj.edit.databinding.ActivitySwitchPositionReasonBinding
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.doublejj.edit.ui.utils.span.CustomSpannableString
import com.google.android.material.snackbar.Snackbar

class SwitchToMentorReasonActivity : AppCompatActivity(), SwitchPositionView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivitySwitchPositionReasonBinding
    private var changeContent: Int? = null
    private var etcChangeContent: String? = null
    private lateinit var nickName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_switch_position_reason)

        // add activity at sActivityList
        ApplicationClass.sActivityList.add(this)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        // apply span to nickname
        nickName = intent.getStringExtra("nickName") ?: ""
        val textTitle = nickName + binding.tvReasonTitle.text.toString()
        val spanStr = CustomSpannableString(applicationContext).getPurpleActiveColorText(textTitle, nickName, R.color.purple_active)
        binding.tvReasonTitle.text = spanStr

        /** set adapter for spinner **/
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
        val typeStringArray = resources.getStringArray(R.array.array_mentee_to_mentor_type).toMutableList()
        typeAdapter.addAll(typeStringArray)
        typeAdapter.add(getString(R.string.spinner_hint))
        binding.spinnerSelectReasonType.adapter = typeAdapter

        // settings for adapter
        binding.spinnerSelectReasonType.setSelection(typeAdapter.count)
        binding.spinnerSelectReasonType.setPopupBackgroundResource(R.drawable.shape_white_bg_round_4dp)

        // convert dp to px
        binding.spinnerSelectReasonType.dropDownVerticalOffset = dipToPixels(40f)

        binding.spinnerSelectReasonType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                changeContent = position

                when (changeContent) {
                    0, 1, 2, 3 -> {
                        binding.btnNext.isEnabled = true
                        binding.llReasonEtc.visibility = View.GONE
                    }
                    4 -> {
                        binding.llReasonEtc.visibility = View.VISIBLE
                        binding.btnNext.isEnabled = binding.etInputReasonEtcContent.text.toString().length >= 10
                    }
                    null -> {
                        binding.btnNext.isEnabled = true
                        binding.llReasonEtc.visibility = View.GONE
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                changeContent = null
            }
        }

        binding.etInputReasonEtcContent.addTextChangedListener(object : TextWatcher {
            // gets triggered immediately after something is typed
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.btnNext.isEnabled = binding.etInputReasonEtcContent.text.toString().length >= 10
            }

            // gets triggered before the next input
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnNext.isEnabled = binding.etInputReasonEtcContent.text.toString().length >= 10
            }

            // gets triggered during an input
            override fun afterTextChanged(s: Editable?) {
                binding.btnNext.isEnabled = binding.etInputReasonEtcContent.text.toString().length >= 10
            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.btnNext.isEnabled) {
                // 기타 선택 후 의견이 유효하도록 NONE 처리
                if (changeContent == 4 && binding.etInputReasonEtcContent.length() >= 10) {
                    etcChangeContent = binding.etInputReasonEtcContent.text.toString()
                }
                else {
                    etcChangeContent = "NONE"
                }

                // switch mentee to mentor 역할 변경 API
                SwitchToMentorService(this).tryPatchSwitchToMentor(
                    SwitchPositionRequest(
                        changeContent = getSelectedString(changeContent!!),
                        etcChangeContent = etcChangeContent!!
                    )
                )
            }
        }
    }

    fun getSelectedString(index: Int) : String {
        val typeStringArray = resources.getStringArray(R.array.array_mentee_to_mentor_type).toMutableList()
        return typeStringArray[index]
    }

    fun dipToPixels(dipValue: Float) : Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, resources.displayMetrics).toInt()
    }


    override fun onSwitchPositionSuccess(response: ResultResponse) {
        if (response.isSuccess) {
            val sendIntent = Intent(this, SwitchPositionCompleteActivity::class.java)
            sendIntent.putExtra("nickName", nickName)
            startActivity(sendIntent)
        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onSwitchPositionFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        ApplicationClass.sActivityList.remove(this)
    }
}