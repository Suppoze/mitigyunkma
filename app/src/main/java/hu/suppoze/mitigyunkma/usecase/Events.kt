package hu.suppoze.mitigyunkma.usecase

import hu.suppoze.mitigyunkma.entity.Drink

data class NavigateToPageEvent(val page: MainPagerAdapter.Page)

data class EditDrinkEvent(val drink: Drink)