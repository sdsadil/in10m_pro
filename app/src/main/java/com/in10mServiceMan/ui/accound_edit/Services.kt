package com.in10mServiceMan.ui.accound_edit


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.in10m.ui.activities.BaseFragment

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.profile.ServiceOfferAdapter
import com.in10mServiceMan.ui.activities.services.AvailableServices
import com.in10mServiceMan.ui.activities.services.ServicesResponse
import com.in10mServiceMan.ui.apis.LoginAPI
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_services.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class Services : BaseFragment() {

    private var servicemanSelectedServiceAdapter: ServiceOfferAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_services, container, false)

        getPreServices()

        view.btnAddServices.setOnClickListener {
            Constants.GlobalSettings.fromAccount = true
            startActivity(Intent(activity, AvailableServices::class.java))
        }

        return view
    }

    private fun getPreServices() {
        LoginAPI.TOkenn = SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val homeCall =
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.USER_ID, "0")
                ?.let { LoginAPI.loginUser().getExistingServiceDetailsWithHeaderAndExperience("Bearer " + LoginAPI.TOkenn, it.toInt()) }
        homeCall?.enqueue(object : Callback<ServicesResponse> {
            override fun onResponse(call: Call<ServicesResponse>, response: Response<ServicesResponse>) {
                if (response.isSuccessful) {
                    bindOfferedServiceRecyclerView(response.body()!!)
                } else {
                    Log.d("error", "Error")
                }
            }

            override fun onFailure(call: Call<ServicesResponse>, t: Throwable) {
                Log.d("error", "Error")
            }
        })

        /*loadExistingServices()
        loadServicemanExperience()*/
    }

    fun bindOfferedServiceRecyclerView(body: ServicesResponse?) {
        val linearLayoutManager = LinearLayoutManager(activity)
        servicemanSelectedServiceAdapter = ServiceOfferAdapter(body?.data, activity)
        view!!.recycler_view_services.layoutManager = linearLayoutManager
        view!!.recycler_view_services.adapter = servicemanSelectedServiceAdapter
        view!!.recycler_view_services.isNestedScrollingEnabled = false
    }
}
