package com.rafiul.lovenotes.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun View.toggleVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.setEnabledState(enabled: Boolean) {
    this.isEnabled = enabled
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
