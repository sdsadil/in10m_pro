package com.in10mServiceMan.ui.activities.signup

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.in10mServiceMan.models.HomeService
import com.in10mServiceMan.models.Service
import com.in10mServiceMan.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.selected_services_custom_adapter.view.*

class OfferedServiceAdapter(var activity: Context, var selectedServiceCallback: SelectedServiceCallback?, var data: HomeService?) : RecyclerView.Adapter<OfferedServiceAdapter.ServiceVH>() {

    private var serviceList = data?.data


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceVH {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.selected_services_custom_adapter, parent, false)
        return ServiceVH(v)
    }

    override fun getItemCount(): Int {
        return this.serviceList!!.size
    }


    override fun onBindViewHolder(holder: ServiceVH, position: Int) {

        val service = this.serviceList!![position]

        holder.itemView?.offeredServiceAdapterTV?.text = service.serviceName
        Picasso.get().load(service.serviceIcon).placeholder(R.drawable.icon_plumbing).fit().into(holder.itemView?.offeredServiceAdapterIV)
        //holder.itemView?.serviceIconIV?.setImageResource(service.ServiceImage)
        var isClicked: Boolean = false
        holder.itemView.offeredServiceAdapterRadio.setOnClickListener {
            if (!isClicked) {

                holder.itemView?.offeredServiceAdapterRadio?.setImageResource(R.drawable.select_radio_one)
                isClicked = true
            } else {
                isClicked = false
                holder.itemView?.offeredServiceAdapterRadio?.setImageResource(R.drawable.unselect_radio_one)
            }
        }
    }


    inner class ServiceVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    interface SelectedServiceCallback {

        fun openSubService(item: Service)
        fun selectService(item: Service)

        fun removeSelectedService(item: Service)
    }

}