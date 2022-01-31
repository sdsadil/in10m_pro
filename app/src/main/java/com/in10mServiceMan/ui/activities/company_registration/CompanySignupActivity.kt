package com.in10mServiceMan.ui.activities.company_registration

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.util.Log
import com.google.gson.Gson
import com.in10mServiceMan.models.CustomerCompleteProfile
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.BackButtonHandler
import com.in10mServiceMan.ui.activities.BaseActivity
import com.in10mServiceMan.ui.activities.sign_in.ILoginView
import com.in10mServiceMan.ui.activities.sign_in.LinkSendResponse
import com.in10mServiceMan.ui.activities.sign_in.LoginPresenter
import com.in10mServiceMan.ui.activities.sign_in.LoginResponse
import com.in10mServiceMan.ui.activities.signup.SignupOneResponse
import com.in10mServiceMan.ui.activities.signup.SignupThreeResponse
import com.in10mServiceMan.ui.activities.signup.SignupViewPagerAdapter
import com.in10mServiceMan.ui.activities.signup.SignupstepTwoResponse
import com.in10mServiceMan.ui.activities.tracking_map.MapTrackingActivity
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.activity_company_signup.*

class CompanySignupActivity : BaseActivity(), ICompanySignupView,
    SignupCompanyDetailsFragment.NextFragmentInterfaceOne,
    SignupCompanyContactFragment.NextFragmentInterfaceTwo,
    VerifyCompanyMobileFragment.NextFragmentInterfaceThree,
    CompanyProfilePictureFragment.NextFragmentInterfaceFour,
    CompanyPaymentTypeFragment.NextFragmentInterfaceSix, ILoginView {

    override fun toNextFragmentSix() {

    }

    override fun toNextFragmentFour(imageUri: String) {
        showProgressDialog("")
        val header =
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")?.let {
            mPresenter.signupLevelTwo(
                "Bearer $header", it,
                imageUri
            )
        }
    }

    override fun toNextFragmentThree(otp: String) {
        removeFragment()

        /*val header = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val email = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.EMAIL, "")
        val mobile = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.MOBILE_NUMBER, "")
        showProgressDialog("")
        mPresenter.verifyMobile(header, otp, email, mobile)*/
        signUpPhaseViewPager.currentItem = signUpPhaseViewPager.currentItem + 1
    }

    override fun onSignUpFirstCompleted(mResponse: SignupOneResponse) {
        destroyDialog()
        if (mResponse.status == 1) {
            var bundle = Bundle()
            Log.d("OTP Received", mResponse?.data!![0]?.customerData!![0]?.otp.toString())
            bundle.putString("otp", mResponse?.data!![0]?.customerData!![0]?.otp.toString())

            /*val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("CopiedText", mResponse?.data!![0]?.customerData!![0]?.otp.toString())
            clipboard.primaryClip = clipData*/

            SharedPreferencesHelper.putString(
                this,
                Constants.SharedPrefs.User.AUTH_TOKEN,
                mResponse?.data!![0]?.apiToken!!
            )
            APIClient().publicAccessToken = mResponse?.data!![0]?.apiToken!!

            SharedPreferencesHelper.putString(
                this,
                Constants.SharedPrefs.User.USER_ID,
                mResponse?.data!![0]?.customerData!![0]?.customerId.toString()
            )
            SharedPreferencesHelper.putString(
                this,
                Constants.SharedPrefs.User.EMAIL,
                mResponse?.data!![0]?.customerData!![0]?.customerEmail.toString()
            )
            SharedPreferencesHelper.putString(
                this,
                Constants.SharedPrefs.User.MOBILE_NUMBER,
                mResponse?.data!![0]?.customerData!![0]?.customerMobile.toString()
            )

            VerifyCompanyMobileFragment().arguments = bundle
            openFragment(
                VerifyCompanyMobileFragment.newInstance(
                    this,
                    mResponse?.data!![0]?.customerData!![0]?.otp.toString()
                )
            )
        } else {
            ShowToast(mResponse.message.toString())
        }

    }

    override fun onFailed(msg: String) {
        destroyDialog()
        ShowToast(msg)
    }

    override fun onProfilePictureUpdated(mData: SignupstepTwoResponse) {
        destroyDialog()
        if (mData.status == 1) {

            Log.d("Response signup two", Gson().toJson(mData).toString())

            if (mData.data?.imageUrl != null)
                SharedPreferencesHelper.putString(
                    this@CompanySignupActivity,
                    Constants.SharedPrefs.User.PROFILE_PICTURE,
                    mData?.data?.imageUrl!!
                )
            if (mData.data?.address1 != null) {
                SharedPreferencesHelper.putString(
                    this@CompanySignupActivity,
                    Constants.SharedPrefs.User.SM_ADDRESS_ONE,
                    mData?.data?.address1!!
                )
                Constants.GlobalSettings.streetName = mData.data.address1
            }
            if (mData.data?.address2 != null) {
                SharedPreferencesHelper.putString(
                    this@CompanySignupActivity,
                    Constants.SharedPrefs.User.SM_ADDRESS_TWO,
                    mData.data.address2
                )
                Constants.GlobalSettings.aptNo = mData.data.address2
            }
            if (mData.data?.streetName != null) {
                SharedPreferencesHelper.putString(
                    this@CompanySignupActivity,
                    Constants.SharedPrefs.User.SM_STREET_NAME,
                    mData.data.streetName
                )
            }
            if (mData.data?.city != null) {
                SharedPreferencesHelper.putString(
                    this@CompanySignupActivity,
                    Constants.SharedPrefs.User.SM_CITY,
                    mData.data.city
                )
                Constants.GlobalSettings.cityName = mData.data.city
            }
            if (mData.data?.state != null) {
                SharedPreferencesHelper.putString(
                    this@CompanySignupActivity,
                    Constants.SharedPrefs.User.SM_STATE,
                    mData.data.state
                )
                Constants.GlobalSettings.stateName = mData.data.state
            }
            if (mData.data?.zipcode != null) {
                SharedPreferencesHelper.putString(
                    this@CompanySignupActivity,
                    Constants.SharedPrefs.User.SM_ZIP_CODE,
                    mData.data.zipcode
                )
                Constants.GlobalSettings.zipCode = mData.data.zipcode
            }


            if (SharedPreferencesHelper.getInt(
                    this,
                    Constants.SharedPrefs.User.PERSON_TYPE,
                    0
                ) == 2
            ) {
                showProgressDialog("")
                mLoginPresenter.getCompleteProfile(
                    SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
                        .toString()
                )
            } else {
                signUpPhaseViewPager.currentItem = signUpPhaseViewPager.currentItem + 1
            }
        } else {
            ShowToast(mData.message)
        }

    }

    override fun onStepThreeCompleted(mData: SignupThreeResponse) {
    }

    override fun onNumberVerified(mData: SignupOneResponse) {
        /*destroyDialog()
        if (mData.status == 1) {
            SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.AUTH_TOKEN, mData?.data!![0]?.apiToken!!)
            LoginAPI().publicAccessToken = mData?.data!![0]?.apiToken!!
            signUpPhaseViewPager.currentItem = signUpPhaseViewPager.currentItem + 1
        } else {
            ShowToast(mData.message!!)
        }*/
    }

    override fun toNextFragmentTwo() {
        showProgressDialog("")
        mPresenter.signUpUserCompany(
            2,
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.FIRST_NAME, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.LAST_NAME, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.DATE_OF_BIRTH, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.STREET, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.SUITE, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.CITY, "").toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.STATE, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.COUNTRY, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.ZIPCODE, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.EMAIL, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.COUNTRY_CODE, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.MOBILE_NUMBER, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.PASSWORD, "")
                .toString(),
            SharedPreferencesHelper.getString(
                this,
                Constants.SharedPrefs.User.SERVICES_PROVIDED_STRING,
                ""
            ).toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.NAME_OF_COMPANY, "")
                .toString()
        )
    }

    override fun toNextFragmentOne() {
        signUpPhaseViewPager.currentItem = signUpPhaseViewPager.currentItem + 1
    }

    private val mPresenter = CompanySignupPresenter(this)
    val mLoginPresenter = LoginPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_signup)

        formViewpagerAdapter()
        if (SharedPreferencesHelper.getInt(this, Constants.SharedPrefs.User.PERSON_TYPE, 0) == 2) {
            if (intent.extras != null) {
                if (intent.getStringExtra("step") == "1") {
                    signUpPhaseViewPager.currentItem = 2
                } else if (intent.getStringExtra("step") == "2") {
                    startActivity(Intent(this, MapTrackingActivity::class.java))
                }
            }
        } else {
            if (intent.extras != null) {
                if (intent.getStringExtra("step") == "1") {
                    signUpPhaseViewPager.currentItem = 2
                } else if (intent.getStringExtra("step") == "2") {
                    signUpPhaseViewPager.currentItem = 3
                } else if (intent.getStringExtra("step") == "3") {
                    startActivity(Intent(this, MapTrackingActivity::class.java))
                }
            }
        }
    }

    private fun formViewpagerAdapter() {

        val adapter = SignupViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(SignupCompanyDetailsFragment.newInstance(this), "1")
        adapter.addFragment(SignupCompanyContactFragment.newInstance(this), "2")
        adapter.addFragment(CompanyProfilePictureFragment.newInstance(this), "3")
        adapter.addFragment(CompanyPaymentTypeFragment.newInstance(this), "4")
        /*adapter.addFragment(PaymentTypeFragment.newInstance(this), "5")*/
        signUpPhaseViewPager.adapter = adapter
        signUpPhaseViewPagerIndicator.setViewPager(signUpPhaseViewPager)
        signUpPhaseViewPagerIndicator.setDotsClickable(false)
    }

    private fun openFragmentWithBack(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (fragment.isAdded)
            transaction.show(fragment)
        else {
            transaction.replace(R.id.signUpPhaseContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        if (fragment.isAdded)
            transaction.show(fragment)
        else {
            transaction.replace(R.id.signUpPhaseContainer, fragment)
            transaction.commit()
        }
    }

    private fun removeFragment() {
        supportFragmentManager.beginTransaction()
            .remove(supportFragmentManager.findFragmentById(R.id.signUpPhaseContainer)!!).commit()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if (signUpPhaseViewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            BackButtonHandler(this).onClick()
            return
        }
    }

    override fun onLoginCompleted(mResponse: LoginResponse) {

    }

    override fun onResetLinkSend(mResposne: LinkSendResponse) {

    }

    override fun onCompleteProfileReceived(metaData: CustomerCompleteProfile) {
        destroyDialog()
        if (metaData.status == 1) {
            localStorage(this).saveCompleteCustomer(metaData.data)
            val intent = Intent(baseContext, MapTrackingActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            ShowToast(metaData?.message)
        }
    }
}
