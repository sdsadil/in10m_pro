package com.in10mServiceMan.ui.activities.company_pros.active


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.in10mServiceMan.ui.activities.BaseFragment

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.company_pros.CompanyProsDetails
import com.in10mServiceMan.ui.activities.company_pros.IAResponse
import com.in10mServiceMan.ui.activities.company_pros.active.active_API.ActiveData
import com.in10mServiceMan.ui.activities.company_pros.active.active_API.ActivePresenter
import com.in10mServiceMan.ui.activities.company_pros.active.active_API.ActiveResponse
import com.in10mServiceMan.ui.activities.company_pros.active.active_API.IActiveView
import com.in10mServiceMan.ui.activities.company_pros.disable_API.DisablePresenter
import com.in10mServiceMan.ui.activities.company_pros.disable_API.IDisableView
import com.in10mServiceMan.ui.activities.company_registration.CompanyResourceActivity
import com.in10mServiceMan.ui.activities.earnings.EarningsActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_active.view.*

/**
 * A simple [Fragment] subclass.
 */
class Active : BaseFragment(), ActiveInterface, IActiveView, IDisableView {
    override fun onDisableCompleted(mPost: IAResponse) {
        destroyDialog()
        if (mPost.status == 1)  {
            Toast.makeText(activity, mPost.message, Toast.LENGTH_LONG).show()
            showProgressDialog("")
            val compId = SharedPreferencesHelper.getInt(activity, Constants.SharedPrefs.User.PERSON_COMPANY_NAME, 0)
            mActivePresenter.active(compId, 1)
        }
        else    {
            Toast.makeText(activity, mPost.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDisableFailed(msg: String) {
        destroyDialog()
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onActiveCompleted(mPost: ActiveResponse) {
        destroyDialog()
        if (mPost.status == 1)  {
            recyclerView(mPost.data)
        }
        else    {
            view!!.data_AA.visibility = View.VISIBLE
        }
    }

    override fun onActiveFailed(msg: String) {
        destroyDialog()
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun deactivate(companyId: Int, companyProId: Int) {
        showProgressDialog("")
        mDisablePresenter.disable(companyId, companyProId)
    }

    override fun activeEarningsTransaction() {
        Constants.GlobalSettings.fromIA = true
        openActivityEarnings()
    }

    override fun activeTransaction() {
        openActivity()
    }

    var mDisablePresenter = DisablePresenter(this)
    var mActivePresenter = ActivePresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_active, container, false)

        val compId = SharedPreferencesHelper.getInt(activity, Constants.SharedPrefs.User.PERSON_COMPANY_NAME, 0)
        mActivePresenter.active(compId, 1)

        view.addActiveServiceman.setOnClickListener {
            Constants.GlobalSettings.fromIAS = true
            val myIntent = Intent(activity, CompanyResourceActivity::class.java)
            activity!!.startActivity(myIntent)
        }

        return view
    }

    fun recyclerView(mData: List<ActiveData>)  {
        view!!.recyclerView_AA.layoutManager = LinearLayoutManager(activity)
        view!!.recyclerView_AA.adapter = ActiveAdapter(mData, activity!!, this)
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
