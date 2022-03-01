package com.in10mServiceMan.ui.activities.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.company_registration.CompanyResourceActivity
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_estimation.*

class EstimationActivity : In10mBaseActivity(), ISignupview {
    override fun onNumberVerified(mData: SignupOneResponse) {
    }

    override fun onSignUpFirstCompleted(mResponse: SignupOneResponse) {

    }

    override fun onFailed(msg: String) {
        destroyDialog()
        ShowToast(msg)
    }

    override fun onProfilePictureUpdated(mData: SignupstepTwoResponse) {

    }

    override fun onStepThreeCompleted(mData: SignupThreeResponse) {
        destroyDialog()
        if (mData.status == 1) {
            val gson = Gson()
            val res = gson.toJson(mData?.data)
            SharedPreferencesHelper.putString(
                this,
                Constants.SharedPrefs.User.PROFILE_PICTURE,
                mData?.data?.image!!
            )
            SharedPreferencesHelper.putString(
                this,
                Constants.SharedPrefs.User.LEVEL_THREE_DATA,
                res
            )

            if (SharedPreferencesHelper.getInt(
                    this,
                    Constants.SharedPrefs.User.PERSON_TYPE,
                    2
                ) == 2
            ) {
                startActivity(Intent(this, ProfileSuccessActivity::class.java))
            } else if (SharedPreferencesHelper.getInt(
                    this,
                    Constants.SharedPrefs.User.PERSON_TYPE,
                    3
                ) == 3
            ) {
                startActivity(Intent(this, CompanyResourceActivity::class.java))
            }
        } else {
            ShowToast(mData?.message)
        }
    }

    val mPresenter = SignupPresenter(this)
    var mEtimateType = "2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estimation)
        APIClient.token =
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")

        FreeEstimateCL.setOnClickListener {
            selectRoundIVFreeEstimate.setImageResource(R.drawable.select_radio_one)
            selectRoundIVPaidEstimate.setImageResource(R.drawable.unselect_radio_one)
            TripEstimateFeeET.visibility = View.GONE
            TripEstimatePromotionTT.visibility = View.GONE
            Constants.GlobalSettings.isFreeEstimate = true
            SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.FREE_ESTIMATE, "1")
            mEtimateType = "1"

        }
        TripChargeCL.setOnClickListener {
            selectRoundIVFreeEstimate.setImageResource(R.drawable.unselect_radio_one)
            selectRoundIVPaidEstimate.setImageResource(R.drawable.select_radio_one)
            TripEstimateFeeET.visibility = View.VISIBLE
            TripEstimatePromotionTT.visibility = View.VISIBLE
            Constants.GlobalSettings.isFreeEstimate = false
            SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.FREE_ESTIMATE, "2")
            mEtimateType = "2"
        }

        enterButtonEstimateAndFee.setOnClickListener {
            val estimationFess = TripEstimateFeeET.text.toString()
            val accountNo = SharedPreferencesHelper.getString(
                this,
                Constants.SharedPrefs.User.ACCOUNT_NUMBER,
                ""
            )
            val routinNo = SharedPreferencesHelper.getString(
                this,
                Constants.SharedPrefs.User.ROUTING_NUMBER,
                ""
            )
            val ssn =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.SSN_NUMBER, "")
            val userID =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
            val userEmail =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.EMAIL, "")
            val expiryMonth =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.EXPIRY_MONTH, "")
            val expiryYear =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.EXPIRY_YEAR, "")
            val cardNo =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.CARD_NUMBER, "")
            val street = SharedPreferencesHelper.getString(
                this,
                Constants.SharedPrefs.User.SM_ADDRESS_ONE,
                ""
            )
            val house = SharedPreferencesHelper.getString(
                this,
                Constants.SharedPrefs.User.SM_ADDRESS_TWO,
                ""
            )
            val state =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.SM_STATE, "")
            val city =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.SM_CITY, "")
            val zip =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.SM_ZIP_CODE, "")
            val country =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.COUNTRY, "")


            if (Constants.GlobalSettings.isCashPaid) {
                showProgressDialog("")
                mPresenter.signupLevelThreeDirectCash(
                    userID.toString(),
                    userEmail.toString(),
                    mEtimateType,
                    estimationFess,
                    SharedPreferencesHelper.getString(
                        this,
                        Constants.SharedPrefs.User.DATE_OF_BIRTH,
                        ""
                    ).toString(),
                    "1"
                )
            } else {
                if (Constants.GlobalSettings.isBankAccount && !Constants.GlobalSettings.isDebitCard) {
                    showProgressDialog("")
                    mPresenter.signupLevelThreeBankPay(
                        userID.toString(),
                        userEmail.toString(),
                        mEtimateType,
                        estimationFess,
                        "2",
                        accountNo.toString(),
                        routinNo.toString(),
                        ssn.toString(),
                        "1",
                        street.toString(),
                        house.toString(),
                        city.toString(),
                        state.toString(),
                        country.toString(),
                        zip.toString()
                    )
                } else {
                    showProgressDialog("")
                    mPresenter.signupLevelThreeDebitCard(
                        userID.toString(),
                        userEmail.toString(),
                        mEtimateType,
                        estimationFess,
                        "2",
                        expiryMonth.toString(),
                        expiryYear.toString(),
                        cardNo.toString(),
                        "2",
                        street.toString(),
                        house.toString(),
                        city.toString(),
                        state.toString(),
                        country.toString(),
                        ssn.toString(),
                        zip.toString()
                    )
                }
            }

        }

    }
}
