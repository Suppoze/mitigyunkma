package hu.suppoze.mitigyunkma.list

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import org.jetbrains.anko.support.v4.dimen

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
        drinkRecyclerView.addItemDecoration(DrinkListItemDecoration(
                dimen(R.dimen.fragment_padding),
                dimen(R.dimen.drinklist_spacing),
                dimen(R.dimen.drinklist_first_and_last_padding_vertical)))

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

class DrinkListItemDecoration(
        val horizontalPadding: Int,
        val verticalSpacing: Int,
        val firstAndLastVerticalPadding: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        if (parent == null || outRect == null || state == null)
            return

        outRect.left = horizontalPadding
        outRect.right = horizontalPadding
        outRect.bottom = verticalSpacing
        outRect.top = verticalSpacing

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = firstAndLastVerticalPadding
        }

        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.bottom = firstAndLastVerticalPadding
        }
    }
}
