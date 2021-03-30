package com.doublejj.edit.ui.utils.dialog

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
import com.doublejj.edit.databinding.DialogTwoButtonsBinding

class CustomDialogFragment(
    private val title: Int,
    private val content: Int,
    private val confirm: Int?,
    private val dismiss: Int?
) : DialogFragment() {
    lateinit var dialogCustomBinding: DialogTwoButtonsBinding
    lateinit var customDialogClickListener: CustomDialogClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialogCustomBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_two_buttons, null, false)
        val view = dialogCustomBinding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        dialogCustomBinding.tvDialogTitle.setText(title)
        dialogCustomBinding.tvDialogContent.setText(content)

        if (confirm != null) {
            dialogCustomBinding.tvConfirm.isEnabled = true
            dialogCustomBinding.tvConfirm.setText(confirm)
        }
        else {
            dialogCustomBinding.tvConfirm.isEnabled = false
            dialogCustomBinding.tvConfirm.visibility = View.GONE
        }
        if (dismiss != null) {
            dialogCustomBinding.tvDismiss.isEnabled = true
            dialogCustomBinding.tvDismiss.setText(dismiss)
        }
        else {
            dialogCustomBinding.tvDismiss.isEnabled = false
            dialogCustomBinding.tvDismiss.visibility = View.GONE
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
