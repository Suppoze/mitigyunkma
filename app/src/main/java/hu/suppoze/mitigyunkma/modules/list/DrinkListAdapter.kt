package hu.suppoze.mitigyunkma.modules.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.extensions.prettyPrint
import hu.suppoze.mitigyunkma.extensions.showPopup
import hu.suppoze.mitigyunkma.model.Drink
import io.realm.RealmResults
import kotlinx.android.synthetic.main.drinklist_row_card.view.*
import org.jetbrains.anko.onClick

class DrinkListAdapter(
        val realmDrinkDataSet: RealmResults<Drink>,
        val editAction: (Drink) -> Unit,
        val deleteAction: (Drink) -> Unit) :
            RecyclerView.Adapter<DrinkListAdapter.DrinkViewHolder>() {

    var cachedDrinkNameList: List<String>

    init {
        cachedDrinkNameList = realmDrinkDataSet.map { it.name }
        realmDrinkDataSet.addChangeListener { cachedListComparatorListener() }
    }

    private fun cachedListComparatorListener() {
        if (cachedDrinkNameList.count() > realmDrinkDataSet.count()) {
            findDeletedIndex()
        }
        else if (cachedDrinkNameList.count() < realmDrinkDataSet.count()) {
            findAddedIndex()
        }
        cachedDrinkNameList = realmDrinkDataSet.map { it.name }
    }

    private fun findDeletedIndex() {
        realmDrinkDataSet.forEachIndexed { i, drink ->
            if (!cachedDrinkNameList[i].equals(drink.name)) {
                notifyItemRemoved(i)
                return
            }
        }
        notifyItemRemoved(realmDrinkDataSet.count())
    }

    private fun findAddedIndex() {
        cachedDrinkNameList.forEachIndexed { i, drinkName ->
            if (!realmDrinkDataSet[i].name.equals(drinkName)) {
                notifyItemInserted(i)
                return
            }
        }
        notifyItemInserted(cachedDrinkNameList.count())
    }

    override fun getItemCount(): Int = realmDrinkDataSet.size

    override fun getItemId(position: Int): Long = realmDrinkDataSet[position].index.toLong()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DrinkViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.drinklist_row_card, parent, false)
        return DrinkViewHolder(view, { optionsIcon, drink ->
            optionsIcon.showPopup(R.menu.drinklist_row_menu) {
                menuItem ->
                    when (menuItem.itemId) {
                        R.id.drinklist_row_menu_edit -> editAction(drink)
                        R.id.drinklist_row_menu_delete -> deleteAction(drink)
                    }
                    true
        }})
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) = holder.bindDrink(realmDrinkDataSet[position])

    class DrinkViewHolder(view: View, val optionsClick: (View, Drink) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bindDrink(drink: Drink) {
            itemView.drinkCardIndex.text = "${drink.index.toInt()}"
            itemView.drinkCardName.text = drink.name
            itemView.drinkCardPercent.text = "${drink.percent.prettyPrint()}%"
            itemView.drinkCardPrice.text = "${drink.price.toInt()} Ft"  // TODO: manage currencies
            itemView.drinkCardCapacity.text = "${drink.capacity.prettyPrint()} l"
            itemView.drinkCardPopupIcon.onClick { optionsClick(it!!, drink) }
        }
    }
}