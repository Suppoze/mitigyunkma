package hu.suppoze.mitigyunkma.ui.list

import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.entity.Drink

class DrinkListBest : AbstractDrinkList(), DrinkListView {

    override fun getDrinkList(): List<Drink> {
        return presenter.getRealmResultDataSet(Drink::index.name)
    }

    override fun getTitle(): CharSequence {
        return getString(R.string.bestof_view_title)
    }

}