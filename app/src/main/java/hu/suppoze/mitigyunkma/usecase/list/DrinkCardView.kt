package hu.suppoze.mitigyunkma.usecase.list

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.entity.Drink
import hu.suppoze.mitigyunkma.extension.prettyPrint
import hu.suppoze.mitigyunkma.extension.setTextColorBasedOnRating
import kotlinx.android.synthetic.main.component_drinklist_card.view.*

class DrinkCardView : CardView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.component_drinklist_card, this)
        useCompatPadding = false
    }

    var drink: Drink? = null
        set(value) {
            if (value == null) return
            field = value
            drinkCardName.text = value.name
            drinkCardIndex.text = value.index.toInt().toString()
            drinkCardIndex.setTextColorBasedOnRating(value.rating, context)
            drinkCardPercent.text = "${value.percent.prettyPrint()}%"
            drinkCardPrice.text = "${value.price.toInt()} Ft"
            drinkCardCapacity.text = "${value.capacity.prettyPrint()} l"
        }

    fun popupAction(onClick: (View?) -> Unit) = drinkCardPopupIcon.setOnClickListener(onClick)
}