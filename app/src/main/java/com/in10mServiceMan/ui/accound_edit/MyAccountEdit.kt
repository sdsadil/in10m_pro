package com.in10mServiceMan.ui.accound_edit


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.my_bookings.service_history.adapter.ServiceHistoryViewpagerAdapter
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_my_account_edit.*

class MyAccountEdit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account_edit)

        var userType = SharedPreferencesHelper.getInt(this, Constants.SharedPrefs.User.PERSON_TYPE, 2)

        val adapter = ServiceHistoryViewpagerAdapter(supportFragmentManager)
        if (userType == 3)  {
            adapter.addFragment(Profile(),"Profile")
        }
        else {
            adapter.addFragment(Profile(),"Profile")
            adapter.addFragment(Services(), "Services")
            adapter.addFragment(Payment(), "Payment")
            adapter.addFragment(Estimates(), "Estimates")
        }
        pager_service_history.adapter = adapter
        tab_service_history.setupWithViewPager(pager_service_history)

        backBtn.setOnClickListener {
            finish()
        }
    }
}
