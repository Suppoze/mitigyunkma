package hu.suppoze.mitigyunkma.list

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.Bind
import butterknife.ButterKnife
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.base.BaseFragment
import hu.suppoze.mitigyunkma.model.Drink
import io.realm.Realm

class DrinkListFragment(sortByField: String) : BaseFragment() {

    @Bind(R.id.drinklist_view_recycler_view) lateinit var recyclerView: RecyclerView

    val sortByField: String

    init {
        this.sortByField = sortByField
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_drinklist, container, false)
        ButterKnife.bind(this, view)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = DrinkListAdapter(Realm.getDefaultInstance(), sortByField)
        recyclerView.itemAnimator = DefaultItemAnimator()

        return view;
    }
}
