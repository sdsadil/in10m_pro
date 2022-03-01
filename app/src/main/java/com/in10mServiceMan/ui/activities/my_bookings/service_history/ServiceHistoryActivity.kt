package com.in10mServiceMan.ui.activities.my_bookings.service_history

import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.my_bookings.ServiceHistoryResponse
import com.in10mServiceMan.ui.activities.my_bookings.service_history.adapter.ServiceHistoryViewpagerAdapter
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.ui.fragments.service_history.CanceledHistory
import com.in10mServiceMan.ui.fragments.service_history.CompletedHistory
import com.in10mServiceMan.ui.fragments.service_history.PendingHistory
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.activity_service_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceHistoryActivity : In10mBaseActivity() {

    var adapter: ServiceHistoryViewpagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_history)

        backBtn.setOnClickListener {
           onBackPressed()
        }

        val adapter = ServiceHistoryViewpagerAdapter(supportFragmentManager)
//        adapter.addFragment(PendingHistory(), resources.getString(R.string.pending))
        adapter.addFragment(CompletedHistory(), resources.getString(R.string.completed))
        adapter.addFragment(CanceledHistory(), resources.getString(R.string.canceled))
        pager_service_history.adapter = adapter
        tab_service_history.setupWithViewPager(pager_service_history)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(0,0)
    }


}