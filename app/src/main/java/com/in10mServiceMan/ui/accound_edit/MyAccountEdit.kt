package com.in10mServiceMan.ui.accound_edit


import android.os.Bundle
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.my_bookings.service_history.adapter.ServiceHistoryViewpagerAdapter
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_my_account_edit.*

class MyAccountEdit() : In10mBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account_edit)

        val userType =
            SharedPreferencesHelper.getInt(this, Constants.SharedPrefs.User.PERSON_TYPE, 2)

        val adapter = ServiceHistoryViewpagerAdapter(supportFragmentManager)
        if (userType == 3) {
            adapter.addFragment(Profile(this@MyAccountEdit), resources.getString(R.string.profile))
        } else {
            adapter.addFragment(Profile(this@MyAccountEdit), resources.getString(R.string.profile))
            adapter.addFragment(Services(this@MyAccountEdit), resources.getString(R.string.services))
            adapter.addFragment(AddPortFolio(this@MyAccountEdit), resources.getString(R.string.my_work))
//            adapter.addFragment(Payment(), resources.getString(R.string.payment))
//            adapter.addFragment(Estimates(), resources.getString(R.string.estimates))
        }
        pager_service_history.adapter = adapter
        tab_service_history.setupWithViewPager(pager_service_history)

        backBtn.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }
    }
}
