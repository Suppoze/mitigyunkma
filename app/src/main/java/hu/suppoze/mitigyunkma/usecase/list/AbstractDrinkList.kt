package hu.suppoze.mitigyunkma.usecase.list

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.entity.Drink
import hu.suppoze.mitigyunkma.usecase.MainPagerAdapter
import hu.suppoze.mitigyunkma.usecase.NavigateToPageEvent
import hu.suppoze.mitigyunkma.usecase.common.BaseFragment
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_drinklist.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.support.v4.dimen

abstract class AbstractDrinkList : BaseFragment<DrinkListPresenter, DrinkListView>(), DrinkListView {

    abstract fun getDrinkList(): List<Drink>

    override fun providePresenter(): DrinkListPresenter {
        return DrinkListPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_drinklist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        drinkRecyclerView.setHasFixedSize(false)
        drinkRecyclerView.layoutManager = LinearLayoutManager(context)
        drinkRecyclerView.itemAnimator = DefaultItemAnimator()

        drinkRecyclerView.addItemDecoration(DrinkListItemDecoration(
                dimen(R.dimen.fragment_padding),
                dimen(R.dimen.drinklist_spacing),
                dimen(R.dimen.drinklist_first_and_last_padding_vertical)))

        val drinkList = getDrinkList()

        drinkRecyclerView.adapter = DrinkListAdapter(drinkList as RealmResults<Drink>, { edit(it) }, { delete(it) })
    }

    private fun edit(drink: Drink) {
        presenter.editDrink(drink)
        EventBus.getDefault().post(NavigateToPageEvent(MainPagerAdapter.Page.CALCULATE))
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