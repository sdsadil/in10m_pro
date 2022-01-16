package com.in10mServiceMan.ui.activities.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.services.AvailableServices
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_account_type.*

class AccountType : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_type)

        freelancerCL.setOnClickListener {
            selectRoundIVFreelancer.setImageResource(R.drawable.select_radio_one)
            selectRoundIVCompany.setImageResource(R.drawable.unselect_radio_one)
            SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.ACCOUNT_TYPE, "1")
            SharedPreferencesHelper.putInt(this, Constants.SharedPrefs.User.PERSON_TYPE, 2)
            startActivity(Intent(this, AvailableServices::class.java))
        }
        companyCL.setOnClickListener {
            selectRoundIVCompany.setImageResource(R.drawable.select_radio_one)
            selectRoundIVFreelancer.setImageResource(R.drawable.unselect_radio_one)
            SharedPreferencesHelper.putInt(this, Constants.SharedPrefs.User.PERSON_TYPE, 3)
            SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.ACCOUNT_TYPE, "2")
            startActivity(Intent(this, AvailableServices::class.java))
        }
    }
}
