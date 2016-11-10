package hu.suppoze.mitigyunkma.usecase.list

import hu.suppoze.mitigyunkma.entity.Drink

class DrinkListHistory() : AbstractDrinkList(), DrinkListView {

    override fun getDrinkList(): List<Drink> {
        return presenter.getDrinksHistory()
    }

}
