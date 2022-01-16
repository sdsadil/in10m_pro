package com.in10mServiceMan.ui.activities.sub_services

import android.app.Activity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.in10mServiceMan.Models.SubService
import com.in10mServiceMan.R


class SubServicesAdapter(var activity: Activity, var selectedSubServiceCallback: SelectedSubServiceCallback?,
                         var subService: MutableList<SubService>): RecyclerView.Adapter<SubServicesAdapter.SubServiceVH>() {

    private var subServiceList= subService
    private var selectSubServiceIndex=intArrayOf(0).toCollection(ArrayList())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubServiceVH {

        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_sub_service, parent, false)
        return SubServiceVH(v)
    }

    override fun getItemCount(): Int {
        return this.subServiceList.count()
    }

    override fun onBindViewHolder(holder: SubServiceVH, position: Int) {

        val subService= this.subServiceList.get(position);
        holder.itemView?.findViewById<TextView>(R.id.subServiceTV)?.text = subService.name
        holder.itemView?.findViewById<TextView>(R.id.subServiceTV2)?.text = subService.name

        if(selectSubServiceIndex.contains(position))
        {
            holder.itemView.findViewById<CardView>(R.id.selectedCard).visibility=View.VISIBLE
            holder.itemView.findViewById<ConstraintLayout>(R.id.defaultConstraintLayout).visibility=View.GONE

        }else{

            holder.itemView.findViewById<ConstraintLayout>(R.id.defaultConstraintLayout).visibility=View.VISIBLE
            holder.itemView.findViewById<CardView>(R.id.selectedCard).visibility=View.GONE
        }

        holder.itemView.setOnClickListener {
            if(selectSubServiceIndex.contains(position))
            {
                selectSubServiceIndex.remove(position)
            }
            else{
                selectSubServiceIndex.add(position)
            }

            selectedSubServiceCallback?.ChoosenSubService(selectSubServiceIndex)

            notifyDataSetChanged()

        }
    }


    inner class SubServiceVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {}


    interface SelectedSubServiceCallback {

        fun ChoosenSubService(selectSubServiceIndex: ArrayList<Int>)
    }
}