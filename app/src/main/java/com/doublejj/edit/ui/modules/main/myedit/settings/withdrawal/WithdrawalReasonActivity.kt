package com.doublejj.edit.ui.modules.main.myedit.settings.withdrawal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.withdrawal.WithdrawalService
import com.doublejj.edit.data.api.services.withdrawal.WithdrawalView
import com.doublejj.edit.data.models.ResultStringResponse
import com.doublejj.edit.data.models.withdrawal.WithdrawalRequest
import com.doublejj.edit.data.models.withdrawal.WithdrawalResponse
import com.doublejj.edit.databinding.ActivityWithdrawalReasonBinding
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.doublejj.edit.ui.utils.span.CustomSpannableString
import com.google.android.material.snackbar.Snackbar

class WithdrawalReasonActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityWithdrawalReasonBinding
    private var withdrawalContent: Int? = null
    private var etcWithdrawalContent: String? = null
    private lateinit var nickName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_withdrawal_reason)

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
        val typeStringArray = resources.getStringArray(R.array.array_withdrawal_type).toMutableList()
        typeAdapter.addAll(typeStringArray)
        typeAdapter.add(getString(R.string.spinner_hint))
        binding.spinnerSelectWithdrawalType.adapter = typeAdapter

        // settings for adapter
        binding.spinnerSelectWithdrawalType.setSelection(typeAdapter.count)
        binding.spinnerSelectWithdrawalType.setPopupBackgroundResource(R.drawable.shape_white_bg_round_4dp)

        // convert dp to px
        binding.spinnerSelectWithdrawalType.dropDownVerticalOffset = dipToPixels(40f)

        binding.spinnerSelectWithdrawalType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                withdrawalContent = position

                when (withdrawalContent) {
                    0, 1, 2 -> {
                        binding.btnNext.isEnabled = true
                        binding.llWithdrawalEtc.visibility = View.GONE
                    }
                    3 -> {
                        binding.llWithdrawalEtc.visibility = View.VISIBLE
                        binding.btnNext.isEnabled = binding.etInputWithdrawalEtcContent.text.toString().length >= 10
                    }
                    null -> {
                        binding.btnNext.isEnabled = true
                        binding.llWithdrawalEtc.visibility = View.GONE
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                withdrawalContent = null
            }
        }

        binding.etInputWithdrawalEtcContent.addTextChangedListener(object : TextWatcher {
            // gets triggered immediately after something is typed
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.btnNext.isEnabled = binding.etInputWithdrawalEtcContent.text.toString().length >= 10
            }

            // gets triggered before the next input
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnNext.isEnabled = binding.etInputWithdrawalEtcContent.text.toString().length >= 10
            }

            // gets triggered during an input
            override fun afterTextChanged(s: Editable?) {
                binding.btnNext.isEnabled = binding.etInputWithdrawalEtcContent.text.toString().length >= 10
            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.btnNext.isEnabled) {
                val dialog = CustomDialogFragment(
                    R.string.tv_dialog_withdrawal_title,
                    R.string.tv_dialog_withdrawal_content,
                    R.string.tv_dialog_agree,
                    R.string.tv_dialog_dismiss
                )

                dialog.setDialogClickListener(object : CustomDialogClickListener, WithdrawalView {
                    override fun onPositiveClick() {
                        // 기타 선택 후 의견이 유효하도록 NONE 처리
                        if (withdrawalContent == 3 && binding.etInputWithdrawalEtcContent.length() >= 10) {
                            etcWithdrawalContent = binding.etInputWithdrawalEtcContent.text.toString()
                        }
                        else {
                            etcWithdrawalContent = "NONE"
                        }

                        // 회원탈퇴 API
                        WithdrawalService(this).tryDeleteWithdrawal(
                            WithdrawalRequest(
                                withdrawalContent = getSelectedString(withdrawalContent!!),
                                etcWithdrawalContent = etcWithdrawalContent!!
                            )
                        )
                    }
                    override fun onNegativeClick() {
                    }

                    override fun onWithdrawalSuccess(response: WithdrawalResponse) {
                        if (response.isSuccess) {
                            // dialog에서 activity로 전환하기 위해 dialog 밖의 activity 넘겨줌
                            val sendIntent = Intent(this@WithdrawalReasonActivity, WithdrawalCompleteActivity::class.java)
                            sendIntent.putExtra("nickName", nickName)
                            startActivity(sendIntent)
                        }
                    }

                    override fun onWithdrawalFailure(message: String) {
                        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
                    }
                })
                dialog.show(supportFragmentManager, "CustomDialog")
            }
        }
    }

    fun getSelectedString(index: Int) : String {
        val typeStringArray = resources.getStringArray(R.array.array_withdrawal_type).toMutableList()
        return typeStringArray[index]
    }

    fun dipToPixels(dipValue: Float) : Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, resources.displayMetrics).toInt()
    }

}