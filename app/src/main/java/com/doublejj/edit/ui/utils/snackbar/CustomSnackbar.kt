package com.doublejj.edit.ui.utils.snackbar

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.SnackbarCustomBinding
import com.google.android.material.snackbar.Snackbar

class CustomSnackbar(view: View, private val message: String, duration: Int) {
    companion object {
        fun make(view: View, message: String, duration: Int) = CustomSnackbar(view, message, duration)
    }

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", duration)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    val snackbarCustomBinding: SnackbarCustomBinding = DataBindingUtil.inflate(inflater, R.layout.snackbar_custom, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarCustomBinding.root, 0)
        }
    }

    private fun initData() {
        snackbarCustomBinding.tvSnackbarMessage.text = message
    }

    fun show() {
        snackbar.show()
    }
}