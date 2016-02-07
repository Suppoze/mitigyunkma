package hu.suppoze.mitigyunkma.base

import android.support.v4.app.Fragment
import org.jetbrains.anko.support.v4.alert

abstract class BaseFragment : Fragment() {

    fun showAlert(message: String) {
            alert(message) {
                positiveButton("OK") { }
            }.show()
    }

}
