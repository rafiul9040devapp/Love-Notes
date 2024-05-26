package com.rafiul.lovenotes.utils

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rafiul.lovenotes.R


//fun View.runWithProgressBar(delayMillis: Long, action: () -> Unit) {
//    val rootView = (this.context as? Activity)?.findViewById<ViewGroup>(android.R.id.content)
//    rootView?.let { viewGroup ->
//        val inflater = LayoutInflater.from(context)
//        val overlay = inflater.inflate(R.layout.progress_overlay, viewGroup, false)
//
//        viewGroup.addView(overlay)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            viewGroup.removeView(overlay)
//            action()
//        }, delayMillis)
//    }
//}




fun View.runWithProgressBar(delayMillis: Long, action: () -> Unit) {
    val rootView = (this.context as? Activity)?.findViewById<ViewGroup>(android.R.id.content)
    rootView?.let { viewGroup ->
        val inflater = LayoutInflater.from(context)
        val overlay = inflater.inflate(R.layout.progress_overlay, viewGroup, false)

        viewGroup.addView(overlay)

        Handler(Looper.getMainLooper()).postDelayed({
            viewGroup.removeView(overlay)
            // Ensure that action is run on the main thread
            if (Looper.myLooper() == Looper.getMainLooper()) {
                action()
            } else {
                Handler(Looper.getMainLooper()).post { action() }
            }
        }, delayMillis)
    }
}

