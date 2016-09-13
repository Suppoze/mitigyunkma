package hu.suppoze.mitigyunkma

import hu.suppoze.mitigyunkma.entity.Drink

data class PageChangedEvent(val page: Int)

data class EditDrinkEvent(val drink: Drink)