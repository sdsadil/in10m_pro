package com.in10mServiceMan.ui.accound_edit


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.in10m.ui.activities.BaseFragment
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.accound_edit.CheckStripe.CheckStripePresenter
import com.in10mServiceMan.ui.accound_edit.CheckStripe.CheckStripeResponse
import com.in10mServiceMan.ui.accound_edit.CheckStripe.ICheckStripeView
import com.in10mServiceMan.ui.accound_edit.UpdatePayment.IUpdatePaymentView
import com.in10mServiceMan.ui.accound_edit.UpdatePayment.UpdatePaymentPresenter
import com.in10mServiceMan.ui.accound_edit.UpdatePayment.UpdatePaymentResponse
import com.in10mServiceMan.ui.activities.create_online_account.CreateOnlinePaymentAccountActivity
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_payment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class Payment : BaseFragment(), ICheckStripeView, IUpdatePaymentView {
    var cash = false
    var online = false
    var paymentType = 0
    var mContext: Context? = null
    private var myUserId = 0
    private var header = ""
    var mCheckStripePresenter = CheckStripePresenter(this)
    var mUpdatePaymentPresenter = UpdatePaymentPresenter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_payment, container, false)
        mContext = this.context

        val mServiceManId =
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.USER_ID, "")
        showProgressDialog("")
        if (mServiceManId != null) {
            mCheckStripePresenter.checkStripe(mServiceManId)
        }

        apiCall()

        view.CashOnlyCL.setOnClickListener {
            if (cash && online) {
                cash = false
                online = true
                view.selectRoundIVCashOnly.setImageResource(R.drawable.unselect_radio_one)
                view.selectRoundIVOnline.setImageResource(R.drawable.select_radio_one)
                paymentType = 3
            } else if (!cash && online) {
                cash = true
                online = true
                view.selectRoundIVCashOnly.setImageResource(R.drawable.select_radio_one)
                view.selectRoundIVOnline.setImageResource(R.drawable.select_radio_one)
                paymentType = 2
            } else if (cash && !online) {
                showToast("Select atleast 1 payment option")
            }
        }
        view.OnlineAndCashCL.visibility = View.GONE
        view.OnlineAndCashCL.setOnClickListener {
            if (cash && online) {
                cash = true
                online = false
                view.selectRoundIVCashOnly.setImageResource(R.drawable.select_radio_one)
                view.selectRoundIVOnline.setImageResource(R.drawable.unselect_radio_one)
                paymentType = 1
            } else if (cash && !online) {
                cash = true
                online = true
                view.selectRoundIVCashOnly.setImageResource(R.drawable.select_radio_one)
                view.selectRoundIVOnline.setImageResource(R.drawable.select_radio_one)
                paymentType = 2
            } else if (!cash && online) {
                showToast("Select atleast 1 payment option")
            }
        }

        view.createAccount.setOnClickListener {
            Constants.GlobalSettings.fromPayment = true
            startActivity(Intent(activity, CreateOnlinePaymentAccountActivity::class.java))
        }

        view.enterButtonPaymentType.setOnClickListener {
            if (!cash && !online) {
                showToast("Select atleast 1 payment option")
            } else {
                mUpdatePaymentPresenter.updatePayment(
                    mServiceManId!!.toInt(),
                    paymentType.toString()
                )
            }
        }

        return view
    }

    fun apiCall() {
        var isLoggedIn = false //localStorage(context).isLoggedIn
        isLoggedIn =
            !SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.AUTH_TOKEN, "")
                .isNullOrEmpty()

        if (isLoggedIn) {
            myUserId = SharedPreferencesHelper.getString(
                activity,
                Constants.SharedPrefs.User.USER_ID,
                "0"
            )!!
                .toInt()//localStorage(this@ProfileActivity).loggedInUser.customerId
            header =
                SharedPreferencesHelper.getString(
                    activity,
                    Constants.SharedPrefs.User.AUTH_TOKEN,
                    ""
                )
                    .toString()

            val callServiceProviders =
                APIClient.getApiInterface().checkStripAccount(header, myUserId.toString())
            callServiceProviders.enqueue(object : Callback<CheckStripeAccount> {
                override fun onResponse(
                    call: Call<CheckStripeAccount>,
                    response: Response<CheckStripeAccount>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.stripeAccount() == "0") {
                            view!!.createAccount.visibility = View.VISIBLE
                            view!!.createAccount.visibility = View.GONE
                        } else {
                            view!!.createAccount.visibility = View.GONE
                        }
                    } else {
                        showToast("Error in loading Complete profile")
                    }
                }

                override fun onFailure(call: Call<CheckStripeAccount>, t: Throwable) {
                    showToast("Something went wrong")
                }
            })
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        if (Constants.GlobalSettings.fromPayment) {
            Constants.GlobalSettings.fromPayment = false
            view!!.createAccount.visibility = View.GONE
            cash = true
            online = true
            view!!.selectRoundIVCashOnly.setImageResource(R.drawable.select_radio_one)
            view!!.selectRoundIVOnline.setImageResource(R.drawable.select_radio_one)
        }
        super.onResume()
    }

    override fun onUpdatePaymentCompleted(mPost: UpdatePaymentResponse) {
        destroyDialog()
        if (mPost.status == 1) {
            showToast("Payment Updated")
        }
    }

    override fun onUpdatePaymentFailed(msg: String) {
        destroyDialog()
        showToast(msg)
    }

    override fun onCheckStripeCompleted(mPost: CheckStripeResponse) {
        destroyDialog()
        if (mPost.status == 1) {
            if (mPost.stripe_account == 1) {
                view!!.createAccount.visibility = View.GONE
                cash = true
                online = true
                view!!.selectRoundIVCashOnly.setImageResource(R.drawable.select_radio_one)
                view!!.selectRoundIVOnline.setImageResource(R.drawable.select_radio_one)
            } else {
//                view!!.createAccount.visibility = View.VISIBLE
                view!!.createAccount.visibility = View.GONE
                cash = true
                online = false
                view!!.selectRoundIVCashOnly.setImageResource(R.drawable.select_radio_one)
                view!!.selectRoundIVOnline.setImageResource(R.drawable.unselect_radio_one)
            }
        } else {
            showToast("Something went wrong")
        }
    }

    override fun onCheckStripeFailed(msg: String) {
        destroyDialog()
        showToast(msg)
    }
}
