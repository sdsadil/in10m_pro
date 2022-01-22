package com.in10mServiceMan.ui.activities.signup

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.in10mServiceMan.Models.CustomerCompleteProfile
import com.in10mServiceMan.Models.RequestVerifyOTP
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.BackButtonHandler
import com.in10mServiceMan.ui.activities.BaseActivity
import com.in10mServiceMan.ui.activities.company_registration.CompanyResourceActivity
import com.in10mServiceMan.ui.activities.sign_in.ILoginView
import com.in10mServiceMan.ui.activities.sign_in.LinkSendResponse
import com.in10mServiceMan.ui.activities.sign_in.LoginPresenter
import com.in10mServiceMan.ui.activities.sign_in.LoginResponse
import com.in10mServiceMan.ui.activities.splash.SplashActivity
import com.in10mServiceMan.ui.activities.tracking_map.MapTrackingActivity
import com.in10mServiceMan.ui.apis.LoginAPI
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity(), SignupDetailsFragment.NextFragmentInterfaceOne,
    SignupContactFragment.NextFragmentInterfaceTwo, ISignupview,
    VerifyMobileFragment.NextFragmentInterfaceThree,
    ProfilePictureFragment.NextFragmentInterfaceFour,
    CertificationDetailsFragment.NextFragmentInterfaceFive,
    PaymentTypeFragment.NextFragmentInterfaceSix {
    override fun termsAndConditions() {
        openactivity(SplashActivity())
    }

    override fun onNumberVerified(mData: SignupOneResponse) {
        /* destroyDialog()
         if (mData.status == 1) {
             SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.AUTH_TOKEN, mData?.data!![0]?.apiToken!!)
             LoginAPI().publicAccessToken = mData?.data!![0]?.apiToken!!
             signUpPhaseViewPager.currentItem = signUpPhaseViewPager.currentItem + 1
         } else {
             showToast(mData.message!!)
         }*/
    }

    override fun onStepThreeCompleted(mData: SignupThreeResponse) {
        destroyDialog()
        if (mData.status == 1) {
            val gson = Gson()
            val res = gson.toJson(mData.data)
            SharedPreferencesHelper.putString(
                this,
                Constants.SharedPrefs.User.PROFILE_PICTURE,
                mData.data?.image!!
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

    override fun toNextFragmentSix() {
        val userID =
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
        val userEmail =
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.EMAIL, "")

        mPresenter.signupLevelThreeDirectCash(
            userID.toString(),
            userEmail.toString(), "1", "", "1"
        )
    }

    override fun toNextFragmentFive(certificateStatus: String, stateId: String) {
        val header =
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        showProgressDialog("")


        /*mPresenter.signupLevelTwo(
            "Bearer $header",
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.SELECTED_IMAGE, "")
                .toString(),
            stateId, certificateStatus
        ) */
        mPresenter.signupLevelTwo(
            "Bearer $header",
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.SELECTED_IMAGE, "")
                .toString(),
            "", "0"
        )
    }


    override fun onProfilePictureUpdated(mData: SignupstepTwoResponse) {
        destroyDialog()
        Log.d("Response sign-up two", Gson().toJson(mData).toString())
        if (mData.status == 1) {
            if (mData.data?.imageUrl != null)
                SharedPreferencesHelper.putString(
                    this@SignUpActivity,
                    Constants.SharedPrefs.User.PROFILE_PICTURE,
                    mData.data.imageUrl
                )
            if (mData.data?.address1?.isNotEmpty()!!) {
                SharedPreferencesHelper.putString(
                    this@SignUpActivity,
                    Constants.SharedPrefs.User.SM_ADDRESS_ONE,
                    mData.data.address1
                )
                Constants.GlobalSettings.streetName = mData?.data?.address1
            }
            if (mData?.data?.address2?.isNotEmpty()!!) {
                SharedPreferencesHelper.putString(
                    this@SignUpActivity,
                    Constants.SharedPrefs.User.SM_ADDRESS_TWO,
                    mData?.data?.address2
                )
                Constants.GlobalSettings.aptNo = mData?.data?.address2
            }
            if (mData?.data?.streetName != null) {
                SharedPreferencesHelper.putString(
                    this@SignUpActivity,
                    Constants.SharedPrefs.User.SM_STREET_NAME,
                    mData?.data?.streetName
                )
            }
            if (mData?.data?.city?.isNotEmpty()!!) {
                SharedPreferencesHelper.putString(
                    this@SignUpActivity,
                    Constants.SharedPrefs.User.SM_CITY,
                    mData?.data?.city
                )
                Constants.GlobalSettings.cityName = mData?.data?.city
            }
            if (mData?.data?.state?.isNotEmpty()!!) {
                SharedPreferencesHelper.putString(
                    this@SignUpActivity,
                    Constants.SharedPrefs.User.SM_STATE,
                    mData?.data?.state
                )
                Constants.GlobalSettings.stateName = mData?.data?.state
            }
            if (mData?.data?.zipcode?.isNotEmpty()!!) {
                SharedPreferencesHelper.putString(
                    this@SignUpActivity,
                    Constants.SharedPrefs.User.SM_ZIP_CODE,
                    mData?.data?.zipcode
                )
                Constants.GlobalSettings.zipCode = mData?.data?.zipcode
            }

            signUpPhaseViewPager.currentItem = signUpPhaseViewPager.currentItem + 1
        } else {
            ShowToast(mData?.message)
        }
    }

    override fun toNextFragmentFour(imageUri: String) {
        SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.SELECTED_IMAGE, imageUri)
//        signUpPhaseViewPager.currentItem = signUpPhaseViewPager.currentItem + 1

        val header =
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        showProgressDialog("")
        mPresenter.signupLevelTwo(
            "Bearer $header",
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
                .toString(),
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.SELECTED_IMAGE, "")
                .toString(),
            "", "0"
        )
    }

    override fun toNextFragmentThree(otp: String) {
        removeFragment()

        signUpPhaseViewPager.currentItem = signUpPhaseViewPager.currentItem + 1
        /*val header = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val email = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.EMAIL, "")
        val mobile = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.MOBILE_NUMBER, "")
        showProgressDialog("")
        mPresenter.verifyMobile(header, otp, email, mobile)*/
    }

    override fun onSignUpFirstCompleted(mResponse: SignupOneResponse) {
        destroyDialog()
        if (mResponse.status == 1) {
            /*var bundle = Bundle()
            Log.d("OTP Received", mResponse?.data!![0]?.customerData!![0]?.otp.toString())
            bundle.putString("otp", mResponse?.data!![0]?.customerData!![0]?.otp.toString())*/
            /*val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("CopiedText", mResponse?.data!![0]?.customerData!![0]?.otp.toString())
            clipboard.primaryClip = clipData*/

            SharedPreferencesHelper.putString(
                this,
                Constants.SharedPrefs.User.AUTH_TOKEN,
                mResponse?.data!![0]?.apiToken!!
            )
            LoginAPI().publicAccessToken = mResponse?.data!![0]?.apiToken!!

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

            //VerifyMobileFragment().arguments = bundle
            openFragment(
                VerifyMobileFragment.newInstance(
                    this,
                    mResponse?.data!![0]?.customerData!![0]?.otp.toString()
                )
            )
        } else {
            ShowToast(mResponse.message)
        }

        // openFragment(VerifyMobileFragment())

    }

    override fun onFailed(msg: String) {
        destroyDialog()
        showToast(msg)
    }

    override fun toNextFragmentTwo() {
        //SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.ACCOUNT_TYPE, "2").toInt()
        showProgressDialog("")
        mPresenter.signUpUser(
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
            ).toString()
        )

        //openFragment(VerifyMobileFragment.newInstance(this, "1234"))

    }

    override fun toNextFragmentOne() {
        signUpPhaseViewPager.currentItem = signUpPhaseViewPager.currentItem + 1
    }

    val mPresenter = SignupPresenter(this)
    val mRegistrationStep: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        formViewpagerAdapter()

        if (intent.extras != null) {
            if (intent.getStringExtra("step") == "1") {
                signUpPhaseViewPager.currentItem = 2
            } else if (intent.getStringExtra("step") == "2") {
                signUpPhaseViewPager.currentItem = 4
            } else if (intent.getStringExtra("step") == "3") {
                startActivity(Intent(this, MapTrackingActivity::class.java))
            }

        }
    }

    private fun formViewpagerAdapter() {
        val adapter = SignupViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(SignupDetailsFragment.newInstance(this), "1")
        adapter.addFragment(SignupContactFragment.newInstance(this), "2")
        adapter.addFragment(ProfilePictureFragment.newInstance(this), "3")
//        adapter.addFragment(CertificationDetailsFragment.newInstance(this), "4")
        adapter.addFragment(PaymentTypeFragment.newInstance(this), "4")
        signUpPhaseViewPager.adapter = adapter
        signUpPhaseViewPagerIndicator.setDotsClickable(false)
        signUpPhaseViewPagerIndicator.setViewPager(signUpPhaseViewPager)
        signUpPhaseViewPagerIndicator.isEnabled = false
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

    override fun onBackPressed() {
        /*when {
            signUpPhaseViewPager.currentItem == 4 -> signUpPhaseViewPager.currentItem =
                    signUpPhaseViewPager.currentItem - 1
            signUpPhaseViewPager.currentItem == 3 -> signUpPhaseViewPager.currentItem =
                    signUpPhaseViewPager.currentItem - 1
            signUpPhaseViewPager.currentItem == 2 -> signUpPhaseViewPager.currentItem =
                    signUpPhaseViewPager.currentItem - 1
            signUpPhaseViewPager.currentItem == 1 -> signUpPhaseViewPager.currentItem =
                    signUpPhaseViewPager.currentItem - 1
            signUpPhaseViewPager.currentItem == 0 -> super.onBackPressed()
        }*/
        if (signUpPhaseViewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            BackButtonHandler(this).onClick()
            return
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun removeFragment() {
        supportFragmentManager.beginTransaction()
            .remove(supportFragmentManager.findFragmentById(R.id.signUpPhaseContainer)!!).commit()
    }

    fun openactivity(activity: Activity) {
        val intent = Intent(activity, activity::class.java)
        startActivity(intent)
    }
}
