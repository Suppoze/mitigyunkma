package hu.suppoze.mitigyunkma.list

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.base.BaseFragment
import hu.suppoze.mitigyunkma.model.Drink
import kotlinx.android.synthetic.main.fragment_drinklist.*
import io.realm.Realm

class DrinkListFragment(sortByField: String) : BaseFragment() {

    constructor() : this(Drink::lastmod.name)

    val sortByField: String

    init {
        this.sortByField = sortByField
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_drinklist, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drinkRecyclerView.setHasFixedSize(true)
        drinkRecyclerView.layoutManager = LinearLayoutManager(context)
        drinkRecyclerView.adapter = DrinkListAdapter(Realm.getDefaultInstance(), sortByField)
        drinkRecyclerView.itemAnimator = DefaultItemAnimator()
    }
}
