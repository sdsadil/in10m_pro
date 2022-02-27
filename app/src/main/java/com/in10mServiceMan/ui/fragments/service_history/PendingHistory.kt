package com.in10mServiceMan.ui.fragments.service_history


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.in10mServiceMan.ui.activities.BaseFragment

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.my_bookings.BookingHistoryInterface
import com.in10mServiceMan.ui.activities.my_bookings.ServiceHistoryData
import com.in10mServiceMan.ui.activities.my_bookings.ServiceHistoryResponse
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.fragments.past_bookings.BookingsAdapter
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.fragment_pending_history.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class PendingHistory : BaseFragment(), BookingHistoryInterface {
    override fun adapterTransaction() {
        Toast.makeText(activity, resources.getString(R.string.no_invoice_found), Toast.LENGTH_LONG).show()
    }

    private lateinit var bookingsAdapter: BookingsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pending_history, container, false)

        getServiceHistory()

        return view
    }

    private fun getServiceHistory() {
        showProgressDialog("")
        val user = localStorage(activity).loggedInUser
        val token = SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        if (!token.isNullOrEmpty()) {
            val header = SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.AUTH_TOKEN, "")
            val userId = SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.USER_ID, "0")!!
                .toInt()
            val userType = SharedPreferencesHelper.getInt(activity, Constants.SharedPrefs.User.PERSON_TYPE, 2)

            if (userType == 3)  {
                val companyId = SharedPreferencesHelper.getInt(activity, Constants.SharedPrefs.User.PERSON_COMPANY_NAME, 0).toInt()
                val callServiceProviders = APIClient.getApiInterface().getCompanyServiceHistory("Bearer $header", userId, companyId, 150, 1)//user.customerId
                callServiceProviders.enqueue(object : Callback<ServiceHistoryResponse> {
                    override fun onResponse(call: Call<ServiceHistoryResponse>, response: Response<ServiceHistoryResponse>) {
                        destroyDialog()
                        Log.i("response data", Gson().toJson(response.body()).toString())
                        if (response.isSuccessful) {
                            destroyDialog()
                            if (response.body()!!.data.size > 0) {
                                view!!.noDataFound.visibility = View.GONE
                                bookingsAdapter = BookingsAdapter(this@PendingHistory.requireContext(), response.body()!!.data as List<ServiceHistoryData>, this@PendingHistory)
                                view!!.pendingRV.layoutManager = LinearLayoutManager(this@PendingHistory.requireContext())
                                view!!.pendingRV.adapter = bookingsAdapter
                            } else {
                                destroyDialog()
                                view!!.noDataFound.visibility = View.VISIBLE
                                //Toast.makeText(this@CanceledHistory.requireContext(),"No Data Found", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            destroyDialog()
                            view!!.noDataFound.visibility = View.VISIBLE
                            //somethingWentWrong()
                        }
                    }

                    override fun onFailure(call: Call<ServiceHistoryResponse>, t: Throwable) {
                        destroyDialog()
                        view!!.noDataFound.visibility = View.VISIBLE
                        //somethingWentWrong()
                    }
                })
            }
            else    {
                val callServiceProviders = APIClient.getApiInterface().getServiceHistory("Bearer $header", userId, "5", 150, 1)//user.customerId
                callServiceProviders.enqueue(object : Callback<ServiceHistoryResponse> {
                    override fun onResponse(call: Call<ServiceHistoryResponse>, response: Response<ServiceHistoryResponse>) {
                        destroyDialog()
                        Log.i("response data", Gson().toJson(response.body()).toString())
                        if (response.isSuccessful) {
                            destroyDialog()
                            if (response.body()!!.data.isNotEmpty()) {
                                view!!.noDataFound.visibility = View.GONE
                                bookingsAdapter = BookingsAdapter(this@PendingHistory.requireContext(), response.body()!!.data as List<ServiceHistoryData>, this@PendingHistory)
                                view!!.pendingRV.layoutManager = LinearLayoutManager(this@PendingHistory.requireContext())
                                view!!.pendingRV.adapter = bookingsAdapter
                            } else {
                                destroyDialog()
                                view!!.noDataFound.visibility = View.VISIBLE
                                //Toast.makeText(this@CanceledHistory.requireContext(),"No Data Found", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            destroyDialog()
                            view!!.noDataFound.visibility = View.VISIBLE
                            //somethingWentWrong()
                        }
                    }

                    override fun onFailure(call: Call<ServiceHistoryResponse>, t: Throwable) {
                        destroyDialog()
                        view!!.noDataFound.visibility = View.VISIBLE
                        //somethingWentWrong()
                    }
                })
            }
        } else {
            //somethingWentWrong()
        }
    }
}
