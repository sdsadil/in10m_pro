package com.in10mServiceMan.ui.accound_edit


import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.adapter.AccountViewPagerAdapter
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_my_account_edit.*

class MyAccountEdit() : In10mBaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account_edit)
        val myAccountList = arrayOf(
            resources.getString(R.string.profile),
            resources.getString(R.string.services),
            resources.getString(R.string.my_work)
        )
        val userType =
            SharedPreferencesHelper.getInt(this, Constants.SharedPrefs.User.PERSON_TYPE, 2)

        /* val adapter = ServiceHistoryViewpagerAdapter(supportFragmentManager)
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
         tab_service_history.setupWithViewPager(pager_service_history)*/

        val adapter = AccountViewPagerAdapter(supportFragmentManager, lifecycle, this@MyAccountEdit)
        pager_service_history.adapter = adapter
        TabLayoutMediator(tab_service_history, pager_service_history) { tab, position ->
            tab.text = myAccountList[position]
        }.attach()

        backBtn.setOnClickListener {
            onBackPressed()
            overridePendingTransition(0, 0)
        }
    }
}
