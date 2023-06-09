package com.in10mServiceMan.ui.activities.rating

import android.os.Bundle
import android.view.View
import com.google.gson.JsonElement
import com.in10mServiceMan.models.CustomerCompleteProfileAfterUpdate
import com.in10mServiceMan.models.RequestReviewModel
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_customer_rating.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerRating : In10mBaseActivity() {
    var bookingId = ""
    var customerId = ""
    var serviceId = ""
    var overAllRating = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_rating)
        customerReviewCL.visibility = View.VISIBLE

        if (intent.extras != null) {
            bookingId = intent.getStringExtra("bookingId").toString()
            customerId = intent.getStringExtra("customerId").toString()
            serviceId = intent.getStringExtra("serviceId").toString()
        }
        if (customerId != null) {
            loadCustomerProfile(customerId)
        }


        backButton.setOnClickListener {
            finish()
            overridePendingTransition(0,0)
        }
        SkipButtonTV.setOnClickListener {
            finish()
            overridePendingTransition(0,0)
        }

        reviewThumpsUpIV.setOnClickListener {
            reviewThumpsUpIV.setImageResource(R.drawable.ic_happy_smilee_icon_large)
            reviewThumpsDownIV.setImageResource(R.drawable.ic_sad_smilee_uncheck)
            overAllRating = 1
        }

        reviewThumpsDownIV.setOnClickListener {
            reviewThumpsUpIV.setImageResource(R.drawable.ic_happy_smilee_uncheck)
            reviewThumpsDownIV.setImageResource(R.drawable.ic_sad_smilee_icon_large)
            overAllRating = 0
        }
        lvBtnSkip.setOnClickListener {
            finish()
            overridePendingTransition(0,0)
        }
        lvBtnSubmit.setOnClickListener {
            var header = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
            var userId = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
            var bookingID = bookingId
            val rm = RequestReviewModel()
            rm.bookingId = bookingID
            rm.customerId = customerId
            rm.status = "1"
            rm.serviceProviderId = userId
            rm.overallRating = overAllRating.toString()
            rm.serviceId = serviceId

            if (reviewDescriptionET.text.toString().trim().isNotEmpty())
                rm.description = reviewDescriptionET.text.toString().trim()
            else
                rm.description = "".trim()
            /*rm.priceRating = priceRating.toString()
            rm.knowledgeRating = knowledgeRating.toString()
            */

            showProgressDialog("")
            val callServicProviders = APIClient.getApiInterface().updateReview(rm)
            callServicProviders.enqueue(object : Callback<JsonElement> {
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    destroyDialog()
                    ShowToast(getString(R.string.thankyou_feedback))
                    finish()
                    overridePendingTransition(0,0)
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    destroyDialog()
                    finish()
                    ShowToast(t.localizedMessage)
                    overridePendingTransition(0,0)
                }
            })

            /*if (reviewDescriptionET.text.toString().trim().isNotEmpty()) {
                showProgressDialog("")
                val callServicProviders = LoginAPI.loginUser().updateReview(rm)
                callServicProviders.enqueue(object : Callback<JsonElement> {
                    override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                        destroyDialog()
                        ShowToast("Thank you for the feedback")
                        finish()
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        destroyDialog()
                        finish()
                        ShowToast(t.localizedMessage)
                    }
                })
            } else {
                ShowToast("Enter the feedback")
            }*/
        }
    }

    private fun loadCustomerProfile(custId: String?) {
        showProgressDialog("")
        val callServicProviders = APIClient.getApiInterface().getCustomerProfile(custId!!.toInt())
        callServicProviders.enqueue(object : Callback<CustomerCompleteProfileAfterUpdate> {
            override fun onResponse(call: Call<CustomerCompleteProfileAfterUpdate>, response: Response<CustomerCompleteProfileAfterUpdate>) {
                destroyDialog()
                if (response.isSuccessful) {
                    if (response?.body()?.status == 1) {
                        ServiceRequestorName.text = response?.body()?.data!![0]?.name!!
                        Picasso.get().load(response?.body()?.data!![0]?.image).placeholder(R.drawable.user_dummy_avatar).error(R.drawable.user_dummy_avatar).into(serviceManIVP)
                        thumbsUpTVR.text = response?.body()?.data!![0]?.totalTumbsUp.toString()
                        thumbsDownTVR.text = response?.body()?.data!![0]?.totalTumbsDown.toString()
                        customerReviewCL.visibility = View.VISIBLE
                    } else {
                        ShowToast(response?.body()!!.message!!)

                    }
                }
            }

            override fun onFailure(call: Call<CustomerCompleteProfileAfterUpdate>, t: Throwable) {
                destroyDialog()
                customerReviewCL.visibility = View.VISIBLE
            }
        })
    }
}
