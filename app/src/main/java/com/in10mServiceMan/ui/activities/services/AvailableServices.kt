package com.in10mServiceMan.ui.activities.services

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import com.in10mServiceMan.models.HomeService
import com.in10mServiceMan.models.Service
import com.in10mServiceMan.R
import com.in10mServiceMan.db.WagonItem
import com.in10mServiceMan.models.AddServicePojo
import com.in10mServiceMan.ui.accound_edit.addService.IServiceInteractorListener
import com.in10mServiceMan.ui.accound_edit.addService.ServiceInteractor
import com.in10mServiceMan.ui.accound_edit.addService.ServiceResponse
import com.in10mServiceMan.ui.activities.BaseActivity
import com.in10mServiceMan.ui.activities.company_registration.CompanySignupActivity
import com.in10mServiceMan.ui.activities.signup.SignUpActivity
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_available_services.*
import org.json.JSONArray

class AvailableServices : In10mBaseActivity(), IServicesView, IServiceInteractorListener,
    ServicesAdapter.SelectedServiceCallback {

    val mPresenterr = ServiceInteractor(this)


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

        ivClose.setOnClickListener {
            onBackPressed()
        }
        initViews()
    }

    private fun initViews() {
        showProgressDialog("")
        mPresenter.getServices()

        ServiceSelectionButton.setOnClickListener {
            if (serviceList.isEmpty()) {
                ShowToast(getString(R.string.please_select_a_service))
            } else {
                val gson = Gson()
                val responseString = gson.toJson(serviceList)
                val addServiceList: MutableList<AddServicePojo> = ArrayList()

                for (i in 0 until serviceList.size) {
                    serviceListString = serviceListString + serviceList[i].serviceId + ","
                    val addServicePojo = AddServicePojo()
                    addServicePojo.certificate = ""
                    addServicePojo.service_id = serviceList[i].serviceId.toString()
                    addServicePojo.total_experience = ""
                    addServiceList.add(addServicePojo)
                }

                val addServiceParam = Gson().toJson(addServiceList)

                SharedPreferencesHelper.putString(
                    this,
                    Constants.SharedPrefs.User.SERVICES_PROVIDED,
                    responseString
                )
/*
                var newServiceListString = serviceListString.substring(0, serviceListString.length-1)
*/
                val newServiceListString =
                    serviceListString.replace(",", " ").trim().replace(" ", ",")
                SharedPreferencesHelper.putString(
                    this,
                    Constants.SharedPrefs.User.SERVICES_PROVIDED_STRING,
                    newServiceListString
                )
                serviceListString = ""
                if (!Constants.GlobalSettings.fromAccount) {
                    if (SharedPreferencesHelper.getString(
                            this,
                            Constants.SharedPrefs.User.ACCOUNT_TYPE,
                            "2"
                        ) == "2"
                    ) {
                        startActivity(Intent(this, CompanySignupActivity::class.java))
                    } else if (SharedPreferencesHelper.getString(
                            this,
                            Constants.SharedPrefs.User.ACCOUNT_TYPE,
                            "1"
                        ) == "1"
                    ) {
                        startActivity(Intent(this, SignUpActivity::class.java))
                    }
                } else {
                    val userId =
                        SharedPreferencesHelper.getString(
                            this,
                            Constants.SharedPrefs.User.USER_ID,
                            "0"
                        )!!
                            .toInt()
                    mPresenterr.services(userId, addServiceParam)
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

    override fun onServiceCompleted(mPost: ServiceResponse) {
        ShowToast(getString(R.string.service_added_successfully))
        onBackPressed()
    }

    override fun onServiceFailed(msg: String) {

    }
}
