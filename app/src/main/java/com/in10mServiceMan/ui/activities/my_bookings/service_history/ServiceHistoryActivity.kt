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
            finish()
        }

        val adapter = ServiceHistoryViewpagerAdapter(supportFragmentManager)
//        adapter.addFragment(PendingHistory(), resources.getString(R.string.pending))
        adapter.addFragment(CompletedHistory(), resources.getString(R.string.completed))
        adapter.addFragment(CanceledHistory(), resources.getString(R.string.canceled))
        pager_service_history.adapter = adapter
        tab_service_history.setupWithViewPager(pager_service_history)
        //getServiceHistory()
    }


    private fun getServiceHistory() {
        showProgressDialog("")
        val user = localStorage(this).loggedInUser
        val token =
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        if (!token.isNullOrEmpty()) {
            val header =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
            val userId =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0")!!
                    .toInt()
            val callServiceProviders = APIClient.getApiInterface()
                .getServiceHistory("Bearer $header", userId, "5", 150, 1)//user.customerId
            callServiceProviders.enqueue(object : Callback<ServiceHistoryResponse> {
                override fun onResponse(
                    call: Call<ServiceHistoryResponse>,
                    response: Response<ServiceHistoryResponse>
                ) {
                    destroyDialog()
                    Log.i("response data", Gson().toJson(response.body()).toString())
                    if (response.isSuccessful) {
                        destroyDialog()
                        if (response.body()!!.data?.size!! > 0) {

                            adapter = ServiceHistoryViewpagerAdapter(supportFragmentManager)
                            adapter?.addFragment(
                                ServiceHistoryListFragment.getInstance(
                                    Gson().toJson(
                                        response?.body()
                                    ), true
                                ), "Completed"
                            )
                            adapter?.addFragment(
                                ServiceHistoryListFragment.getInstance(
                                    Gson().toJson(
                                        response?.body()
                                    ), false
                                ), "Canceled"
                            )
                            tab_service_history.setupWithViewPager(pager_service_history)
                            pager_service_history.adapter = adapter
                        } else {
                            destroyDialog()
                            //somethingWentWrong()
                        }
                    } else {
                        destroyDialog()
                        //somethingWentWrong()
                    }
                }

                override fun onFailure(call: Call<ServiceHistoryResponse>, t: Throwable) {
                    destroyDialog()

                    //somethingWentWrong()
                }
            })
        } else {
            //somethingWentWrong()
        }
    }
}