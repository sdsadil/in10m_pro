package com.in10mServiceMan.ui.activities.company_registration

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.google.gson.Gson
import com.in10mServiceMan.models.HomeService
import com.in10mServiceMan.models.Service
import com.in10mServiceMan.models.viewmodels.CompanyServices
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.signup.ProfileSuccessActivity
import com.in10mServiceMan.ui.adapter.CompanyServimanDetailsAdapter
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_company_resource.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyResourceActivity : In10mBaseActivity(), CompanyServimanDetailsAdapter.SelectedServiceCallback,
        CompanyServimanDetailsAdapter.setFullChanges {

    var mAdap: CompanyServimanDetailsAdapter? = null
    var serId = ""

    override fun setUserDetails(data: ArrayList<CompanyServices>) {

        Log.e("ACTIITYYYYY", "IVIDE uNDDDDDDDD" + data.size)
        if (Constants.GlobalSettings.fromIAS)
            Constants.SharedPrefs.User.totalStringRequest = Gson().toJson(data[0].users)
        else
            Constants.SharedPrefs.User.totalStringRequest = Gson().toJson(data)
        Log.d("full data ", Constants.SharedPrefs.User.totalStringRequest)

        serId = data[0].service_id.toString()

        setServicemanData()
    }

    override fun openSubService(item: Service) {
    }

    override fun selectService(item: Service) {
    }

    override fun removeSelectedService(item: Service) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_resource)

        getServiceManServices()

        companyServicemanDetailsEntryButton.setOnClickListener {
            mAdap!!.onClick()
            /*if (Constants.SharedPrefs.User.totalStringRequest.isNotEmpty()) {
                Log.d("serviceman List", Constants.SharedPrefs.User.totalStringRequest)
                //setServicemanData()
            } else {
                ShowToast("Service provider list empty")
            }*/
        }
    }

    private fun getServiceManServices() {

        val header = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val homeCall =
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0")
                ?.let { APIClient.getApiInterface().getExistingServiceDetailsWithHeader("Bearer $header", it.toInt()) }
        homeCall?.enqueue(object : Callback<HomeService> {
            override fun onResponse(call: Call<HomeService>, response: Response<HomeService>) {
                if (response.isSuccessful) {
                    bindRecyclerData(response.body()!!)
                } else {
                    Log.d("error", "Error")
                }
            }

            override fun onFailure(call: Call<HomeService>, t: Throwable) {
                Log.d("error", "Error")
            }
        })
    }

    private fun bindRecyclerData(body: HomeService?) {
        var listCompanyServices: ArrayList<CompanyServices> = ArrayList()

        for (i in 0 until body?.data?.size!!) {

            listCompanyServices.add(CompanyServices(body.data[i].serviceId, body.data[i].serviceName, body.data[i].serviceColor))
        }

        val ListRV = findViewById<RecyclerView>(R.id.companyOfferedServicesRV)
        ListRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdap = CompanyServimanDetailsAdapter(this, this, listCompanyServices, this)
        ListRV.adapter = mAdap
    }

    private fun setServicemanData() {
        showProgressDialog("")
        val header = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")

        if (Constants.GlobalSettings.fromIAS)   {
            //Log.d("total data ", Constants.SharedPrefs.User.totalStringRequest)
            val service = "[{\"service_id\":$serId,\"users\":" + Constants.SharedPrefs.User.totalStringRequest + "}]"
            Log.d("service", service)
            val homeCall = APIClient.getApiInterface().addServicemanExtra("Bearer $header", SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0"), service)
            homeCall.enqueue(object : Callback<ServiceProviderAddResponse> {
                override fun onResponse(call: Call<ServiceProviderAddResponse>, response: Response<ServiceProviderAddResponse>) {
                    destroyDialog()
                    if (response.isSuccessful) {
                        /*ShowToast(response.body()?.message)*/
                        response.body()?.message?.let { Log.d("message", it) }
                        finish()
                    } else {
                        destroyDialog()
                        Log.d("error", "Error")
                    }
                }

                override fun onFailure(call: Call<ServiceProviderAddResponse>, t: Throwable) {
                    destroyDialog()
                    Log.d("error", "Error")
                }
            })
        }
        else    {
            val homeCall = APIClient.getApiInterface().addServiceman("Bearer $header", SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0"), Constants.SharedPrefs.User.totalStringRequest)
            homeCall.enqueue(object : Callback<ServiceProviderAddResponse> {
                override fun onResponse(call: Call<ServiceProviderAddResponse>, response: Response<ServiceProviderAddResponse>) {
                    destroyDialog()
                    if (response.isSuccessful) {
                        /*ShowToast(response.body()?.message)*/
                        startActivity(Intent(this@CompanyResourceActivity, ProfileSuccessActivity::class.java))
                        finish()
                    } else {
                        destroyDialog()
                        Log.d("error", "Error")
                    }
                }

                override fun onFailure(call: Call<ServiceProviderAddResponse>, t: Throwable) {
                    destroyDialog()
                    Log.d("error", "Error")
                }
            })
        }
    }

    override fun onBackPressed() {
        Constants.GlobalSettings.fromIAS = false
        super.onBackPressed()
    }

}
