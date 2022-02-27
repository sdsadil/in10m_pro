package com.in10mServiceMan.ui.activities

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.view.Window
import android.view.inputmethod.InputMethodManager
import com.in10mServiceMan.R
import com.in10mServiceMan.utils.AppProgressBar
import com.in10mServiceMan.utils.LoadingDialog
import androidx.appcompat.app.AppCompatActivity


open class BaseFragment : Fragment() {
    private var mProgressDialog: LoadingDialog? = null
    var dialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mProgressDialog = LoadingDialog(activity)
    }


    fun hideKeyboard() {
        try {
            val view = activity!!.currentFocus
            if (view != null) {
                val inputManager =
                    activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun openFragment(fragment: Fragment) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        activity!!.supportFragmentManager.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        if (fragment.isAdded)
            transaction.show(fragment)
        else {
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }
    }

    fun openFragmentAndBackStack(fragment: Fragment) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        if (fragment.isAdded)
            transaction.show(fragment)
        else {
            transaction.add(R.id.container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    fun getVisibleFragment(): Fragment? {
        val fragmentManager = activity!!.supportFragmentManager
        val fragments = fragmentManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible)
                    return fragment
            }
        }
        return null
    }

    fun showProgressDialog(mContent: String) {
//        mProgressDialog = LoadingDialog(activity)
//        mProgressDialog!!.showProgressDialog(mContent, false)

//        mProgressDialog = new LoadingDialog(this);
//        mProgressDialog.showProgressDialog(getString(mContent));
        AppProgressBar.showProgressDialog(activity as AppCompatActivity?)

    }

    fun showProgressDialog(mContent: String, mCancelable: Boolean) {
//        mProgressDialog = LoadingDialog(activity)
//        mProgressDialog!!.showProgressDialog(mContent, mCancelable)
        AppProgressBar.showProgressDialog(activity as AppCompatActivity?)
    }

    fun destroyDialog() {
        /*  try {
           if (mProgressDialog != null) {
               mProgressDialog.destroyDialog();
           }
           mProgressDialog = null;
       } catch (Exception mE) {
       }*/
        AppProgressBar.dismissProgressDialog()
    }

}