package com.in10mServiceMan.ui.fragments.service_history


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.in10mServiceMan.ui.activities.BaseFragment

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.my_bookings.BookingHistoryInterface
import com.in10mServiceMan.ui.activities.my_bookings.ServiceHistoryResponse
import com.in10mServiceMan.ui.activities.my_bookings.invoice_details_API.IInvoiceDetailsView
import com.in10mServiceMan.ui.activities.my_bookings.invoice_details_API.InvoiceDetailsPresenter
import com.in10mServiceMan.ui.activities.my_bookings.invoice_details_API.InvoiceDetailsResponse
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.fragments.past_bookings.BookingsAdapter
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.alert_invoice.view.*
import kotlinx.android.synthetic.main.fragment_completed_history.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class CompletedHistory : BaseFragment(), BookingHistoryInterface, IInvoiceDetailsView {
    private lateinit var bookingsAdapter: BookingsAdapter
    var mPresenter = InvoiceDetailsPresenter(this)

    private var isStarted = false
    private var isVisiblee = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_completed_history, container, false)
        return view
    }

    private fun getServiceHistory() {
        showProgressDialog("")
        val user = localStorage(activity).loggedInUser
        val token =
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        if (!token.isNullOrEmpty()) {
            val header = SharedPreferencesHelper.getString(
                activity,
                Constants.SharedPrefs.User.AUTH_TOKEN,
                ""
            )
            val userId = SharedPreferencesHelper.getString(
                activity,
                Constants.SharedPrefs.User.USER_ID,
                "0"
            )!!
                .toInt()
            val callServiceProviders = APIClient.getApiInterface()
                .getServiceHistory("Bearer $header", userId, "6", 150, 1)//user.customerId
            callServiceProviders.enqueue(object : Callback<ServiceHistoryResponse> {
                override fun onResponse(
                    call: Call<ServiceHistoryResponse>,
                    response: Response<ServiceHistoryResponse>
                ) {
                    destroyDialog()
                    if (response.isSuccessful) {
                        destroyDialog()
                        if (response.body()!!.data.isNotEmpty()) {
                            view!!.noDataFound.visibility = View.GONE
                            bookingsAdapter = BookingsAdapter(
                                this@CompletedHistory.requireContext(),
                                response.body()!!.data,
                                this@CompletedHistory
                            )
                            view!!.completedRV.layoutManager =
                                LinearLayoutManager(this@CompletedHistory.requireContext())
                            view!!.completedRV.adapter = bookingsAdapter
                        } else {
                            destroyDialog()
                            view!!.noDataFound.visibility = View.VISIBLE
                            //Toast.makeText(this@CompletedHistory.requireContext(),"No Data Found", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        destroyDialog()
                        view!!.noDataFound.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ServiceHistoryResponse>, t: Throwable) {
                    destroyDialog()
                    view!!.noDataFound.visibility = View.VISIBLE
                }
            })
        }
    }

    override fun onInvoiceDetailsCompleted(mPost: InvoiceDetailsResponse) {
        destroyDialog()
        if (mPost.status == 1) {
            val moduleAlert = LayoutInflater.from(activity).inflate(R.layout.alert_invoice, null)
            val moduleBuilder = AlertDialog.Builder(activity).setView(moduleAlert)
            moduleAlert.serviceCost.text = "KD " + mPost.data.serviceman_charge.toString()
            moduleAlert.charges.text = "KD " + mPost.data.in10m_fee.toString()
            moduleAlert.totalAmount.text = "KD " + mPost.data.amount_paid.toString()
            if (mPost.data.payment_method == 1) {
                moduleAlert.onlineId.visibility = View.GONE
                moduleAlert.cashHeader.text = resources.getString(R.string.received_by_cash)
            } else {
                moduleAlert.onlineId.visibility = View.VISIBLE
                moduleAlert.cashHeader.text =
                    resources.getString(R.string.received_by_online_payment)

                if (mPost.data.transaction_id != "null" || mPost.data.transaction_id != null)
                    moduleAlert.onlineId.text = mPost.data.transaction_id.toString()
            }
            val moduleDialogue = moduleBuilder!!.show()
        } else {
            Toast.makeText(activity!!, mPost.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onInvoiceDetailsFailed(msg: String) {
        destroyDialog()
        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show()
    }

    override fun adapterTransaction() {
        showProgressDialog("")
        val userId =
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.USER_ID, "0")!!
                .toInt()
        mPresenter.invoiceDetails(
            SharedPreferencesHelper.getInt(
                activity,
                Constants.SharedPrefs.User.COMMON_ID,
                0
            ), userId, 2
        )
    }

    override fun onStart() {
        super.onStart()
        isStarted = true
        if (isVisiblee) getServiceHistory()

    }

    override fun onStop() {
        super.onStop()
        isStarted = false
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isVisiblee = isVisibleToUser
        if (isVisiblee && isStarted) getServiceHistory()

    }

}
