package hu.suppoze.mitigyunkma.calculate

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class TextChangeHandler(textView: TextView, changeHandler: () -> Int) : TextWatcher {
    val textView: TextView
    val handler: () -> Int

    init {
        this.textView = textView
        this.handler = changeHandler
    }

    override fun afterTextChanged(s: Editable?) { }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        async {
            val index = handler()

            uiThread {
                textView.text = index.toString()
            }
        }
    }
}