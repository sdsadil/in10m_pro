package com.in10mServiceMan.ui.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.in10mServiceMan.models.Service
import com.in10mServiceMan.models.viewmodels.CompanyServiceman
import com.in10mServiceMan.models.viewmodels.CompanyServices
import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.company_serviceman_details.view.*

class CompanyServimanDetailsAdapter(var activity: Context,
                                    var selectedServiceCallback: SelectedServiceCallback?,
                                    var data: ArrayList<CompanyServices>,
                                    var mCallBack : setFullChanges)
    : RecyclerView.Adapter<CompanyServimanDetailsAdapter.ServiceVH>(),
        CompanyServicemanDetailsEntryAdapter.setChanges {

    @SuppressLint("LogNotTimber")
    override fun setUserDetails(datas: List<CompanyServiceman>, pos: Int) {
        data[pos].users = datas
    }

    private var listCompanyServices: ArrayList<CompanyServices> = data
    private var mAdapter: CompanyServicemanDetailsEntryAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceVH {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.company_serviceman_details, parent, false)
        return ServiceVH(v)
    }

    override fun getItemCount(): Int {
        return this.listCompanyServices!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ServiceVH, position: Int) {
        val service = this.listCompanyServices[position]
        holder.itemView.companyServiceNameTV?.text = service.serviceName
        val mDrawable = ContextCompat.getDrawable(activity, R.drawable.r_circle)!!.constantState!!.newDrawable().mutate()//holder.ImageViewColor.getDrawable();
        mDrawable.colorFilter = PorterDuffColorFilter(Color.parseColor(service.serviceColor), PorterDuff.Mode.SRC_IN)
        holder.itemView.companyServiceIconIV?.setImageDrawable(mDrawable)
        listCompanyServices[position].users.add(CompanyServiceman("", "", "", "", position))
        holder.itemView.CSManEntryDetailsRV?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mAdapter = CompanyServicemanDetailsEntryAdapter(activity, listCompanyServices[position].users, position, this)
        holder.itemView.CSManEntryDetailsRV?.adapter = mAdapter
        holder.itemView.companyServicemanAddUserTV?.setOnClickListener {
            listCompanyServices[position].users.add(CompanyServiceman("", "", "", "", position))
            mAdapter = CompanyServicemanDetailsEntryAdapter(activity, listCompanyServices[position].users, position, this)
            holder.itemView.CSManEntryDetailsRV?.adapter = mAdapter
            mAdapter!!.notifyItemInserted(listCompanyServices[position].users.size - 1)
        }
    }

    inner class ServiceVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    interface SelectedServiceCallback {
        fun openSubService(item: Service)
        fun selectService(item: Service)
        fun removeSelectedService(item: Service)
    }

    interface setFullChanges {
        fun setUserDetails(data: ArrayList<CompanyServices>)
    }

    fun onClick(){
        mCallBack.setUserDetails(data)
    }
}