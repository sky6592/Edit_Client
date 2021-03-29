package com.doublejj.edit.ui.utils.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.DialogLoadingBinding

class CustomLoadingDialog(context: Context) : Dialog(context) {
    private var binding: DialogLoadingBinding

    companion object {
        fun make(context: Context) = CustomLoadingDialog(context)
    }

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_loading, null, false)
        setContentView(binding.root)

        setCanceledOnTouchOutside(false)
        setCancelable(false)

        this.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val anim = AnimationUtils.loadAnimation(context, R.anim.loading)
        binding.cvLogo.animation = anim
    }

    override fun show() {
        if(!this.isShowing) super.show()
    }

    override fun dismiss() {
        if(this.isShowing) super.dismiss()
    }
}