package hu.suppoze.mitigyunkma.list

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.base.BaseFragment
import hu.suppoze.mitigyunkma.base.Navigator
import hu.suppoze.mitigyunkma.calculate.CalculatePresenter
import hu.suppoze.mitigyunkma.model.Drink
import kotlinx.android.synthetic.main.fragment_drinklist.*
import io.realm.Realm
import io.realm.Sort

class DrinkListFragment(val sortByField: String) : BaseFragment() {

    val realm: Realm

    init {
        realm = Realm.getDefaultInstance()
    }

    constructor() : this(Drink::lastmod.name)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_drinklist, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drinkRecyclerView.setHasFixedSize(true)
        drinkRecyclerView.layoutManager = LinearLayoutManager(context)
        drinkRecyclerView.itemAnimator = DefaultItemAnimator()

        val realmResultDataSet = realm.where(Drink::class.java)
                .findAllSorted(
                        sortByField,
                        if (sortByField == Drink::lastmod.name) Sort.DESCENDING else Sort.ASCENDING)

        drinkRecyclerView.adapter = DrinkListAdapter(realmResultDataSet, { edit(it) }, { delete(it) })
    }

    private fun edit(drink: Drink) {
        CalculatePresenter.instance.editDrink(drink)
        Navigator.navigate(Navigator.Pages.CALCULATE)
    }

    private fun delete(drink: Drink) {
        realm.executeTransaction {
            drink.deleteFromRealm()
        }
    }
}
