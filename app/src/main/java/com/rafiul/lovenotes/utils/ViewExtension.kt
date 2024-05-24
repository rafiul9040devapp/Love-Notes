package com.rafiul.lovenotes.utils

import android.view.View

fun View.toggleVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

