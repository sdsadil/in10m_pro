package com.in10mServiceMan.ui.activities.earnings


import android.os.Bundle
import android.util.Log
import android.view.View
import com.in10mServiceMan.models.EarningsResponse
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_earnings.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarningsActivity : In10mBaseActivity() {

    var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earnings)
        showProgressDialog("")

        button_close.setOnClickListener {
            Constants.GlobalSettings.fromIA = false
            finish()
        }

        getServiceManEarnings()

        onlinePaymentLL.setOnClickListener {
            if (onlineLayout.visibility == View.GONE)   {
                onlineLayout.visibility = View.VISIBLE
                onlineArrow.rotation = 180f
            }
            else    {
                onlineLayout.visibility = View.GONE
                onlineArrow.rotation = 0f
            }
        }

        cashPaymentLL.setOnClickListener {
            if (cashLayout.visibility == View.GONE)   {
                cashLayout.visibility = View.VISIBLE
                cashArrow.rotation = 180f
            }
            else    {
                cashLayout.visibility = View.GONE
                cashArrow.rotation = 0f
            }
        }
    }

    private fun getServiceManEarnings() {
        val header = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")

        if (Constants.GlobalSettings.fromIA) {
            userId = SharedPreferencesHelper.getInt(this, Constants.SharedPrefs.User.COMMON_ID, 0)
        }
        else    {
            userId = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")!!
                .toInt()
        }

        val homeCall = APIClient.getApiInterface().getServicemanEarnings("Bearer $header", userId.toString())
        homeCall.enqueue(object : Callback<EarningsResponse> {
            override fun onResponse(call: Call<EarningsResponse>, response: Response<EarningsResponse>) {
                if (response.isSuccessful) {
                    destroyDialog()
                    setData(response.body()!!)
                } else {
                    destroyDialog()
                    Log.d("error", "Error")
                }
            }

            override fun onFailure(call: Call<EarningsResponse>, t: Throwable) {
                destroyDialog()
                Log.d("error", "Error")
            }
        })
    }

    fun setData(data: EarningsResponse) {
        totalEarningContainerCL.visibility = View.VISIBLE
        onlinePaymentValueTV.text = "KD " + data.data?.online_total_customer_payment.toString()
        onlineSubOneRate.text = "KD " + data.data?.online_total_earning.toString()
        onlineSubTwoRate.text = "KD " + data.data?.online_in10m_other_charges.toString()
        onlineSubThreeRate.text = "KD " + data.data?.online_total_customer_payment.toString()
        cashPaymentValueTV.text = "KD " + data.data?.cash_total_customer_payment.toString()
        cashSubOneRate.text = "KD " + data.data?.cash_total_earning.toString()
        cashSubTwoRate.text = "KD " + data.data?.cash_in10m_other_charges.toString()
        cashSubThreeRate.text = "KD " + data.data?.cash_total_customer_payment.toString()
        totalEarningValueTV.text = "KD " + data.data?.total_in10m_outstanding.toString()
        outstandingAmountValueTV.text = "KD " + data.data?.total_earnings.toString()
    }

    override fun onBackPressed() {
        if (Constants.GlobalSettings.fromIA)
            Constants.GlobalSettings.fromIA = false
        super.onBackPressed()
    }
}
