package hu.suppoze.mitigyunkma.ui.list

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.ui.base.Navigator
import hu.suppoze.mitigyunkma.entity.Drink
import hu.suppoze.mitigyunkma.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_drinklist.*
import org.jetbrains.anko.support.v4.dimen

// TODO: remove sortByField, make lists inherit from a base fragment
class DrinkListFragment(val sortByField: String) : BaseFragment<DrinkListPresenter, DrinkListView>(), DrinkListView {

    constructor() : this(Drink::lastmod.name)

    override fun providePresenter(): DrinkListPresenter {
        return DrinkListPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_drinklist, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun getTitle(): CharSequence = getString(if (sortByField == Drink::lastmod.name) R.string.history_view_title else R.string.bestof_view_title)

    private fun setupRecyclerView() {
        drinkRecyclerView.setHasFixedSize(false)
        drinkRecyclerView.layoutManager = LinearLayoutManager(context)
        drinkRecyclerView.itemAnimator = DefaultItemAnimator()

        drinkRecyclerView.addItemDecoration(DrinkListItemDecoration(
                dimen(R.dimen.fragment_padding),
                dimen(R.dimen.drinklist_spacing),
                dimen(R.dimen.drinklist_first_and_last_padding_vertical)))

        val realmResultDataSet = presenter.getRealmResultDataSet(sortByField)

        drinkRecyclerView.adapter = DrinkListAdapter(realmResultDataSet, { edit(it) }, { delete(it) })
    }

    private fun edit(drink: Drink) {
        // TODO: do it some other way.
        // CalculatePresenter.instance.loadDrinkForEdit(drink)
        Navigator.navigate(Navigator.Pages.CALCULATE)
    }

    private fun delete(drink: Drink) {
        presenter.deleteDrink(drink)
    }

    class DrinkListItemDecoration(
            val horizontalPadding: Int,
            val verticalSpacing: Int,
            val firstAndLastVerticalPadding: Int
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            if (parent == null || outRect == null || state == null || view == null)
                return

            outRect.left = horizontalPadding
            outRect.right = horizontalPadding
            outRect.bottom = verticalSpacing
            outRect.top = verticalSpacing

            if ((view.layoutParams as RecyclerView.LayoutParams).isItemRemoved && parent.getChildLayoutPosition(view) == 0) {
                outRect.top = firstAndLastVerticalPadding
            } else if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = firstAndLastVerticalPadding
            }

            if (parent.getChildLayoutPosition(view) == state.itemCount - 1) {
                outRect.bottom = firstAndLastVerticalPadding
            }
        }
    }
}
