package com.in10mServiceMan.ui.activities.home

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.in10mServiceMan.R
import com.in10mServiceMan.db.Dummy
import com.in10mServiceMan.db.ServiceMan
import kotlinx.android.synthetic.main.item_serviceman_home.view.*


class SelectServiceManAdapter(var activity: Activity, var serviceManList: List<ServiceMan> = Dummy.getServiceManList(), var selectedCallBack:SelectServiceManCallBack?) : RecyclerView.Adapter<SelectServiceManAdapter.SelectServiceManVH>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectServiceManVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_serviceman_home, parent, false)
        return SelectServiceManVH(v)
    }

    override fun getItemCount(): Int = serviceManList.size

    override fun onBindViewHolder(holder: SelectServiceManVH, position: Int) {

        holder.itemView.serviceManIV.setImageResource(serviceManList[position].Image)

        holder.itemView.ServiceManName.text = serviceManList[position].Name

        holder.itemView.ratingTV.text = serviceManList[position].Rating.toString()

        holder.itemView.titleTV.text = serviceManList[position].Title

        holder.itemView.addressTV.text = serviceManList[position].Address
        holder.itemView.timeTV.text = serviceManList[position].ETA.toString()+"mins"
        holder.itemView.distanceTV.text = serviceManList[position].Distance.toString() +"km away"

//        if (position == selectedPosition) {
//
//            holder.itemView.carIV.isSelected = true
//            holder.itemView.carIV.setBackgroundResource(R.drawable.white_bg)
//            holder.itemView.carNameTV.setTextColor(ContextCompat.getColor(in10mApplication.instance!!.baseContext, R.color.darkText))
//        } else {
//            holder.itemView.carIV.isSelected = false
//            holder.itemView.carIV.setBackgroundResource(R.drawable.grey_bg)
//            holder.itemView.carNameTV.setTextColor(ContextCompat.getColor(in10mApplication.instance!!.baseContext, R.color.normalGreyText))
//        }

        holder.itemView.setOnClickListener {
//            selectedPosition = position
//            notifyDataSetChanged()
            selectedCallBack!!.selected(serviceManList[position])
        }


    }


    inner class SelectServiceManVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {}

    interface SelectServiceManCallBack{
        fun selected(serviceMan:ServiceMan)
    }
}