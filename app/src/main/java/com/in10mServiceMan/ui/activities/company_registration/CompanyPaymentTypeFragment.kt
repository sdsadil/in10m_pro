package com.in10mServiceMan.ui.activities.company_registration


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatSpinner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.signup.EstimationActivity
import com.in10mServiceMan.ui.activities.signup.State
import com.in10mServiceMan.ui.activities.signup.StatesResponse
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.spinnerAdapter
import kotlinx.android.synthetic.main.fragment_company_payment_type.*
import kotlinx.android.synthetic.main.fragment_company_payment_type.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyPaymentTypeFragment : Fragment() {

    interface NextFragmentInterfaceSix {
        fun toNextFragmentSix()
    }

    var state: String = ""
    var city: String = ""
    var country: String = ""
    var country_id: String = ""
    private var mStatesList: List<State?>? = null
    var mContext: Context? = null

    companion object {
        private var mListener: NextFragmentInterfaceSix? = null
        fun newInstance(mNextFragmentListener: NextFragmentInterfaceSix): CompanyPaymentTypeFragment {
            mListener = mNextFragmentListener
            return CompanyPaymentTypeFragment()
        }
    }

    val monthArray = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
    val yearArray = mutableListOf("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030")

    var mView: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_company_payment_type, container, false)
        mView = view
        mContext = this.context
        getStates()
        preFillAddress()

        val cashRadio = view.findViewById(R.id.CashOnlyCL) as ConstraintLayout
        val onlineRadio = view.findViewById(R.id.OnlineAndCashCL) as ConstraintLayout
        val bankAccoutRadio = view.findViewById(R.id.BankAccountCL) as ConstraintLayout
        val debitCardRadio = view.findViewById(R.id.DebitCardCL) as ConstraintLayout
        val accountTypeCL = view.findViewById(R.id.accountTypeMainCL) as ConstraintLayout
        val accountDetailCL = view.findViewById(R.id.accountPaymentDetailsCL) as ConstraintLayout
        val debitCardDetailsCL = view.findViewById(R.id.DebitCardDetailsCL) as ConstraintLayout
        val bankAccountDetailsCL = view.findViewById(R.id.BankAccountDetailsCL) as ConstraintLayout
        val debitAndBankDetailsCL = view.findViewById(R.id.accountPaymentDetailsCL) as ConstraintLayout
        val addressDetailsCL = view.findViewById(R.id.paymentSectionAddressDetailsCL) as ConstraintLayout
        val monthSpinner = view.findViewById(R.id.AccountDetailsMonthSpinner) as AppCompatSpinner
        val yearSpinner = view.findViewById(R.id.AccountDetailsYearSpinner) as AppCompatSpinner
        var myTypeSpinner = view.findViewById(R.id.AccountDetailsStateSpinner) as AppCompatSpinner

        var monthAdapter = spinnerAdapter(mContext, R.layout.custom_simple_spinner_dropdown_item)
        monthAdapter.addAll(monthArray)
        monthAdapter.add("Expiry Month")
        monthSpinner.adapter = monthAdapter
        monthSpinner.setSelection(monthAdapter.count)

        var yearAdapter = spinnerAdapter(mContext, R.layout.custom_simple_spinner_dropdown_item)
        yearAdapter.addAll(yearArray)
        yearAdapter.add("Expiry Year")
        yearSpinner.adapter = yearAdapter
        yearSpinner.setSelection(yearAdapter.count)

        monthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>, view: View,
                    position: Int, id: Long
            ) {
                if (monthSpinner.selectedItem.toString() != "Expiry Month")
                    SharedPreferencesHelper.putString(mContext, Constants.SharedPrefs.User.EXPIRY_MONTH, monthSpinner.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>, view: View,
                    position: Int, id: Long
            ) {
                if (yearSpinner.selectedItem.toString() != "Expiry Year")
                    SharedPreferencesHelper.putString(mContext, Constants.SharedPrefs.User.EXPIRY_YEAR, yearSpinner.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


        myTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>, view: View,
                    position: Int, id: Long
            ) {
                if (mStatesList != null) {

                    state = mStatesList!![position]!!.name!!

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        Constants.GlobalSettings.isCashPaid = true
        Constants.GlobalSettings.isBankAccount = false
        Constants.GlobalSettings.isDebitCard = false

        cashRadio.setOnClickListener {
            accountTypeCL.visibility = View.GONE
            accountDetailCL.visibility = View.GONE
            view.selectRoundIVCashOnly.setImageResource(R.drawable.select_radio_one)
            view.selectRoundIVOnline.setImageResource(R.drawable.unselect_radio_one)
            addressDetailsCL.visibility = View.GONE
            debitAndBankDetailsCL.visibility = View.GONE
            Constants.GlobalSettings.isCashPaid = true
            SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.PAYOUT_TYPE, "1")
            Constants.GlobalSettings.isBankAccount = false
            Constants.GlobalSettings.isDebitCard = false
        }
        onlineRadio.setOnClickListener {
            accountTypeCL.visibility = View.VISIBLE
            accountDetailCL.visibility = View.VISIBLE
            view.selectRoundIVCashOnly.setImageResource(R.drawable.unselect_radio_one)
            view.selectRoundIVOnline.setImageResource(R.drawable.select_radio_one)
            addressDetailsCL.visibility = View.VISIBLE
            debitAndBankDetailsCL.visibility = View.VISIBLE
            Constants.GlobalSettings.isCashPaid = false
            Constants.GlobalSettings.isBankAccount = true
            SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.PAYOUT_TYPE, "2")
        }
        bankAccoutRadio.setOnClickListener {
            selectRoundIVBankAccount.setImageResource(R.drawable.select_radio_one)
            selectRoundIVDebitCard.setImageResource(R.drawable.unselect_radio_one)
            accountDetailCL.visibility = View.VISIBLE
            bankAccountDetailsCL.visibility = View.VISIBLE
            debitCardDetailsCL.visibility = View.GONE
            Constants.GlobalSettings.isBankAccount = true
            Constants.GlobalSettings.isDebitCard = false
            SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.PAYMENT_TYPE, "1")
        }
        debitCardRadio.setOnClickListener {
            selectRoundIVBankAccount.setImageResource(R.drawable.unselect_radio_one)
            selectRoundIVDebitCard.setImageResource(R.drawable.select_radio_one)
            accountDetailCL.visibility = View.VISIBLE
            debitCardDetailsCL.visibility = View.VISIBLE
            bankAccountDetailsCL.visibility = View.GONE
            monthSpinner.setSelection(monthAdapter.count)
            yearSpinner.setSelection(yearAdapter.count)
            Constants.GlobalSettings.isBankAccount = false
            Constants.GlobalSettings.isDebitCard = true
            SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.PAYMENT_TYPE, "2")

        }
        view.enterButtonPaymentType.setOnClickListener {
            val routingNo = view.AccountRoutingNoET.text.toString().trim()
            val accountNo = view.AccountDetailsACNoET.text.toString().trim()
            val ssn = view.AccountDetailsSSNET.text.toString().trim()
            val cardno = view.AccountDetailsCardNoET.text.toString().trim()

            if (Constants.GlobalSettings.isBankAccount) {

                if (ssn.length != 4)//!ssn.matches(Regex("[0-9]+")) &&
                {
                    showToast("Valid 4 digit SSN number required")
                } else if (routingNo.length != 9) //!routingNo.matches(Regex("[0-9]+")) &&
                {
                    showToast("Valid 9 digit routing number required")
                } else if (accountNo.length != 12) //!accountNo.matches(Regex("[0-9]+")) &&
                {
                    showToast("Valid 12 digit account number required")
                } else if (view.AccountDetailsStreetAddressET.text.toString().trim().isEmpty()) {
                    showToast("Please enter Street address")
                } else if (view.AccountDetailsSuiteET.text.toString().trim().isEmpty()) {
                    showToast("Please enter Apartment number")
                } else if (view.AccountDetailsCityTV.text.toString().trim().isEmpty()) {
                    showToast("Please enter your city ")
                } else if (view.AccountDetailsZipET.text.toString().trim().isEmpty()) {
                    showToast("Please enter your zipcode ")
                } else {
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.ROUTING_NUMBER, routingNo)
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.ACCOUNT_NUMBER, accountNo)
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.SSN_NUMBER, ssn)
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.SM_ADDRESS_TWO, view.AccountDetailsSuiteET.text.toString())
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.SM_ADDRESS_ONE, view.AccountDetailsStreetAddressET.text.toString())
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.SM_CITY, view.AccountDetailsCityTV.text.toString())
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.SM_ZIP_CODE, view.AccountDetailsZipET.text.toString())
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.SM_STATE, state)

                    startActivity(Intent(mContext!!, EstimationActivity::class.java))
                }
            } else if (Constants.GlobalSettings.isDebitCard) {

                if (ssn.length != 4) //!ssn.matches(Regex("[0-9]+")) &&
                {
                    showToast("Valid 4 digit SSN number required")
                } else if (cardno.length != 16) //!cardno.matches(Regex("[0-9]+")) &&
                {
                    showToast("Valid 16 digit Card number required")
                } else if (SharedPreferencesHelper.getString(mContext!!, Constants.SharedPrefs.User.EXPIRY_YEAR, "") == "EXPIRY_YEAR" || SharedPreferencesHelper.getString(mContext!!, Constants.SharedPrefs.User.EXPIRY_YEAR, "") == "") {
                    showToast("Select card expiry year")
                } else if (SharedPreferencesHelper.getString(mContext!!, Constants.SharedPrefs.User.EXPIRY_MONTH, "") == "EXPIRY_MONTH" || SharedPreferencesHelper.getString(mContext!!, Constants.SharedPrefs.User.EXPIRY_MONTH, "") == "") {
                    showToast("Select card expiry month")
                } else if (view.AccountDetailsStreetAddressET.text.toString().trim().isEmpty()) {
                    showToast("Please enter Street address")
                } else if (view.AccountDetailsSuiteET.text.toString().trim().isEmpty()) {
                    showToast("Please enter Apartment number")
                } else if (view.AccountDetailsCityTV.text.toString().trim().isEmpty()) {
                    showToast("Please enter your city ")
                } else if (view.AccountDetailsZipET.text.toString().trim().isEmpty()) {
                    showToast("Please enter your zipcode ")
                } else {
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.SSN_NUMBER, ssn)
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.CARD_NUMBER, cardno)
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.SM_ADDRESS_TWO, view.AccountDetailsSuiteET.text.toString())
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.SM_ADDRESS_ONE, view.AccountDetailsStreetAddressET.text.toString())
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.SM_CITY, view.AccountDetailsCityTV.text.toString())
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.SM_ZIP_CODE, view.AccountDetailsZipET.text.toString())
                    SharedPreferencesHelper.putString(mContext!!, Constants.SharedPrefs.User.SM_STATE, state)
                    startActivity(Intent(mContext!!, EstimationActivity::class.java))
                }
            } else {
                startActivity(Intent(mContext!!, EstimationActivity::class.java))
            }


        }
        /*view.AccountDetailsStreetAddressET.setText(SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.SM_ADDRESS_ONE, ""))
        view.AccountDetailsSuiteET.setText(SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.SM_ADDRESS_TWO, ""))
        view.AccountDetailsCityTV.setText(SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.SM_CITY, ""))
        view.AccountDetailsZipET.setText(SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.SM_ZIP_CODE, ""))
*/



        return view
    }

    private fun getStates() {
        val homeCall = APIClient.getApiInterface().states
        homeCall.enqueue(object : Callback<StatesResponse> {
            override fun onResponse(call: Call<StatesResponse>, response: Response<StatesResponse>) {
                if (response.isSuccessful) {
                    bindData(response.body()!!)
                } else {

                }
            }

            override fun onFailure(call: Call<StatesResponse>, t: Throwable) {

            }
        })


    }

    private fun bindData(body: StatesResponse) {
        var mPreSelectedState = SharedPreferencesHelper.getString(mContext, Constants.SharedPrefs.User.SM_STATE, "")
        var pos: Int = 0
        mStatesList = body?.data?.states
        country = body?.data?.countryCode!!
        country_id = body?.data?.countryId.toString()
        var myStateList: ArrayList<String>? = ArrayList()
        if (mStatesList?.size!! > 0) {
            for (i in 0 until mStatesList!!.size) {
                myStateList?.add(mStatesList?.get(i)?.name!!)
                if (mPreSelectedState == mStatesList?.get(i)?.name!!) {
                    pos = i
                }
            }
        }
        /* val adapter = ArrayAdapter(mContext, android.R.layout.simple_list_item_1, myStateList)
         AccountDetailsStateSpinner.adapter = adapter
         AccountDetailsStateSpinner.setSelection(pos)*/
        val adapter = spinnerAdapter(this.context, R.layout.custom_state_spinner_list)
        if (myStateList != null) {
            adapter.addAll(myStateList)
        }
        adapter.add("STATE")
        AccountDetailsStateSpinner.adapter = adapter
        AccountDetailsStateSpinner.setSelection(pos)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun preFillAddress() {
        if (SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.SM_ADDRESS_ONE, "")!!
                .isNotEmpty()) {
            mView!!.AccountDetailsStreetAddressET.setText(SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.SM_ADDRESS_ONE, ""))

        } else {
            mView!!.AccountDetailsStreetAddressET.setText(Constants.GlobalSettings.streetName)
        }
        if (SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.SM_ADDRESS_TWO, "")!!
                .isNotEmpty()) {
            mView!!.AccountDetailsSuiteET.setText(SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.SM_ADDRESS_TWO, ""))
        } else {
            mView!!.AccountDetailsSuiteET.setText(Constants.GlobalSettings.aptNo)
        }
        if (SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.SM_CITY, "")!!
                .isNotEmpty()) {
            mView!!.AccountDetailsCityTV.setText(SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.SM_CITY, ""))
        } else {
            mView!!.AccountDetailsCityTV.setText(Constants.GlobalSettings.cityName)
        }
        if (SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.SM_ZIP_CODE, "")!!
                .isNotEmpty()) {
            mView!!.AccountDetailsZipET.setText(SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.SM_ZIP_CODE, ""))

        } else {
            mView!!.AccountDetailsZipET.setText(Constants.GlobalSettings.zipCode)
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) {
            preFillAddress()
        }

    }
}
