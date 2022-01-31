package com.in10mServiceMan.ui.activities.rating

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_reviews.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewsActivity : In10mBaseActivity() {

    private lateinit var reviewAdapter: ReviewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)
        showProgressDialog("")
        initRV()
        initViews()
    }

    private fun initViews() {
        noReviewsTV.visibility = View.GONE
        button_close.setOnClickListener {
            finish()
        }
    }

    private fun initRV() {
        val userId = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
        val header = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val callServiceProviders = APIClient.getApiInterface().getOwnReviews("Bearer $header", userId)
        callServiceProviders.enqueue(object : Callback<ReviewsResponse> {
            override fun onResponse(call: Call<ReviewsResponse>, response: Response<ReviewsResponse>) {
                destroyDialog()
                if (response.isSuccessful) {
                    val reviewsData = response.body()

                    if (reviewsData?.status == 1) {
                        reviewAdapter = ReviewsAdapter(this@ReviewsActivity, reviewsData.data as List<ReviewsData>)
                        reviewRV.layoutManager = LinearLayoutManager(this@ReviewsActivity)
                        reviewRV.adapter = reviewAdapter
                    } else {
                        Log.d("error Response", response.message())
                        noReviewsTV.visibility = View.VISIBLE
                        reviewRV.visibility = View.GONE
                    }
                } else {
                    Log.d("error Response", response.message())
                    noReviewsTV.visibility = View.VISIBLE
                    reviewRV.visibility = View.GONE

                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                destroyDialog()
                Log.d("error Response", t.localizedMessage)
                noReviewsTV.visibility = View.VISIBLE
                reviewRV.visibility = View.GONE

            }
        })
    }

}
