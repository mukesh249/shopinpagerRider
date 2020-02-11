package shopinpager.wingstud.shopinpagerrider.extensions

import android.annotation.SuppressLint
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.text.SimpleDateFormat
import java.util.*


fun ViewGroup.inflate(layout: Int, attachToRoot: Boolean = false) =
    LayoutInflater.from(this.context).inflate(layout, this, attachToRoot)


fun SpannableString.setLinkSpan(text: String, action: () -> Unit, color: Int) {
    val textIndex = this.indexOf(text, ignoreCase = true)

    val span =
        object : ClickableSpan() {
            override fun onClick(widget: View) {
                Log.d("StringExtension", "onLinkClick")
                action()
            }

            override fun updateDrawState(ds: TextPaint) {
                //ds.linkColor = Color.WHITE
                ds.color = color
                ds.isUnderlineText = true
            }
        }

    setSpan(
        span,
        textIndex,
        textIndex + text.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}


@SuppressLint("SimpleDateFormat")
fun isTimingClosed(checkTime: Long): Boolean {
    val opening = SimpleDateFormat("HH:mm:ss").parse("10:00:00")
    val closing = SimpleDateFormat("HH:mm:ss").parse("23:30:00")
    val timeToCheck = SimpleDateFormat("HH:mm:ss").format(Date(checkTime))
    val d = SimpleDateFormat("HH:mm:ss").parse(timeToCheck)
    return !(d!!.after(opening) && d.before(closing))
}