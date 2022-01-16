package com.in10mServiceMan.ui.activities.item_details

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.in10mServiceMan.R
import com.in10mServiceMan.db.Dummy
import com.in10mServiceMan.db.ItemSize
import kotlinx.android.synthetic.main.item_size_list_item.view.*

class ItemAdapter(var itemList: List<ItemSize> = Dummy.getItemSizeList(),
                  var selectedPosition: Int = 0) : RecyclerView.Adapter<ItemAdapter.ItemVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_size_list_item, parent, false)
        return ItemVH(v)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemVH, position: Int) {

        holder.itemView.itemIV.setImageResource(itemList[position].itemImage)

        holder.itemView.nameTV.text = itemList[position].itemName

        if (position == selectedPosition) {
            holder.itemView.itemIV.isSelected = true
            holder.itemView.itemIV.setBackgroundResource(R.drawable.white_bg)
        } else {
            holder.itemView.itemIV.isSelected = false
            holder.itemView.itemIV.setBackgroundResource(R.drawable.grey_bg)
        }

        holder.itemView.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
        }
    }


    class ItemVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {}

}