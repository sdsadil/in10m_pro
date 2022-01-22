package com.in10mServiceMan.ui.activities.create_online_account

import android.content.Intent
import com.in10mServiceMan.ui.base.In10mBaseActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.payment.InvoiceActivity
import com.in10mServiceMan.utils.Constants
import kotlinx.android.synthetic.main.activity_create_online_payment_account.*

class CreateOnlinePaymentAccountActivity : In10mBaseActivity() {

    var bookingId = ""
    var customerId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_online_payment_account)

        if (intent.extras != null) {
            bookingId = intent.getStringExtra("bookingId").toString()
            customerId = intent.getStringExtra("customerId").toString()

        }

        Log.i("booking / customer", "$bookingId $customerId")
        iv_back.setOnClickListener {
            Constants.GlobalSettings.fromPayment = false
            onBackPressed()
        }

        pushFragment(CreateOnlinePaymentAccountFragment())
    }

    fun pushFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager

        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(R.id.col_frag_container, fragment).addToBackStack(fragment.tag).commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1){
            Constants.GlobalSettings.fromPayment = false
            this.finish()
        }else{
            supportFragmentManager.popBackStackImmediate()
        }
    }

    fun finishThisAndCallRecallInvoice(){
        if (Constants.GlobalSettings.fromPayment)   {
            finish()
        }
        else {
            startActivity(Intent(this@CreateOnlinePaymentAccountActivity, InvoiceActivity::class.java).putExtra("bookingId", bookingId)
                    .putExtra("customerId", customerId))
        }
        //finish()
    }
}
