package com.in10mServiceMan.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.in10mServiceMan.models.viewmodels.CompanyServiceman
import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.custom_layout_serviceman_details_entry.view.*


class CompanyServicemanDetailsEntryAdapter(var activity: Context,
                                           var data: List<CompanyServiceman>,
                                           var parentPosition: Int,
                                           var callBack: setChanges)
    : RecyclerView.Adapter<CompanyServicemanDetailsEntryAdapter.ServiceVH>() {

    private var serviceList = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceVH {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.custom_layout_serviceman_details_entry, parent, false)
        return ServiceVH(v)
    }

    override fun getItemCount(): Int {
        return this.serviceList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ServiceVH, position: Int) {

        val service = this.serviceList[position]
        holder.itemView?.companyServicemanFullNameET?.setText(service.name.toString())
        holder.itemView?.companyServicemanMobileET?.setText(service.mobile.toString())
        holder.itemView?.companyServicemanEmailET?.setText(service.email.toString())
        holder.itemView?.companyServicemanExperienceET?.setText(service.experience.toString())
        holder.itemView?.companyServicemanFullNameET?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                serviceList[position].name = s.toString()
                callBack.setUserDetails(serviceList, parentPosition)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        holder.itemView?.companyServicemanMobileET?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                serviceList[position].mobile = s.toString()
                callBack.setUserDetails(serviceList, parentPosition)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        holder.itemView?.companyServicemanExperienceET?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                serviceList[position].experience = s.toString()
                callBack.setUserDetails(serviceList, parentPosition)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        holder.itemView?.companyServicemanEmailET?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                serviceList[position].email = s.toString()
                callBack.setUserDetails(serviceList, parentPosition)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    inner class ServiceVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    interface setChanges {
        fun setUserDetails(datas: List<CompanyServiceman>, pos: Int)
    }
}