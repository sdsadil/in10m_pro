package com.in10mServiceMan.ui.activities.on_going_trips

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.in10mServiceMan.R

class OnGoingTripsAdapter  : RecyclerView.Adapter<OnGoingTripsAdapter.OnGoingTripsVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnGoingTripsVH {
       val v= LayoutInflater.from(parent.context).inflate(R.layout.on_going_trips_list_item,parent,false)
        return OnGoingTripsVH(v)
    }

    override fun getItemCount(): Int = 8

    override fun onBindViewHolder(holder: OnGoingTripsVH, position: Int) {
    }


    inner class OnGoingTripsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {}
}