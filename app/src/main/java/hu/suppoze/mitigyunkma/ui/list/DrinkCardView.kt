package hu.suppoze.mitigyunkma.ui.list

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.entity.Drink
import hu.suppoze.mitigyunkma.extension.prettyPrint
import kotlinx.android.synthetic.main.component_drinklist_card.view.*
import org.jetbrains.anko.onClick

class DrinkCardView : CardView {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.component_drinklist_card, this)
        useCompatPadding = true
    }

    var drink : Drink? = null
        set(value) {
            if (value == null) return
            field = value
            drinkCardName.text = value.name
            drinkCardIndex.text = value.index.toInt().toString()
            drinkCardPercent.text = "${value.percent.prettyPrint()}%"
            drinkCardPrice.text = "${value.price.toInt()} Ft"
            drinkCardCapacity.text = "${value.capacity.prettyPrint()} l"
        }

    fun popupAction(onClick: (View?) -> Unit) = drinkCardPopupIcon.onClick(onClick)

}