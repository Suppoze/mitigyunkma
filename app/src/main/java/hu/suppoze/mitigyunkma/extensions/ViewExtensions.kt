package hu.suppoze.mitigyunkma.extensions

import android.support.v7.widget.PopupMenu
import android.view.Gravity
import android.view.MenuItem
import android.view.View

fun View.showPopup(menuId: Int, onMenuItemClick: (item: MenuItem) -> Boolean ) {
    val popup: PopupMenu = PopupMenu(this.context, this)
    popup.setOnMenuItemClickListener(onMenuItemClick)
    popup.menuInflater.inflate(menuId, popup.menu)
    popup.gravity = Gravity.LEFT
    popup.show()
}