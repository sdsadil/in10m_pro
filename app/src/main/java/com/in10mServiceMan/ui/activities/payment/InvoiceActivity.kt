package com.in10mServiceMan.ui.activities.payment


import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater.*
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatTextView
import com.google.firebase.database.*
import com.google.gson.Gson
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.create_online_account.CreateOnlinePaymentAccountActivity
import com.in10mServiceMan.ui.activities.rating.CustomerRating
import com.in10mServiceMan.ui.apis.LoginAPI
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.activity_invoice.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class InvoiceActivity : In10mBaseActivity() {


    var bookingId = ""
    var customerId = ""
    var serviceId = ""
    var paymentSelected = "1"
//    var paymentSelected = "2"
    lateinit var mDatabase: DatabaseReference
    lateinit var dbServiceMan: DatabaseReference
    var isNodeAvailable: Boolean = false
    var mAlertDialog: AlertDialog? = null
    var mAlertDialogConfirm: AlertDialog? = null
    var totalAmount: String = ""
    var workDescription: String = ""
    var isRequote: Boolean = false
    var tripCharge:Double=0.0
    var request: PaymentRequestClass = PaymentRequestClass()

    override fun onResume() {
        super.onResume()
        Log.e("Invoice Activity","onResume")

        setOnlinVisibility()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("Invoice Activity","onCreate")
        setContentView(R.layout.activity_invoice)
        val userid =
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0")?.toInt()

        backButton.setOnClickListener {
            finish()
        }

        if (intent.extras != null) {
            bookingId = intent.getStringExtra("bookingId").toString()
            customerId = intent.getStringExtra("customerId").toString()
            tripCharge = intent.getDoubleExtra("tripCharge",0.0)
            serviceId = intent.getStringExtra("serviceId").toString()
        }
        Log.i("booking / customer", "$bookingId $customerId")

        val paymentNodePath = "payments/$customerId"//"payments/$bookingId"
        mDatabase = FirebaseDatabase.getInstance().getReference(paymentNodePath)

        val serviceManNodePath = "bookings/service_men/$userid"
        dbServiceMan = FirebaseDatabase.getInstance().getReference(serviceManNodePath)

        val storage = localStorage(this@InvoiceActivity)



        OnlinePayCL.setOnClickListener {
            selectRoundIVOnlineOnly.setImageResource(R.drawable.select_radio_one)
            selectRoundIVCash.setImageResource(R.drawable.unselect_radio_one)
            paymentSelected = "2"
        }
        CashPayCL.setOnClickListener {
            selectRoundIVCash.setImageResource(R.drawable.select_radio_one)
            selectRoundIVOnlineOnly.setImageResource(R.drawable.unselect_radio_one)
            paymentSelected = "1"
        }
        EnableOnlineCL.setOnClickListener {
            startActivity(Intent(this@InvoiceActivity, CreateOnlinePaymentAccountActivity::class.java))
        }
        if(tripCharge>0.0){
            feePayDescriptionET.setText(tripCharge.toString())
        }
        submitClickLL.setOnClickListener {

            if (feePayDescriptionET.text.toString().trim().isNotEmpty() && feePayDescriptionET.text.toString().trim().toDouble() != 0.0)
                makePaymentInitialize()
            else
                ShowToast("Enter fee amount")
        }
        CashPayCL.callOnClick()

        mDatabase.ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val data = p0.getValue(PaymentRequestClass::class.java)
                if (data?.booking_Id == bookingId) {

                    if (!data.accepted_status) {
                        mAlertDialog?.cancel()
                        isRequote = true
                        customerCancelQuoteMessageTV.visibility = View.VISIBLE
                        Toast.makeText(this@InvoiceActivity,"Customer has requested for a re-quote. Please update amount and generate new invoice",Toast.LENGTH_LONG).show()
                    } else if (data.pay_status == true) {
                        if (paymentSelected == "2") {
                            mAlertDialog?.cancel()
                            //mDatabase.child(bookingId).setValue(null)
                            displayAlertDialogConfirmation(totalAmount, "Received by online payment", true)

                        } else if (paymentSelected == "1") {
                            mAlertDialog?.cancel()
                            //mDatabase.child(bookingId).setValue(null)
                            displayAlertDialogConfirmation(totalAmount, "Received by cash payment", false)

                        }
                    }
                    //else if (data?.getIs_editing() == true) {
                    //                        mAlertDialog?.cancel()
                    //                    }
                }
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {
                try {
                    val data = p0.getValue(PaymentRequestClass::class.java)
                    if (data?.booking_Id == bookingId) {
                        /*mAlertDialog?.cancel()*/
                        Log.d("remove response", data.booking_Id!!)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    private fun displayAlertDialogInvoice() {
        val mDialogView = from(this).inflate(R.layout.payment_request_dialog, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        mAlertDialog = mBuilder.show()
        mAlertDialog!!.setCanceledOnTouchOutside(false)
        mAlertDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val button = mDialogView.findViewById(R.id.paymentCancelButton) as TextView
        val editButton = mDialogView.findViewById(R.id.paymentEditButton) as LinearLayout

        val serviceTv = mDialogView.findViewById(R.id.serviceAmountTV) as AppCompatTextView
        val in10mTv = mDialogView.findViewById(R.id.in10mAmountTV) as AppCompatTextView
        val amountTv = mDialogView.findViewById(R.id.AmountTV) as AppCompatTextView
       // if (data.contains("."))
            amountTv.text = getFormattedAmt(request.total_amount)
//        else
//            amountTv.text = "$$data.00"
        serviceTv.text = getFormattedAmt(request.service_amount.toString())
        in10mTv.text = getFormattedAmt(request.applicationFee?.toString())
        button.setOnClickListener {
            mAlertDialog!!.cancel()

            request.editing_status = true
            mDatabase.child(bookingId).setValue(request)
            isRequote = true
        }
        editButton.setOnClickListener {
            mAlertDialog!!.cancel()
            request.editing_status = true
            mDatabase.child(bookingId).setValue(request)
            isRequote = true
        }
    }
    private fun getFormattedAmt(amt:String?):String{
        val myAmt= amt ?: "0.00"
        return if (myAmt.contains("."))
            "$$myAmt"
        else
            "$$myAmt.00"
    }

    private fun displayAlertDialogConfirmation(amount: String, text: String, isOnlinePay: Boolean) {
        val mDialogView = from(this).inflate(R.layout.confirm_payment_dialog, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        mAlertDialogConfirm = mBuilder.show()
        mAlertDialogConfirm!!.setCanceledOnTouchOutside(false)
        mAlertDialogConfirm!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val typeImage = mDialogView.findViewById(R.id.greenAssetIV) as ImageView
        val statusText = mDialogView.findViewById(R.id.cashReceiveStatusTV) as AppCompatTextView
        statusText.text = text
        if (isOnlinePay)
            typeImage.setImageResource(R.drawable.online_green_card)
        else
            typeImage.setImageResource(R.drawable.cash_green)
        val button = mDialogView.findViewById(R.id.submitClickLL) as LinearLayout
        val amountTv = mDialogView.findViewById(R.id.AmountTV) as AppCompatTextView
        if (amount.contains("."))
            amountTv.text = "KD $amount"
        else
            amountTv.text = "KD $amount.00"


        button.setOnClickListener {
            mAlertDialogConfirm!!.cancel()
            mDatabase.child(bookingId).removeValue()//.setValue(null)
            dbServiceMan.child(bookingId).removeValue()
            startActivity(Intent(this@InvoiceActivity, CustomerRating::class.java).putExtra("bookingId", bookingId)
                    .putExtra("customerId", customerId)
                    .putExtra("serviceId", serviceId))
            finish()
        }
    }


    private fun makePaymentInitialize() {
        showProgressDialog("")
        val header = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val userId = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
        val bookingID = bookingId
        totalAmount = feePayDescriptionET.text.toString().trim()
        workDescription = workDescriptionET.text.toString().trim()

        val paymentType = paymentSelected

        val callServiceProviders = LoginAPI.loginUser().paymentInitilize(header, bookingID, totalAmount, paymentType, userId, workDescription)

        callServiceProviders.enqueue(object : Callback<PaymentInitilizeResponse> {
            override fun onResponse(call: Call<PaymentInitilizeResponse>, response: Response<PaymentInitilizeResponse>) {
                destroyDialog()
                if (response.isSuccessful) {
                    Log.i("code", response.code().toString())
                    Log.i("response", Gson().toJson(response.body()))
                    //Log.i("error", response.errorBody()!!.string())
                    if (response.body()?.status == 1) {

                        request.booking_Id = bookingID
                        if (response.body()!!.data!!.applicationFee != null)
                            request.applicationFee = response.body()!!.data!!.applicationFee!!
                        else
                            request.applicationFee = 0.0
                        if (response.body()!!.data!!.feePercent != null)
                            request.fee_percent = response.body()!!.data!!.feePercent!!
                        else
                            request.fee_percent = ""
                       // request.total_amount = totalAmount
                        request.customer_id = customerId
                        request.pay_status = false
                        request.accepted_status = true
                        request.editing_status = false
                        if (response.body()!!.data!!.serviceFee != null)
                            request.service_amount = response.body()!!.data!!.servicemanServiceAmount
                        else
                            request.service_amount = 0.0
                        request.payment_type = paymentType.toInt()
                        if (response.body()!!.data!!.processingFee != null)
                            request.processingFee = response.body()!!.data!!.processingFee!!
                        else
                            request.processingFee = 0.0
                        request.service_man_id = userId
                        if (response.body()!!.data!!.tax != null)
                            request.tax = response.body()!!.data!!.tax!!.toDouble()
                        else
                            request.tax = 0.0
                        if (response.body()!!.data!!.taxPercent != null)
                            request.tax_percent = response.body()!!.data!!.taxPercent!!
                        else
                            request.tax_percent = 0.0
                        if (response.body()!!.data!!.totalAmount != null) {
                            request.total_amount = response.body()!!.data!!.totalAmount.toString()
                            totalAmount = response.body()!!.data!!.totalAmount.toString()
                        }
                            else
                            request.total_amount = ""

                        Log.d("request class", request.toString())
                        try {
                            Log.d("Request class", Gson().toJson(request).toString())
                            if (!isRequote) {
                                mDatabase.child(bookingID).setValue(request)
                            } else {
                                mDatabase.child(bookingID).setValue(null)
                                mDatabase.child(bookingID).setValue(request)
                                isRequote = false
                                customerCancelQuoteMessageTV.visibility = View.GONE
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        displayAlertDialogInvoice()
                    } else {
                        ShowToast(response.body()?.message!!)
                    }
                } else {
                    Log.i("error response", Gson().toJson(response.errorBody()?.string()))
                }
            }

            override fun onFailure(call: Call<PaymentInitilizeResponse>, t: Throwable) {
                destroyDialog()
                t.message?.let { Log.i("eeeERRRO", it) }
                Log.i("eeeERRRO", t.localizedMessage)
            }
        })
    }

    private fun setOnlinVisibility(){
        if(SharedPreferencesHelper.getInt(this,Constants.SharedPrefs.User.SM_PAYMENT_TYPE,0) == 2) {
//            EnableOnlineCL.visibility = View.INVISIBLE
            EnableOnlineCL.visibility = View.GONE
            OnlinePayCL.visibility = View.VISIBLE
        }else{
//            EnableOnlineCL.visibility = View.VISIBLE
            EnableOnlineCL.visibility =  View.GONE
            OnlinePayCL.visibility = View.INVISIBLE
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        //startActivity(Intent(this@InvoiceActivity, MapTrackingActivity::class.java))
    }
}
