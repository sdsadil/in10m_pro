package com.in10mServiceMan.ui.activities.company_pros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.BaseActivity
import com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API.IServicemanDetailsView
import com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API.ServicemanDetailsPresenter
import com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API.ServicemanDetailsResponse
import com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API.ServicemanDetailsReview
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_company_pros_details.*

class CompanyProsDetails : BaseActivity(), IServicemanDetailsView {
    override fun onServicemanDetailsCompleted(mPost: ServicemanDetailsResponse) {
        destroyDialog()
        if (mPost.status == 1)  {
            bodyLayout.visibility = View.VISIBLE
            Glide.with(this).load(mPost.data[0].image).placeholder(R.drawable.dummy_user).into(serviceManIV)
            ServiceManName.text = mPost.data[0].name + " " + mPost.data[0].lastname
            thumbsUpTV.text = mPost.data[0].total_tumbs_up.toString()
            thumbsDownTV.text = mPost.data[0].total_tumbs_down.toString()
            textView9.text = mPost.data[0].dob

            if (mPost.data[0].certificate == 1)
                tvCertified.text = "Yes"
            else
                tvCertified.text = "No"

            recyclerView(mPost.data[0].reviews)
        }
        else    {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onServicemanDetailsFailed(msg: String) {
        destroyDialog()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    var mServicemanDetailsPresenter = ServicemanDetailsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_pros_details)

        val servicemanId = SharedPreferencesHelper.getInt(this, Constants.SharedPrefs.User.COMMON_ID, 0)

        showProgressDialog("")
        //mServicemanDetailsPresenter.servicemanDetails(10)
        mServicemanDetailsPresenter.servicemanDetails(servicemanId)

        btn_close.setOnClickListener {
            finish()
        }
    }

    fun recyclerView(mData: List<ServicemanDetailsReview>)  {
        listSliderReview.layoutManager = LinearLayoutManager(this)
        listSliderReview.adapter = CompanyReviewDetailsAdapter(mData, this)
    }
}
