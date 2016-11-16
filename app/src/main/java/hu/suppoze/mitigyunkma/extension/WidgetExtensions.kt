package hu.suppoze.mitigyunkma.extension

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import hu.suppoze.mitigyunkma.R
import org.jetbrains.anko.inputMethodManager

fun TabLayout.setIconColorStateList(context: Context, colorStateListId: Int) {
    for (tabIndex in 0..tabCount - 1) {
        var icon = getTabAt(tabIndex)?.icon ?: break
        icon = DrawableCompat.wrap(icon)
        DrawableCompat.setTintList(icon, context.getColorStateListCompat(colorStateListId))
    }
}

@Suppress("DEPRECATION")
private fun Context.getColorStateListCompat(colorStateId: Int): ColorStateList? {
    if (Build.VERSION.SDK_INT >= 23) {
        return resources.getColorStateList(colorStateId, theme)
    } else {
        return resources.getColorStateList(colorStateId)
    }
}


fun Context.hideKeyboard(view: View) {
    this.inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.doOnTextChanged(action: () -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            action()
        }
    })
}

fun TextView.setTextColorBasedOnRating(rating: Double, context: Context) {
    val ratingClass = Math.round(rating * 7).toInt()
    val colorId = when (ratingClass) {
        0 -> R.color.rating_0
        1 -> R.color.rating_1
        2 -> R.color.rating_2
        3 -> R.color.rating_3
        4 -> R.color.rating_4
        5 -> R.color.rating_5
        6 -> R.color.rating_6
        7 -> R.color.rating_7
        else -> R.color.rating_none
    }
    this.setTextColorWithAnimation(ContextCompat.getColor(context, colorId))
}

fun TextView.setTextColorWithAnimation(toTextColor: Int, duration: Long = 200) {
    val textColorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), this.currentTextColor, toTextColor)
    textColorAnimation.duration = duration
    textColorAnimation.addUpdateListener { this.setTextColor(it.animatedValue as Int) }
    textColorAnimation.start()
}