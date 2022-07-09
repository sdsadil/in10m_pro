package com.in10mServiceMan.ui.activities.signup


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.widget.AppCompatEditText
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.in10mServiceMan.ui.activities.BaseFragment
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.GenericOtpTextWatcher
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.widgets.textview.CustomBookTextView
import kotlinx.android.synthetic.main.fragment_verify_mobile.*
import kotlinx.android.synthetic.main.fragment_verify_mobile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class VerifyMobileFragment : BaseFragment() {
    companion object {
        var otp: String = ""
        private var mListener: NextFragmentInterfaceThree? = null
        fun newInstance(
            mNextFragmentListener: NextFragmentInterfaceThree,
            otp: String
        ): VerifyMobileFragment {
            mListener = mNextFragmentListener
            this.otp = otp
            return VerifyMobileFragment()
        }
    }

    var countDownTime: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_verify_mobile, container, false)

        view!!.setOnTouchListener { view, motionEvent -> return@setOnTouchListener true }

        val mobile = SharedPreferencesHelper.getString(
            this.context,
            Constants.SharedPrefs.User.MOBILE_NUMBER,
            ""
        )
//        view.mobileDescription.text =
//            (getString(R.string.code) + " " + replaceCentreFour(mobile.toString()))
        view.termsOfUseTV.text =
            Html.fromHtml(
                getString(R.string.verify_1) + " " + "<font color=#4A90E2><u><a href=\"http://3.81.20.120/in10m_termscondition.html\"> " + getString(
                    R.string.verify_2
                ) + "</a> </u></font> & <font color=#4A90E2><u><a href=\"http://3.81.20.120/in10m_privacypolicy.html\">" + getString(
                    R.string.verify_3
                ) + "</a></u></font> "
            )
        view.termsOfUseTV.movementMethod = LinkMovementMethod.getInstance()
        countDown(view.tvResendCode)
        /* view.termsCheckBox.setOnClickListener {
             mListener?.termsAndConditions()
         }*/

        val otpET1 = view.findViewById<AppCompatEditText>(R.id.et_otp1)
        val otpET2 = view.findViewById<AppCompatEditText>(R.id.et_otp2)
        val otpET3 = view.findViewById<AppCompatEditText>(R.id.et_otp3)
        val otpET4 = view.findViewById<AppCompatEditText>(R.id.et_otp4)
        var otpText = ""
        otpET1.requestFocus()
        GenericOtpTextWatcher.handleOtpTextWatcher(otpET1, otpET2, otpET3, otpET4)

        view.verifyButton.setOnClickListener {
            otpText =
                otpET1.text.toString() + otpET2.text.toString() + otpET3.text.toString() + otpET4.text.toString()
            if (!termsCheckBox.isChecked) {
                Toast.makeText(
                    this.context,
                    resources.getString(R.string.please_accept_terms_privacy),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (otpText.length < 4) {
                Toast.makeText(
                    this.context,
                    resources.getString(R.string.enter_the_received_OTP),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                /* if(view.enterCode.text.toString() == otp) {
                     mListener?.toNextFragmentThree(view.enterCode.text.toString())
                 }*/
                /*mListener?.toNextFragmentThree(view.enterCode.text.toString())*/

                val header = SharedPreferencesHelper.getString(
                    context,
                    Constants.SharedPrefs.User.AUTH_TOKEN,
                    ""
                )
                val email =
                    SharedPreferencesHelper.getString(context, Constants.SharedPrefs.User.EMAIL, "")
                val mobile = SharedPreferencesHelper.getString(
                    context,
                    Constants.SharedPrefs.User.MOBILE_NUMBER,
                    ""
                )

                showProgressDialog("")
                verifyMobile(header.toString(), otpText, email.toString(), mobile.toString())
            }
        }


        view.tvResendCode.setOnClickListener {
            callResendOtp(view.tvResendCode)
        }
        return view
    }

    private fun callResendOtp(tvResendCode: CustomBookTextView) {
        tvResendCode.isEnabled = false

        val registerStepOne = APIClient.getApiInterface().SignupOne(
            2,
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.FIRST_NAME, "")
                .toString(),
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.LAST_NAME, "")
                .toString(),
            SharedPreferencesHelper.getString(
                activity,
                Constants.SharedPrefs.User.DATE_OF_BIRTH,
                ""
            )
                .toString(),
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.STREET, "")
                .toString(),
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.SUITE, "")
                .toString(),
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.CITY, "")
                .toString(),
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.STATE, "")
                .toString(),
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.COUNTRY, "")
                .toString(),
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.ZIPCODE, "")
                .toString(),
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.EMAIL, "")
                .toString(),
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.COUNTRY_CODE, "")
                .toString(),
            SharedPreferencesHelper.getString(
                activity,
                Constants.SharedPrefs.User.MOBILE_NUMBER,
                ""
            )
                .toString(),
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.PASSWORD, "")
                .toString(),
            SharedPreferencesHelper.getString(
                activity,
                Constants.SharedPrefs.User.SERVICES_PROVIDED_STRING,
                ""
            ).toString()
        )
        registerStepOne.enqueue(object : Callback<SignupOneResponse> {
            override fun onResponse(
                call: Call<SignupOneResponse>,
                response: Response<SignupOneResponse>
            ) {
                if (response.isSuccessful) {
                    val mResponse: SignupOneResponse = response.body()!!
                    if (mResponse.status == 1) {
                        SharedPreferencesHelper.putString(
                            activity,
                            Constants.SharedPrefs.User.AUTH_TOKEN,
                            mResponse.data!![0]?.apiToken!!
                        )
                        APIClient().publicAccessToken = mResponse.data?.get(0)?.apiToken!!

                        SharedPreferencesHelper.putString(
                            activity,
                            Constants.SharedPrefs.User.USER_ID,
                            mResponse.data[0]?.customerData!![0]?.customerId.toString()
                        )
                        SharedPreferencesHelper.putString(
                            activity,
                            Constants.SharedPrefs.User.EMAIL,
                            mResponse.data[0]?.customerData!![0]?.customerEmail.toString()
                        )
                        SharedPreferencesHelper.putString(
                            activity,
                            Constants.SharedPrefs.User.MOBILE_NUMBER,
                            mResponse.data[0]?.customerData!![0]?.customerMobile.toString()
                        )
                        countDown(tvResendCode)
                    } else {
                        Toast.makeText(
                            context,
                            mResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        this@VerifyMobileFragment.tvResendCode!!.isEnabled = true
                    }
                } else {
                    this@VerifyMobileFragment.tvResendCode!!.isEnabled = true
                }
            }

            override fun onFailure(call: Call<SignupOneResponse>, t: Throwable) {
//                listener.onFailed(t.localizedMessage)
                this@VerifyMobileFragment.tvResendCode!!.isEnabled = true
            }
        })
    }

    private fun replaceLastFour(s: String): String {
        val length = s.length
        //Check whether or not the string contains at least four characters; if not, this method is useless
        return if (length < 4) "****" else s.substring(0, length - 4) + "****"
    }

    private fun replaceCentreFour(s: String): String {
        val length = s.length
        var data = ""
        //Check whether or not the string contains at least four characters; if not, this method is useless
        data = if (length < 4) {
            "****"
        } else {
            s.substring(0, 2) + "******" + s.substring(8)
        }
        return data
    }

    private fun verifyMobile(header: String, otp: String, email: String, mobile: String) {

        val request = APIClient.getApiInterface().verifyOtp("Bearer $header", otp, mobile, email)
        request.enqueue(object : Callback<SignupOneResponse> {
            override fun onResponse(
                call: Call<SignupOneResponse>,
                response: Response<SignupOneResponse>
            ) {
                destroyDialog()
                if (response.isSuccessful) {
                    Log.d("response ", response.body().toString())
                    if (response.body()?.status == 1) {
                        SharedPreferencesHelper.putString(
                            context,
                            Constants.SharedPrefs.User.AUTH_TOKEN,
                            response.body()?.data!![0]?.apiToken!!
                        )
                        APIClient().publicAccessToken = response.body()?.data!![0]?.apiToken!!
                        countDownTime!!.cancel()
                        mListener?.toNextFragmentThree("")
                    } else {
                        Toast.makeText(context, response.body()?.message!!, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<SignupOneResponse>, t: Throwable) {
                destroyDialog()
                Log.d("error", t.localizedMessage)

            }
        })
    }

    fun openactivity(activity: Activity) {
        val intent = Intent(activity, activity::class.java)
        startActivity(intent)
    }

    private fun countDown(tvResendCode: CustomBookTextView) {
        tvResendCode.isEnabled = false
        countDownTime = object : CountDownTimer(60000, 1000) {
            override fun onTick(duration: Long) {
                tvResendCode.text = activity!!.resources.getString(R.string.resend_30)
                    .toString() + " " + String.format(
                    "%02d",
                    TimeUnit.MILLISECONDS.toSeconds(duration) % 60
                )
            }

            override fun onFinish() {
                countDownTime!!.cancel()
                tvResendCode.text = activity!!.resources.getString(R.string.resend_otp)
                tvResendCode.isEnabled = true
            }
        }.start()
    }

    override fun onResume() {
        if (Constants.GlobalSettings.signupTerms)
            Constants.GlobalSettings.signupTerms = false
        super.onResume()
    }


    interface NextFragmentInterfaceThree {
        fun toNextFragmentThree(otp: String)
        fun termsAndConditions()
    }
}
