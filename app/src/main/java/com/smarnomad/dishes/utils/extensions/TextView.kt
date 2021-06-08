package com.smarnomad.dishes.utils.extensions

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

/**
 * Span class for non underlined text
 */
private class NoUnderlineSpan : UnderlineSpan() {
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
    }
}

/**
 * Extension function to remove underline from [AppCompatTextView] with links
 */
fun AppCompatTextView.removeUnderline() {
    val spannableText = this.text as? Spannable
    spannableText?.let {
        val spans = it.getSpans(0, it.length, URLSpan::class.java)
        for (span in spans) {
            val start = it.getSpanStart(span)
            val end = it.getSpanEnd(span)
            val noUnderline = NoUnderlineSpan()
            it.setSpan(noUnderline, start, end, 0)
        }
    }
}

fun AppCompatTextView.applyUnderline() {
    val spannableText = this.text
    spannableText?.let {
        val content = SpannableString(spannableText)
        text = SpannableString(spannableText).apply {
            setSpan(UnderlineSpan(), 0, content.length, 0)
        }
    }
}

fun TextView.addAnimation(id: Int, context: Context){
    this.animation = AnimationUtils.loadAnimation(context,id)
}
