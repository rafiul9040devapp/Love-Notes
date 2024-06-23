package com.rafiul.lovenotes.utils

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextToSpeechHelper @Inject constructor(@ApplicationContext private val context: Context) {

    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false
    private var onProgress: ((Int) -> Unit)? = null

    init {
        initializeTts {}
    }

    private fun initializeTts(onInitCallback: () -> Unit) {
        tts = TextToSpeech(context,TextToSpeech.OnInitListener { status->
            context.showToast(status.toString())
            checkTheStatusOfInitialization(status, onInitCallback)
        },null )
    }

    private fun checkTheStatusOfInitialization(status: Int, onInitCallback: () -> Unit) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                context.showToast("The language is not supported.")
                Log.e("TTS", "The language is not supported.")
            } else {
                isTtsInitialized = true
                Log.i("TTS", "TTS initialized successfully.")
                context.showToast("TTS initialized successfully.")
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        Log.i("TTS", "TTS onStart")
                    }

                    override fun onDone(utteranceId: String?) {
                        Log.i("TTS", "TTS onDone")
                        onProgress?.invoke(-1)  // -1 to signify the end
                    }

                    override fun onError(utteranceId: String?) {
                        context.showToast("Unable to play the voice message")
                        Log.e("TTS", "TTS onError")
                    }

                    override fun onRangeStart(
                        utteranceId: String?,
                        start: Int,
                        end: Int,
                        frame: Int
                    ) {
                        Log.i("TTS", "TTS onRangeStart: $start - $end")
                        onProgress?.invoke(end)
                    }
                })
                onInitCallback()
            }
        } else {
            context.showToast("Initialization failed.")
            Log.e("TTS", "Initialization failed.")
        }
    }

    fun speak(text: String, onProgressCallback: (Int) -> Unit) {
        when {
            isTtsInitialized -> speakUpText(onProgressCallback, text)
            else -> initializeTts {
                speakUpText(onProgressCallback, text)
            }
        }
    }

    private fun speakUpText(onProgressCallback: (Int) -> Unit, text: String) {
        onProgress = onProgressCallback
        val params = Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID")
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params, "UniqueID")
    }

    fun updateTextHighlight(textView: TextView, fullText: String, progress: Int) {
        val spannableString = SpannableString(fullText)
        spannableString.setSpan(
            ForegroundColorSpan(Color.RED),
            0,
            progress,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spannableString
    }

    fun release() {
        tts?.stop()
        tts?.shutdown()
        isTtsInitialized = false
        Log.i("TTS", "TTS released")
    }
}
