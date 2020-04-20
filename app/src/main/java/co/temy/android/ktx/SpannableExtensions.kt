package co.temy.android.ktx

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View

fun Array<out StyleSpan>.replaceItalicInSpannable(spannable: SpannableString) = forEach {
    if (it.style == Typeface.ITALIC) {
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            spannable.getSpanStart(it),
            spannable.getSpanEnd(it),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(Color.BLACK),
            spannable.getSpanStart(it),
            spannable.getSpanEnd(it),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.removeSpan(it)
    }
}

fun Spannable.makeTextClickable(clickableText: String, clickableTextColor: Int, clickListener: () -> (Unit)): SpannableString {
    val regex = Regex(clickableText)
    val matches = regex.findAll(this)
    val ranges = matches.map { it.range }
    val spannable = SpannableString(this)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) { clickListener.invoke() }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = clickableTextColor
            ds.isUnderlineText = false
        }
    }
    ranges.forEach {
        spannable.setSpan(clickableSpan, it.first, it.last + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    return spannable
}