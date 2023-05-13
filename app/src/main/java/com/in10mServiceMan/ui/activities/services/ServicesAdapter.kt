package com.in10mServiceMan.ui.activities.services

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.in10mServiceMan.models.HomeService
import com.in10mServiceMan.models.Service
import com.in10mServiceMan.R

import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.SharedPreferencesHelper.getBoolean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_services.view.*


class ServicesAdapter(
    var activity: Context,
    var selectedServiceCallback: SelectedServiceCallback?,
    var data: HomeService?
) : RecyclerView.Adapter<ServicesAdapter.ServiceVH>() {

    private var serviceList = data?.data


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_services, parent, false)
        return ServiceVH(v)
    }

    override fun getItemCount(): Int {
        return this.serviceList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ServiceVH, position: Int) {

        val service = this.serviceList!![position]
        /* if (service.serviceName.length > 35) {
             val text = service.serviceName.substring(0, 35) + "..."
             holder.itemView.tvItem?.text = text
         } else {
             holder.itemView.tvItem?.text = service.serviceName
         }*/

        val serviceName: String
        val isLangArabic = getBoolean(
            activity,
            Constants.SharedPrefs.User.IS_LANG_ARB, false
        )!!
        serviceName = if (isLangArabic) {
            if (!service.serviceArName.equals("") && !service.serviceArName.equals(null) && !service.serviceArName.equals(
                    "null"
                )
            ) {
                service.serviceArName.trim()
            } else
                ""
        } else {
            service.serviceName.trim()
        }

        holder.itemView.tvItem?.text = serviceName

        holder.itemView.colorView?.setBackgroundColor(Color.parseColor(service.serviceColor))
/*
"http://"+ <--- this was added before for getting service icon.
*/
        Picasso.get().load(service.serviceIcon).placeholder(R.drawable.icon_plumbing).fit()
            .into(holder.itemView?.serviceIconIV)
        //holder.itemView?.serviceIconIV?.setImageResource(service.ServiceImage)
        var isClicked: Boolean = false
        holder.itemView.setOnClickListener {
            if (!isClicked) {
                selectedServiceCallback?.selectService(service)
                holder.itemView?.tvItem?.setTextColor(Color.parseColor("#000000"))
                //holder.itemView?.ad_bg.setBackgroundColor(Color.parseColor(service.serviceColor))
                holder.itemView?.cons_bg!!.setBackgroundColor(Color.parseColor(service.serviceColor))
                //holder.itemView?.ad_bg.alpha = 0.3f
                holder.itemView?.cons_bg!!.alpha = 0.3f
                isClicked = true
            } else {
                isClicked = false
                selectedServiceCallback?.removeSelectedService(service)
                holder.itemView?.tvItem?.setTextColor(Color.parseColor("#7F8081"))
                //holder.itemView?.ad_bg.setBackgroundColor(Color.parseColor("#ffffff"))
                holder.itemView?.cons_bg!!.setBackgroundColor(Color.parseColor("#ffffff"))
            }
        }
    }


    class ServiceVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    interface SelectedServiceCallback {

        fun openSubService(item: Service)
        fun selectService(item: Service)

        fun removeSelectedService(item: Service)
    }

}