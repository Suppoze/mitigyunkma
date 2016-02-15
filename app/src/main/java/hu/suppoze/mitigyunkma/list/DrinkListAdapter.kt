package hu.suppoze.mitigyunkma.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.model.Drink
import hu.suppoze.mitigyunkma.util.ResourceHelper
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

class DrinkListAdapter(realm: Realm, sortByField: String) : RecyclerView.Adapter<DrinkListAdapter.DrinkViewHolder>() {

    val realm: Realm
    val realmDrinkDataSet: RealmResults<Drink>

    init {
        this.realm = realm
        this.realmDrinkDataSet = realm.allObjectsSorted(
                Drink::class.java,
                sortByField,
                if (sortByField == Drink::lastmod.name) Sort.DESCENDING else Sort.ASCENDING)
        realmDrinkDataSet.addChangeListener { notifyDataSetChanged() }
    }

    override fun getItemCount(): Int = realmDrinkDataSet.size
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DrinkViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.drinklist_row_card, parent, false)
        return DrinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder?, position: Int) {
        val drink = realmDrinkDataSet[position]

        holder?.drinkIndex?.text = drink.index.toInt().toString()
        holder?.drinkName?.text = drink.name
        holder?.drinkPercent?.text = drink.percent.toString()
        holder?.drinkPrice?.text = drink.price.toString()
        holder?.drinkCapacity?.text = drink.capacity.toString()
    }

    class DrinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val drinkIndex: TextView
        val drinkName: TextView
        val drinkPercent: TextView
        val drinkPrice: TextView
        val drinkCapacity: TextView

        init {
            drinkIndex = view.findViewById(R.id.drinklist_card_index) as TextView
            drinkName = view.findViewById(R.id.drinklist_card_name) as TextView
            drinkPercent = view.findViewById(R.id.drinklist_card_percent) as TextView
            drinkPrice = view.findViewById(R.id.drinklist_card_price) as TextView
            drinkCapacity = view.findViewById(R.id.drinklist_card_capacity) as TextView
        }
    }
}