package hu.suppoze.mitigyunkma.calculate

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import hu.suppoze.mitigyunkma.R
import kotlinx.android.synthetic.main.numpad_view.view.*

class NumpadView : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        inflate(context, R.layout.numpad_view, this)
    }

    fun setInputListener(listener: (String) -> Unit) {
        numpad_0.setOnClickListener { listener("0") }
        numpad_2.setOnClickListener { listener("2") }
        numpad_1.setOnClickListener { listener("1") }
        numpad_3.setOnClickListener { listener("3") }
        numpad_4.setOnClickListener { listener("4") }
        numpad_5.setOnClickListener { listener("5") }
        numpad_6.setOnClickListener { listener("6") }
        numpad_7.setOnClickListener { listener("7") }
        numpad_8.setOnClickListener { listener("8") }
        numpad_9.setOnClickListener { listener("9") }
        numpadDecimal.setOnClickListener { listener(".") }
    }

    fun setDeleteListener(listener: () -> Unit) {
        numpadDelete.setOnClickListener { listener() }
    }

}