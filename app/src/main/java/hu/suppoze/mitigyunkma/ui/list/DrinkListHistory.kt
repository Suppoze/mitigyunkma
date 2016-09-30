package hu.suppoze.mitigyunkma.ui.list

import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.entity.Drink

class DrinkListHistory() : AbstractDrinkList(), DrinkListView {

    override fun getDrinkList(): List<Drink> {
        return presenter.getDrinks(Drink::lastmod.name)
    }

    override fun getTitle(): CharSequence = getString(R.string.history_view_title)

}
