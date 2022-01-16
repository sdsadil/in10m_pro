package com.in10mServiceMan.ui.activities.company_pros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.company_pros.Inactive.InActive
import com.in10mServiceMan.ui.activities.company_pros.active.Active
import com.in10mServiceMan.ui.activities.company_pros.pending.Pending
import com.in10mServiceMan.ui.activities.my_bookings.service_history.adapter.ServiceHistoryViewpagerAdapter
import kotlinx.android.synthetic.main.activity_company_pros.*

class CompanyPros : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_pros)

        val adapter = ServiceHistoryViewpagerAdapter(supportFragmentManager)
        adapter.addFragment(Active(), "Active")
        adapter.addFragment(InActive(), "Inactive")
        adapter.addFragment(Pending(), "Pending")
        viewpager_CP.adapter = adapter
        tab_service_CP.setupWithViewPager(viewpager_CP)

        backBtn.setOnClickListener {
            finish()
        }
    }
}
