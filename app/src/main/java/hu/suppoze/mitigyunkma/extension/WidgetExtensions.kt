package hu.suppoze.mitigyunkma.extension

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.support.design.widget.TabLayout
import android.support.v4.graphics.drawable.DrawableCompat

fun TabLayout.setIconColorStateList(context: Context, colorStateListId: Int) {
    for (tabIndex in 0..tabCount - 1) {
        var icon = getTabAt(tabIndex)?.icon ?: break
        icon = DrawableCompat.wrap(icon)
        DrawableCompat.setTintList(icon, context.getColorStateListCompat(colorStateListId))
    }
}

@Suppress("DEPRECATION")
private fun Context.getColorStateListCompat(colorStateId: Int) : ColorStateList? {
    if (Build.VERSION.SDK_INT >= 23) {
        return resources.getColorStateList(colorStateId, theme)
    } else {
        return resources.getColorStateList(colorStateId)
    }
}