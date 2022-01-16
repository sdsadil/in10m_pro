package com.in10mServiceMan.ui.activities.company_pros.pending


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.in10m.ui.activities.BaseFragment

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.company_pros.CompanyProsDetails
import com.in10mServiceMan.ui.activities.company_pros.IAResponse
import com.in10mServiceMan.ui.activities.company_pros.active.active_API.ActiveData
import com.in10mServiceMan.ui.activities.company_pros.active.active_API.ActivePresenter
import com.in10mServiceMan.ui.activities.company_pros.active.active_API.ActiveResponse
import com.in10mServiceMan.ui.activities.company_pros.active.active_API.IActiveView
import com.in10mServiceMan.ui.activities.company_pros.delete_API.DeletePresenter
import com.in10mServiceMan.ui.activities.company_pros.delete_API.IDeleteView
import com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API.IServicemanDetailsView
import com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API.ServicemanDetailsPresenter
import com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API.ServicemanDetailsResponse
import com.in10mServiceMan.ui.activities.earnings.EarningsActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_pending.view.*

/**
 * A simple [Fragment] subclass.
 */
class Pending : BaseFragment(), PendingInterface, IActiveView, IServicemanDetailsView, IDeleteView {
    override fun onDeleteCompleted(mPost: IAResponse) {
        destroyDialog()
        if (mPost.status == 1)  {
            Toast.makeText(activity, mPost.message, Toast.LENGTH_LONG).show()
            showProgressDialog("")
            val compId = SharedPreferencesHelper.getInt(activity, Constants.SharedPrefs.User.PERSON_COMPANY_NAME, 2)
            mActivePresenter.active(compId, 0)
        }
        else    {
            Toast.makeText(activity, mPost.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDeleteFailed(msg: String) {
        destroyDialog()
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onServicemanDetailsCompleted(mPost: ServicemanDetailsResponse) {
        destroyDialog()
        if (mPost.status == 1)  {
            if (mPost.data.isEmpty())   {
                Toast.makeText(activity, mPost.message, Toast.LENGTH_SHORT).show()
            }
            else    {
                openActivity()
            }
        }
        else    {
            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onServicemanDetailsFailed(msg: String) {
        destroyDialog()
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun pendingTransaction(servicemanId: Int) {
        showProgressDialog("")
        mServicemanDetailsPresenter.servicemanDetails(servicemanId)
    }

    override fun pendingEarningsTransaction() {
        Constants.GlobalSettings.fromIA = true
        openActivityEarnings()
    }

    override fun delete(companyId: Int, companyProId: Int) {
        showProgressDialog("")
        mDeletePresenter.delete(companyId, companyProId)
    }

    override fun onActiveCompleted(mPost: ActiveResponse) {
        destroyDialog()
        if (mPost.status == 1)  {
            recyclerView(mPost.data)
        }
        else    {
            view!!.data_PA.visibility = View.VISIBLE
        }
    }

    override fun onActiveFailed(msg: String) {
        destroyDialog()
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    var mActivePresenter = ActivePresenter(this)
    var mDeletePresenter = DeletePresenter(this)
    var mServicemanDetailsPresenter = ServicemanDetailsPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pending, container, false)

        val compId = SharedPreferencesHelper.getInt(activity, Constants.SharedPrefs.User.PERSON_COMPANY_NAME, 0)
        mActivePresenter.active(compId, 2)

        return view
    }

    fun recyclerView(mData: List<ActiveData>)  {
        view!!.recyclerView_PA.layoutManager = LinearLayoutManager(activity)
        view!!.recyclerView_PA.adapter = PendingAdapter(mData, activity!!, this)
    }

    fun openActivity() {
        val myIntent = Intent(activity, CompanyProsDetails::class.java)
        activity!!.startActivity(myIntent)
    }

    fun openActivityEarnings() {
        val myIntent = Intent(activity, EarningsActivity::class.java)
        activity!!.startActivity(myIntent)
    }
}
