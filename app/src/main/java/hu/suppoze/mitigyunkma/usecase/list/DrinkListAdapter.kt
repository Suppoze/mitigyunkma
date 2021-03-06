package hu.suppoze.mitigyunkma.usecase.list

import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.entity.Drink
import hu.suppoze.mitigyunkma.extension.showPopup
import io.realm.RealmChangeListener
import io.realm.RealmResults

class DrinkListAdapter(
        private val realmDrinkDataSet: RealmResults<Drink>,
        private val editAction: (Drink) -> Unit,
        private val deleteAction: (Drink) -> Unit) :
        RecyclerView.Adapter<DrinkListAdapter.DrinkViewHolder>() {

    private var cachedDrinkNameList: List<String>

    init {
        cachedDrinkNameList = realmDrinkDataSet.map { it.name }

        // TODO Realm might provide better way of doing this
        realmDrinkDataSet.addChangeListener(RealmChangeListener { cachedListComparatorListener() })
    }

    private fun cachedListComparatorListener() {
        when {
            cachedDrinkNameList.count() > realmDrinkDataSet.count() -> findDeletedIndex()
            cachedDrinkNameList.count() < realmDrinkDataSet.count() -> findAddedIndex()
            else -> notifyDataSetChanged()
        }
        cachedDrinkNameList = realmDrinkDataSet.map { it.name }
    }

    private fun findDeletedIndex() {
        realmDrinkDataSet.forEachIndexed { i, drink ->
            if (cachedDrinkNameList[i] != drink.name) {
                notifyItemRemoved(i)
                notifyDataSetChangedDelayed()
                return
            }
        }
        notifyItemRemoved(realmDrinkDataSet.count())
        notifyDataSetChangedDelayed()
    }

    private fun findAddedIndex() {
        cachedDrinkNameList.forEachIndexed { i, drinkName ->
            if (realmDrinkDataSet[i]?.name != drinkName) {
                notifyItemInserted(i)
                notifyDataSetChangedDelayed()
                return
            }
        }
        notifyItemInserted(cachedDrinkNameList.count())
        notifyDataSetChangedDelayed()
    }

    private fun notifyDataSetChangedDelayed() {
        Handler().postDelayed({ notifyDataSetChanged() }, 500)
    }

    override fun getItemCount(): Int = realmDrinkDataSet.size

    override fun getItemId(position: Int): Long =
            realmDrinkDataSet[position]?.index?.toLong() ?: throw IndexOutOfBoundsException()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DrinkViewHolder? {
        val view = DrinkCardView(parent!!.context)
        return DrinkViewHolder(view, { optionsIcon, drink ->
            optionsIcon.showPopup(R.menu.drinklist_row_menu) {
                menuItem ->
                when (menuItem.itemId) {
                    R.id.drinklist_row_menu_edit -> editAction(drink)
                    R.id.drinklist_row_menu_delete -> deleteAction(drink)
                }
                false
            }
        })
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) = holder.bindDrink(realmDrinkDataSet[position])

    class DrinkViewHolder(view: View, private val optionsClick: (View, Drink) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bindDrink(drink: Drink?) {
            if (drink == null) return
            itemView as DrinkCardView
            itemView.drink = drink
            itemView.popupAction { optionsClick(it!!, drink) }
        }
    }
}