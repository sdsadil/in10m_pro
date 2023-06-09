package com.in10mServiceMan.ui.activities.company_registration


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.in10mServiceMan.R
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_signup_company_contact.view.*

class SignupCompanyContactFragment : Fragment() {


    interface NextFragmentInterfaceTwo {
        fun toNextFragmentTwo()
    }

    companion object {
        private var mListener: NextFragmentInterfaceTwo? = null
        fun newInstance(mNextFragmentListener: NextFragmentInterfaceTwo): SignupCompanyContactFragment {
            mListener = mNextFragmentListener
            return SignupCompanyContactFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup_company_contact, container, false)

        view.enterButton.setOnClickListener {
            if (view.contactDetailsEmail.text.toString().isEmpty()) {
                showToast("Please enter Valid e-mail address")
            } else if (view.contactDetailsMobile.text.toString().isEmpty() && view.contactDetailsMobile.text.toString().length != 10) {
                showToast("Please enter Valid Mobile number")
            } else if (view.contactDetailsPassword.text.toString().isEmpty()) {
                showToast("Please enter the Password ")
            } else if (view.contactDetailsPassword.text!!.length < 8) {
                showToast("Password must be atleast 8 characters")
            } else {
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.EMAIL, view.contactDetailsEmail.text.toString())
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.MOBILE_NUMBER, view.contactDetailsMobile.text.toString())
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.PASSWORD, view.contactDetailsPassword.text.toString())

                mListener?.toNextFragmentTwo()
            }

        }
        return view
    }

    private fun showToast(msg: String) {
        Toast.makeText(this.context!!, msg, Toast.LENGTH_SHORT).show()
    }


}
