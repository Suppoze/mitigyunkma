package hu.suppoze.mitigyunkma.di

import dagger.Component
import hu.suppoze.mitigyunkma.usecase.calculate.CalculatePresenter
import hu.suppoze.mitigyunkma.usecase.list.DrinkListPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(calculatePresenter: CalculatePresenter)

    fun inject(drinkListPresenter: DrinkListPresenter)

}
