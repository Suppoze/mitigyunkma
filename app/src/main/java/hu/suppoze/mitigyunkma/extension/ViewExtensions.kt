package hu.suppoze.mitigyunkma.extension

// TODO: cannot use support PopupMenu due to a scrolling bug: https://code.google.com/p/android/issues/detail?id=135439
// import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu

fun View.showPopup(menuId: Int, onMenuItemClick: (item: MenuItem) -> Boolean) {
    val popup: PopupMenu = PopupMenu(context, this)
    popup.setOnMenuItemClickListener(onMenuItemClick)
    popup.menuInflater.inflate(menuId, popup.menu)
//    popup.gravity = Gravity.START
    popup.show()
}