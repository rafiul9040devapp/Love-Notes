package com.rafiul.lovenotes.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rafiul.lovenotes.R

fun View.toggleVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.setEnabledState(enabled: Boolean= true ) {
    this.isEnabled = enabled
}

fun View.setDisabledState(disabled: Boolean = false) {
    this.isEnabled = disabled
}


fun Fragment.hideAppBar() {
    (activity as? AppCompatActivity)?.supportActionBar?.hide()
}

fun Fragment.showAppBar() {
    (activity as? AppCompatActivity)?.supportActionBar?.show()
}


fun Fragment.enableHomeAsUp(enabled: Boolean) {
    (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
}

fun TextView.updateTextHighlight(context: Context, fullText: String, progress: Int) {
    val spannableString = SpannableString(fullText)
    val words = fullText.split(" ")

    var charIndex = 0
    for (word in words) {
        val endCharIndex = charIndex + word.length
        if (charIndex <= progress) {
            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.blur)),
                charIndex,
                endCharIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else {
            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.red)),
                charIndex,
                endCharIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        charIndex += word.length + 1
    }

    this.text = spannableString
}
