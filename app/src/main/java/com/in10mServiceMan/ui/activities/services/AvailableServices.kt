package com.in10mServiceMan.ui.activities.services

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.in10mServiceMan.Models.HomeService
import com.in10mServiceMan.Models.Service
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.BaseActivity
import com.in10mServiceMan.ui.activities.company_registration.CompanySignupActivity
import com.in10mServiceMan.ui.activities.signup.SignUpActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_available_services.*

class AvailableServices : BaseActivity(), IServicesView, ServicesAdapter.SelectedServiceCallback {
    override fun openSubService(item: Service) {
    }

    override fun removeSelectedService(item: Service) {
        serviceList.remove(item)
        if (serviceList.isEmpty())
            ServiceSelectionButton.visibility = View.GONE
    }

    override fun selectService(item: Service) {
        serviceList.add(item)
        ServiceSelectionButton.visibility = View.VISIBLE
    }

    override fun onServiceCompleted(mData: HomeService) {
        destroyDialog()
        bindData(mData)
    }

    override fun onFailed(msg: String) {
        destroyDialog()
        ShowToast(msg)
    }

    val mPresenter = ServicesPresenter(this)
    var serviceList: ArrayList<Service> = ArrayList()
    var serviceListString: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_services)

        initViews()
    }

    private fun initViews() {
        showProgressDialog("")
        mPresenter.getServices()

        ServiceSelectionButton.setOnClickListener {
            if (serviceList.isEmpty()) {
                ShowToast("Please select a service")
            } else {
                val gson = Gson()
                val responseString = gson.toJson(serviceList)
                Log.d("serviceList", responseString)
                for (i in 0 until serviceList.size) {
                    serviceListString = serviceListString + serviceList[i].serviceId + ","
                }
                SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.SERVICES_PROVIDED, responseString)
/*
                var newServiceListString = serviceListString.substring(0, serviceListString.length-1)
*/
                var newServiceListString = serviceListString.replace(",", " ").trim().replace(" ", ",")

                Log.d("service provide", newServiceListString)
                SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.SERVICES_PROVIDED_STRING, newServiceListString)
                serviceListString = ""
                if (SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.ACCOUNT_TYPE, "2") == "2") {
                    startActivity(Intent(this, CompanySignupActivity::class.java))

                } else if (SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.ACCOUNT_TYPE, "1") == "1") {
                    startActivity(Intent(this, SignUpActivity::class.java))
                }
            }
        }
    }

    private fun bindData(body: HomeService?) {
        val ListRV = findViewById<RecyclerView>(R.id.availableServicesRV)
        ListRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ListRV.adapter = ServicesAdapter(this, this, body)
    }

    override fun onBackPressed() {
        if (Constants.GlobalSettings.fromAccount)
            Constants.GlobalSettings.fromAccount = false

        super.onBackPressed()
    }
}
