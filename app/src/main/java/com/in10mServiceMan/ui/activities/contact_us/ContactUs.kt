package com.in10mServiceMan.ui.activities.contact_us

import com.in10mServiceMan.ui.base.In10mBaseActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import com.google.gson.Gson
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_contact_us.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.net.Uri


class ContactUs : In10mBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        button_close.setOnClickListener {
            finish()
        }
        lvBtnSubmit.setOnClickListener {
            if (description.text.toString().trim().isEmpty())
                ShowToast(resources.getString(R.string.please_enter_your_query))
            else
                putQuery(description.text.toString())
        }
        textView29.movementMethod = LinkMovementMethod.getInstance()
        imageView2.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:12027797977")
            startActivity(callIntent)
        }
    }

    private fun putQuery(description: String) {


        val userId = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
        val userName = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.EMAIL, "")
        val header = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")

        showProgressDialog("")
        val callServiceProviders = APIClient.getApiInterface().putQueries(header, description, userName, userId)
        callServiceProviders.enqueue(object : Callback<ContactResponse> {
            override fun onResponse(call: Call<ContactResponse>, response: Response<ContactResponse>) {
                destroyDialog()
                if (response.isSuccessful)
                    if (response.body()?.status == 1) {
                        finish()
                        ShowToast(resources.getString(R.string.your_query_successfully))
                        Log.i("duty response", response.body().toString())
                    } else {
                        Log.e("Duty change error", Gson().toJson(response.errorBody()).toString())
                        ShowToast(resources.getString(R.string.something_went_wrong))
                    }
            }

            override fun onFailure(call: Call<ContactResponse>, t: Throwable) {
                destroyDialog()
                Log.e("Duty change error", t.message.toString())

            }
        })
    }
}
