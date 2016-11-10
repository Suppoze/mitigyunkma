package hu.suppoze.mitigyunkma.usecase

import hu.suppoze.mitigyunkma.entity.Drink

data class NavigateToPageEvent(val page: MainPagerAdapter.Page, val customTitle: String? = null)

data class EditDrinkEvent(val drink: Drink)