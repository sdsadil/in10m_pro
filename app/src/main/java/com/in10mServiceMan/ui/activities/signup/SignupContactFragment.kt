package com.in10mServiceMan.ui.activities.signup


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.in10mServiceMan.R
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_signup_contact.view.*


class SignupContactFragment : Fragment() {

    interface NextFragmentInterfaceTwo {
        fun toNextFragmentTwo()
    }

    companion object {
        private var mListener: NextFragmentInterfaceTwo? = null
        fun newInstance(mNextFragmentListener: NextFragmentInterfaceTwo): SignupContactFragment {
            mListener = mNextFragmentListener
            return SignupContactFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup_contact, container, false)

        view.enterButton.setOnClickListener {
            if (!isValidEmail(view.contactDetailsEmail.text.toString()))//view.contactDetailsEmail.text.toString().trim().isEmpty()
            {
                showToast(resources.getString(R.string.please_enter_valid_email))
            } else if (view.contactDetailsMobile.text.toString().trim()
                    .isEmpty() && view.contactDetailsMobile.text.toString().length != 8
            ) {
                showToast(resources.getString(R.string.enter_valid_mobile_number))
            } else if (view.contactDetailsPassword.text.toString().trim().isEmpty()) {
                showToast(resources.getString(R.string.enter_your_password1))
            } else if (view.contactDetailsPassword.text!!.length < 8) {
                showToast(resources.getString(R.string.enter_your_password2))
            } else {
                SharedPreferencesHelper.putString(
                    this.context!!,
                    Constants.SharedPrefs.User.EMAIL,
                    view.contactDetailsEmail.text.toString()
                )
                SharedPreferencesHelper.putString(
                    this.context!!,
                    Constants.SharedPrefs.User.MOBILE_NUMBER,
                    view.contactDetailsMobile.text.toString()
                )
                SharedPreferencesHelper.putString(
                    this.context!!,
                    Constants.SharedPrefs.User.PASSWORD,
                    view.contactDetailsPassword.text.toString()
                )

                mListener?.toNextFragmentTwo()
            }

        }

        return view
    }

    private fun showToast(msg: String) {
        Toast.makeText(this.context!!, msg, Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}
