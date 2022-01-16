package com.in10mServiceMan.ui.activities.select_location

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.in10mServiceMan.R

class SelectLocationAdapter(var activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val selectLocationType = 0
    private val selectLocationMap = 1
    private val enterLocation = 2

    private var selectLocationCallbacks: SelectLocationCallbacks = activity as SelectLocationCallbacks


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            selectLocationMap -> SelectLocationMapVH(LayoutInflater.from(parent.context).inflate(R.layout.select_location_map, parent, false))
            enterLocation -> EnterLocationVH(LayoutInflater.from(parent.context).inflate(R.layout.enter_location_item, parent, false))
            else -> SelectLocationVH(LayoutInflater.from(parent.context).inflate(R.layout.select_location_item, parent, false))
        }
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {

            when (holder) {

                is EnterLocationVH -> {
                    selectLocationCallbacks.onEnterLocationSelected()
                }

                is SelectLocationVH -> {
                    selectLocationCallbacks.onLocationSelected()
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {

        return when (position) {

            itemCount - 1 -> {
                enterLocation
            }

            itemCount - 2 -> {
                selectLocationMap
            }

            else -> selectLocationType

        }
    }

    inner class SelectLocationVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {}

    inner class SelectLocationMapVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {}

    inner class EnterLocationVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {}

    interface SelectLocationCallbacks {

        fun onEnterLocationSelected()

        fun onLocationSelected()
    }
}