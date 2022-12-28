package com.in10mServiceMan.ui.accound_edit


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.in10mServiceMan.ui.activities.BaseFragment

import com.in10mServiceMan.R
import com.in10mServiceMan.models.*
import com.in10mServiceMan.models.viewmodels.ServiceWithSubService
import com.in10mServiceMan.ui.activities.profile.ServiceOfferAdapter
import com.in10mServiceMan.ui.activities.services.AvailableServices
import com.in10mServiceMan.ui.activities.services.ServiceData
import com.in10mServiceMan.ui.activities.services.ServicesResponse
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.listener.EditSubServicesListener
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_services.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class Services(context: Context) : BaseFragment(), EditSubServicesListener {
    private val mcontext: Context = context
    private var isStarted = false
    private var isVisiblee = false

    private var servicemanSelectedServiceAdapter: ServiceOfferAdapter? = null

    var serviceListString: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_services, container, false)
        view.btnAddServices.setOnClickListener {
            Constants.GlobalSettings.fromAccount = true
            startActivity(Intent(mcontext, AvailableServices::class.java))
        }
        view.btnEditProfile_view_services.setOnClickListener {
            view.btnSaveProfile_view_services.visibility = View.VISIBLE
            view.btnEditProfile_view_services.visibility = View.GONE
            servicemanSelectedServiceAdapter?.setVisibleDelete(true)
        }

        view.btnSaveProfile_view_services.setOnClickListener {
            val serviceList: List<ServiceData> = servicemanSelectedServiceAdapter!!.serviceDataList
            val gson = Gson()
            val addServiceList: MutableList<AddServicePojo> = ArrayList()

            for (i in serviceList.indices) {
                serviceListString = serviceListString + serviceList[i].serviceId + ","
                val addServicePojo = AddServicePojo()
                addServicePojo.certificate = ""
                addServicePojo.service_id = serviceList[i].serviceId.toString()
                addServicePojo.total_experience = serviceList[i].totalExperience.toString()
                addServiceList.add(addServicePojo)
            }
            val services = Gson().toJson(addServiceList)
            updateProfile(services)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        getPreServices()
    }
    override fun onStart() {
        super.onStart()
//        isStarted = true
//        if (isVisiblee)
//            getPreServices()

    }

    override fun onStop() {
        super.onStop()
        isStarted = false
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isVisiblee = isVisibleToUser
        if (isVisiblee && isStarted) {
            if (isVisiblee && isStarted) getPreServices()
        } else {
            destroyDialog()
        }

    }

    private fun updateProfile(services: String) {
        val userId =
            SharedPreferencesHelper.getString(
                mcontext,
                Constants.SharedPrefs.User.USER_ID,
                "0"
            )!!.toInt()
        showProgressDialog("")
        val update_service = APIClient.getApiInterface().update_service(
            userId,
            services
        )
        update_service?.enqueue(object : Callback<UpdateService> {
            override fun onResponse(
                call: Call<UpdateService>,
                response: Response<UpdateService>,
            ) {
                destroyDialog()
                if (response.isSuccessful) {
                    Toast.makeText(mcontext, response.body()?.message, Toast.LENGTH_SHORT).show()
                    view!!.btnSaveProfile_view_services.visibility = View.GONE
                    view!!.btnEditProfile_view_services.visibility = View.VISIBLE
                    servicemanSelectedServiceAdapter?.setVisibleDelete(false)
                }
            }

            override fun onFailure(call: Call<UpdateService>, t: Throwable) {
                destroyDialog()
                Log.d("onResponse", "Error")
            }
        })
    }

    private fun getPreServices() {
        showProgressDialog("")
        APIClient.token =
            SharedPreferencesHelper.getString(mcontext, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val homeCall =
            SharedPreferencesHelper.getString(mcontext, Constants.SharedPrefs.User.USER_ID, "0")
                ?.let {
                    APIClient.getApiInterface().getExistingServiceDetailsWithHeaderAndExperience(
                        "Bearer " + APIClient.token,
                        it.toInt()
                    )
                }
        homeCall?.enqueue(object : Callback<ServicesResponse> {
            override fun onResponse(
                call: Call<ServicesResponse>,
                response: Response<ServicesResponse>,
            ) {
                destroyDialog()
                if (response.isSuccessful) {

                    bindOfferedServiceRecyclerView(response.body()!!)
                }
            }

            override fun onFailure(call: Call<ServicesResponse>, t: Throwable) {
                destroyDialog()
                Log.d("error", "Error")
            }
        })
    }

    fun bindOfferedServiceRecyclerView(body: ServicesResponse?) {
        val linearLayoutManager = LinearLayoutManager(mcontext)
        servicemanSelectedServiceAdapter = ServiceOfferAdapter(body?.data, mcontext, this)
        view!!.recycler_view_services.layoutManager = linearLayoutManager
        view!!.recycler_view_services.adapter = servicemanSelectedServiceAdapter
        view!!.recycler_view_services.isNestedScrollingEnabled = false
    }

    override fun onEditClick(position: Int, serviceWithSubService: ServiceWithSubService?) {

    }

    override fun onDeleteClick(position: Int, serviceData: ServiceData?) {
        showProgressDialog("")
        val homeCall =
            SharedPreferencesHelper.getString(mcontext, Constants.SharedPrefs.User.USER_ID, "0")
        val removeSubServicesModel = RequestRemoveServicesModel()
        removeSubServicesModel.servicemanId = homeCall?.toInt()
        removeSubServicesModel.service_id = serviceData?.serviceId
        val removeSubServices =
            APIClient.getApiInterface().remove_service(removeSubServicesModel)
        removeSubServices?.enqueue(object : Callback<RemoveServicePojo> {
            override fun onResponse(
                call: Call<RemoveServicePojo>,
                response: Response<RemoveServicePojo>,
            ) {
                destroyDialog()
                if (response.isSuccessful) {
                    Log.e("onResponse", "" + response.body())
                    getPreServices()
//                    bindOfferedServiceRecyclerView(response.body()!!)
                }
            }

            override fun onFailure(call: Call<RemoveServicePojo>, t: Throwable) {
                destroyDialog()
                Log.e("onFailure", "Error")
            }
        })
    }


}
