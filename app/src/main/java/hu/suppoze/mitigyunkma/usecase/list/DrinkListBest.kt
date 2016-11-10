package hu.suppoze.mitigyunkma.usecase.list

import hu.suppoze.mitigyunkma.entity.Drink

class DrinkListBest : AbstractDrinkList(), DrinkListView {

    override fun getDrinkList(): List<Drink> {
        return presenter.getBestDrinks()
    }

}