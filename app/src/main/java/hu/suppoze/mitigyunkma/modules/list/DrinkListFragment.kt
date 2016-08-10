package hu.suppoze.mitigyunkma.modules.list

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.`interface`.ToolbarCollapseController
import hu.suppoze.mitigyunkma.modules.base.BaseFragment
import hu.suppoze.mitigyunkma.modules.base.Navigator
import hu.suppoze.mitigyunkma.modules.calculate.CalculatePresenter
import hu.suppoze.mitigyunkma.model.Drink
import kotlinx.android.synthetic.main.fragment_drinklist.*
import io.realm.Realm
import io.realm.Sort
import org.jetbrains.anko.support.v4.dimen

class DrinkListFragment(val sortByField: String) : BaseFragment() {

    val realm: Realm
    private val DOWN: Int = 1
    private val UP: Int = 0

    lateinit var toolbarCollapseController : ToolbarCollapseController

    init {
        realm = Realm.getDefaultInstance()
    }

    constructor() : this(Drink::lastmod.name)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            toolbarCollapseController = context as ToolbarCollapseController
        } catch (ex: ClassCastException) {
            throw RuntimeException("${activity.localClassName} must implement controller")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_drinklist, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        Handler().postDelayed({ setToolbarCollapse() }, 100)
    }

    private fun setupRecyclerView() {
        drinkRecyclerView.setHasFixedSize(false)
        drinkRecyclerView.layoutManager = CollapsingHelperLinearLayoutManager(context) { setToolbarCollapse() }
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

    private fun setToolbarCollapse() {
        if (drinkRecyclerView.canScrollVertically(DOWN))
            toolbarCollapseController.turnOnToolbarScrolling()
        else
            toolbarCollapseController.turnOffToolbarScrolling()
    }

    private fun edit(drink: Drink) {
        CalculatePresenter.instance.loadDrinkForEdit(drink)
        Navigator.navigate(Navigator.Pages.CALCULATE)
    }

    private fun delete(drink: Drink) {
        realm.executeTransaction {
            drink.deleteFromRealm()
        }
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

    class CollapsingHelperLinearLayoutManager(
            context: Context?,
            val modifyCollapseStatus: () -> Unit)
    : LinearLayoutManager(context) {

        override fun onItemsAdded(recyclerView: RecyclerView?, positionStart: Int, itemCount: Int) {
            super.onItemsAdded(recyclerView, positionStart, itemCount)
            modifyCollapseStatus()
        }

        override fun onItemsRemoved(recyclerView: RecyclerView?, positionStart: Int, itemCount: Int) {
            super.onItemsRemoved(recyclerView, positionStart, itemCount)
            modifyCollapseStatus()
        }

        override fun onItemsChanged(recyclerView: RecyclerView?) {
            super.onItemsChanged(recyclerView)
            modifyCollapseStatus()
        }
    }
}
