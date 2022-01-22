package com.in10mServiceMan.ui.activities.my_bookings

import android.os.Bundle
import com.in10mServiceMan.ui.base.In10mBaseActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.in10mServiceMan.Models.BookingHistoryResponse
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.BaseActivity
import com.in10mServiceMan.ui.apis.LoginAPI
import com.in10mServiceMan.ui.fragments.past_bookings.BookingsAdapter
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.activity_my_booking.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBookingsActivity : In10mBaseActivity(), BookingHistoryInterface {
    override fun adapterTransaction() {
    }

    private lateinit var bookingsAdapter: BookingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_booking)

        backBtn.setOnClickListener {
            finish()
        }
        showProgressDialog("")
        initRV()
    }

    private fun initRV() {

        val user = localStorage(this).loggedInUser
        val token = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        if (!token.isNullOrEmpty()) {
            val header = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
            val userId = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0")!!
                .toInt()
            val callServiceProviders = LoginAPI.loginUser().getServiceHistory("Bearer $header", userId, "5",150, 1)//user.customerId
            callServiceProviders.enqueue(object : Callback<ServiceHistoryResponse> {
                override fun onResponse(call: Call<ServiceHistoryResponse>, response: Response<ServiceHistoryResponse>) {
                    Log.i("response data", Gson().toJson(response.body()).toString())
                    if (response.isSuccessful) {
                        destroyDialog()
                        if (response.body()!!.data?.size!! > 0) {
                            noBookingTV.visibility = View.GONE
                            bookingsAdapter = BookingsAdapter(this@MyBookingsActivity, response.body()!!.data as List<ServiceHistoryData>, this@MyBookingsActivity)
                            upcomingRV.layoutManager = LinearLayoutManager(this@MyBookingsActivity)
                            upcomingRV.adapter = bookingsAdapter
                        } else {
                            destroyDialog()
                            somethingWentWrong()
                        }
                    } else {
                        destroyDialog()
                        somethingWentWrong()
                    }
                }

                override fun onFailure(call: Call<ServiceHistoryResponse>, t: Throwable) {
                    destroyDialog()

                    somethingWentWrong()
                }
            })
        } else {
            somethingWentWrong()
        }
    }

    private fun somethingWentWrong() {
        noBookingTV.visibility = View.VISIBLE
    }


}
