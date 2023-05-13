package com.in10mServiceMan.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.signup.BottomSheatDialog
import kotlinx.android.synthetic.main.item_size_list_item.view.itemIV
import kotlinx.android.synthetic.main.row_country.view.tvCountry
import kotlinx.android.synthetic.main.row_country.view.tvRight
import java.util.ArrayList
import java.util.Locale

class CountryAdapter(
    var itemList: ArrayList<String>,
    var selectedPosition: Int = 0,
    val listner: CountrySelectionListener
) : RecyclerView.Adapter<CountryAdapter.ItemVH>() , Filterable {
    var countryFilterList = ArrayList<String>()

    init {
        countryFilterList = itemList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_country, parent, false)
        return ItemVH(v)
    }

    override fun getItemCount(): Int = countryFilterList.size

    override fun onBindViewHolder(holder: ItemVH, position: Int) {

        try {
            holder.itemView.tvCountry.text = countryFilterList[position]
            holder.itemView.tvRight.visibility =  View.INVISIBLE
            if (position == selectedPosition) {
                holder.itemView.tvRight.visibility =  View.VISIBLE
//                holder.itemView.tvRight.isSelected = true
//                holder.itemView.itemIV.setBackgroundResource(R.drawable.white_bg)
            } else {
                holder.itemView.tvRight.visibility =  View.GONE
//                holder.itemView.tvRight.isSelected = false
//                holder.itemView.itemIV.setBackgroundResource(R.drawable.grey_bg)
            }

            holder.itemView.setOnClickListener {
                selectedPosition = position
              var nameCou=  countryFilterList[position]
                holder.itemView.tvRight.visibility =  View.VISIBLE
                listner.countryListener(nameCou)
                notifyDataSetChanged()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = itemList
                } else {
                    val resultList = java.util.ArrayList<String>()
                    for (row in itemList) {
                        if (row.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as java.util.ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }


    class ItemVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {}

    interface CountrySelectionListener{

        fun countryListener(positionname: String)
    }

}