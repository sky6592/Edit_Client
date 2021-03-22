package com.doublejj.edit.ui.utils.span

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat

class CustomSpannableString(val context: Context) {
    fun getPurpleActiveColorText(string: String, targetString: String, color: Int) : SpannableString {
        val spannableString = SpannableString(string)
        val targetStartIndex = string.indexOf(targetString)
        val targetEndIndex = targetStartIndex + targetString.length
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, color)),
                targetStartIndex,
                targetEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        return spannableString
    }
}