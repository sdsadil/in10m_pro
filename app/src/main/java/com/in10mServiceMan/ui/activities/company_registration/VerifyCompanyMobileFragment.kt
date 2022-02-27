package com.in10mServiceMan.ui.activities.company_registration


import android.os.Bundle
import androidx.appcompat.widget.AppCompatEditText
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.in10mServiceMan.ui.activities.BaseFragment

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.signup.SignupOneResponse
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.GenericOtpTextWatcher
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_verify_company_mobile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyCompanyMobileFragment : BaseFragment() {
    interface NextFragmentInterfaceThree {
        fun toNextFragmentThree(otp: String)
    }

    companion object {
        var otp: String = ""
        private var mListener: NextFragmentInterfaceThree? = null
        fun newInstance(mNextFragmentListener: NextFragmentInterfaceThree, otp: String): VerifyCompanyMobileFragment {
            mListener = mNextFragmentListener
            this.otp = otp
            return VerifyCompanyMobileFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_verify_company_mobile, container, false)

        view!!.setOnTouchListener { view, motionEvent -> return@setOnTouchListener true }
//        view.enterCode.hint = otp
        val mobile = SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.MOBILE_NUMBER, "")
        view.mobileDescription.text = "4 digits code has been sent to the mobile number " + replaceCentreFour(
            mobile.toString()
        )
        view.termsOfUseTV.text = Html.fromHtml("By clicking to “VERIFY”, I agree to the <font color=#4A90E2><u><a href=\"http://3.81.20.120/in10m_termscondition.html\"> Terms of Use</a> </u></font> and <font color=#4A90E2><u><a href=\"http://3.81.20.120/in10m_privacypolicy.html\">Privacy Policy</a></u></font> ")
        view.termsOfUseTV.movementMethod = LinkMovementMethod.getInstance()

        val otpET1: AppCompatEditText = view.findViewById<AppCompatEditText>(R.id.et_otp1)
        val otpET2: AppCompatEditText = view.findViewById<AppCompatEditText>(R.id.et_otp2)
        val otpET3: AppCompatEditText = view.findViewById<AppCompatEditText>(R.id.et_otp3)
        val otpET4: AppCompatEditText = view.findViewById<AppCompatEditText>(R.id.et_otp4)
        var otpText:String = ""

        GenericOtpTextWatcher.handleOtpTextWatcher(otpET1,otpET2,otpET3,otpET4)

        view.verifyButton.setOnClickListener {
            otpText = otpET1.text.toString()+otpET2.text.toString()+otpET3.text.toString()+otpET4.text.toString()
            if (otpText.length < 4) {
                Toast.makeText(this.context, "Enter the received OTP", Toast.LENGTH_SHORT).show()
            } else {
//            if (view.enterCode.text.toString() == otp) {
                /* mListener?.toNextFragmentThree(view.enterCode.text.toString())*/

                val header = SharedPreferencesHelper.getString(context, Constants.SharedPrefs.User.AUTH_TOKEN, "")
                val email = SharedPreferencesHelper.getString(context, Constants.SharedPrefs.User.EMAIL, "")
                val mobile = SharedPreferencesHelper.getString(context, Constants.SharedPrefs.User.MOBILE_NUMBER, "")

                showProgressDialog("")
                verifyMobile(header.toString(), otpText, email.toString(), mobile.toString())
            }
        }
        return view
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
            override fun onResponse(call: Call<SignupOneResponse>, response: Response<SignupOneResponse>) {
                destroyDialog()
                if (response.isSuccessful) {
                    Log.d("response ", response.body().toString())
                    if (response.body()?.status == 1) {
                        SharedPreferencesHelper.putString(context, Constants.SharedPrefs.User.AUTH_TOKEN, response.body()?.data!![0]?.apiToken!!)
                        APIClient().publicAccessToken = response.body()?.data!![0]?.apiToken!!

                        mListener?.toNextFragmentThree("")
                    } else {
                        Toast.makeText(context, response.body()?.message!!, Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<SignupOneResponse>, t: Throwable) {
                destroyDialog()
                Log.d("error", t.localizedMessage)

            }
        })
    }
}
