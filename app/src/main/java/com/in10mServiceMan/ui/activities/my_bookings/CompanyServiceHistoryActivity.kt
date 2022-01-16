package com.in10mServiceMan.ui.activities.my_bookings

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.BaseActivity
import com.in10mServiceMan.ui.apis.LoginAPI
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.activity_company_service_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyServiceHistoryActivity : In10mBaseActivity() {
    private lateinit var bookingsAdapter: CompanyBookingsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_service_history)
        backBtn.setOnClickListener {
            finish()
        }
        initRV()
    }

    private fun initRV() {

        val user = localStorage(this).loggedInUser
        val token = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        if (!token.isNullOrEmpty()) {
            /*val header = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
            val userId = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0").toInt()
            var companyId = SharedPreferencesHelper.getInt(this, Constants.SharedPrefs.User.PERSON_COMPANY_NAME, 0).toInt()
            val callServiceProviders = LoginAPI.loginUser().getCompanyServiceHistory("Bearer $header", userId, companyId, 150, 1)//user.customerId
            callServiceProviders.enqueue(object : Callback<CompanyServiceHistoryResponse> {
                override fun onResponse(call: Call<CompanyServiceHistoryResponse>, response: Response<CompanyServiceHistoryResponse>) {
                    Log.i("response data", Gson().toJson(response.body()).toString())
                    if (response.isSuccessful) {
                        destroyDialog()
                        if (response.body()!!.data?.size!! > 0) {
                            noBookingTV.visibility = View.GONE
                            bookingsAdapter = CompanyBookingsAdapter(this@CompanyServiceHistoryActivity, response.body()!!.data as List<CSHData>)
                            serviceHistoryRV.layoutManager = LinearLayoutManager(this@CompanyServiceHistoryActivity)
                            serviceHistoryRV.adapter = bookingsAdapter
                        } else {
                            destroyDialog()
                            somethingWentWrong()
                        }
                    } else {
                        destroyDialog()
                        somethingWentWrong()
                    }
                }

                override fun onFailure(call: Call<CompanyServiceHistoryResponse>, t: Throwable) {
                    destroyDialog()

                    somethingWentWrong()
                }
            })*/
        } else {
            somethingWentWrong()
        }
    }

    private fun somethingWentWrong() {
        noBookingTV.visibility = View.VISIBLE
    }
}
