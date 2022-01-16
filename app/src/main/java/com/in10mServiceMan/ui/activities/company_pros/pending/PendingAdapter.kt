package com.in10mServiceMan.ui.activities.company_pros.pending

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.company_pros.active.active_API.ActiveData
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.card_ongoing_service.view.*

class PendingAdapter(val mList: List<ActiveData>, val context: Context, val v: PendingInterface): RecyclerView.Adapter<PendingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.card_ongoing_service, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.itemview.acButton.text = "DELETE"
        Glide.with(context).load(mList[p1].pro_image).placeholder(R.drawable.user_dummy_avatar).into(p0.itemView.iv_profile_pic_ongoing)
        p0.itemview.tv_status_ongoing.text = mList[p1].name
        p0.itemview.tv_status.text = mList[p1].earnings.toString()

        if (mList[p1].work_status == 1)
            p0.itemview.tv_subStatus_ongoing.text = "Status: Online"
        else
            p0.itemview.tv_subStatus_ongoing.text = "Status: Offline"

        p0.itemview.clickLayout.setOnClickListener {
            SharedPreferencesHelper.putInt(context, Constants.SharedPrefs.User.COMMON_ID, mList[p1].id)
            v.pendingTransaction(mList[p1].id)
        }

        p0.itemview.tv_status.setOnClickListener {
            SharedPreferencesHelper.putInt(context, Constants.SharedPrefs.User.COMMON_ID, mList[p1].id)
            v.pendingEarningsTransaction()
        }

        p0.itemview.acButton.setOnClickListener {
            val compId = SharedPreferencesHelper.getInt(context, Constants.SharedPrefs.User.PERSON_COMPANY_NAME, 0)
            v.delete(compId, mList[p1].id)
        }
    }

    class ViewHolder(var itemview: View) : RecyclerView.ViewHolder(itemview)
}