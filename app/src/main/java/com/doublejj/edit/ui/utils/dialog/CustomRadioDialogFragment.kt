package com.doublejj.edit.ui.utils.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.doublejj.edit.R
import com.doublejj.edit.databinding.DialogRadiobuttonsBinding

class CustomRadioDialogFragment(val type: String, var selectedId: Int?) : DialogFragment() {
    lateinit var dialogCustomBinding: DialogRadiobuttonsBinding
    lateinit var customDialogClickListener: CustomDialogClickListener
    val radioJob = mutableMapOf<Int, Int>(
        Pair(R.id.rb_job_0, R.string.spinner_job_0),
        Pair(R.id.rb_job_1, R.string.spinner_job_1),
        Pair(R.id.rb_job_2, R.string.spinner_job_2),
        Pair(R.id.rb_job_3, R.string.spinner_job_3),
        Pair(R.id.rb_job_4, R.string.spinner_job_4),
        Pair(R.id.rb_job_5, R.string.spinner_job_5),
        Pair(R.id.rb_job_6, R.string.spinner_job_6),
        Pair(R.id.rb_job_7, R.string.spinner_job_7),
        Pair(R.id.rb_job_8, R.string.spinner_job_8),
        Pair(R.id.rb_job_9, R.string.spinner_job_9),
        Pair(R.id.rb_job_10, R.string.spinner_job_10),
        Pair(R.id.rb_job_11, R.string.spinner_job_11),
        Pair(R.id.rb_job_12, R.string.spinner_job_12)
    )
    var selectedStrId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialogCustomBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_radiobuttons, null, false)
        var view = dialogCustomBinding.root

        if (type == "JOB") {
            dialogCustomBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_radiobuttons, null, false)
            view = dialogCustomBinding.root

            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

            if (selectedId == null) {
                dialogCustomBinding.rgJob.clearCheck()
            }
            else {
                dialogCustomBinding.rgJob.check(selectedId!!)
            }

            // 라디오버튼 선택
            dialogCustomBinding.rgJob.setOnCheckedChangeListener { group, checkedId ->
                selectedId = checkedId
                selectedStrId = radioJob[checkedId]
            }
        }

        dialogCustomBinding.tvConfirm.setOnClickListener {
            customDialogClickListener.onPositiveClick()
            dismiss()
        }
        dialogCustomBinding.tvDismiss.setOnClickListener {
            customDialogClickListener.onNegativeClick()
            dismiss()
        }

        return view
    }

    fun setDialogClickListener(customDialogClickListener: CustomDialogClickListener) {
        this.customDialogClickListener = customDialogClickListener
    }
}
