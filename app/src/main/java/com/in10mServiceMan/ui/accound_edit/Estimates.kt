package com.in10mServiceMan.ui.accound_edit


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.in10mServiceMan.ui.activities.BaseFragment

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.accound_edit.updateEstimate.IUpdateEstimateView
import com.in10mServiceMan.ui.accound_edit.updateEstimate.UpdateEstimatePresenter
import com.in10mServiceMan.ui.accound_edit.updateEstimate.UpdateEstimateResponse
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_estimates.view.*

/**
 * A simple [Fragment] subclass.
 */
class Estimates : BaseFragment(), IUpdateEstimateView {
    var freeEstimate = true
    var estimate = false
    var estimationStatus = 0
    var estimationFee = "0"

    var mUpdateEstimatePresenter = UpdateEstimatePresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_estimates, container, false)

        val mServiceManId =
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.USER_ID, "")

        view.FreeEstimateCL.setOnClickListener {
            view!!.selectRoundIVFreeEstimate.setImageResource(R.drawable.select_radio_one)
            view.selectRoundIVPaidEstimate.setImageResource(R.drawable.unselect_radio_one)
            freeEstimate = true
            estimate = false
            view.TripEstimateFeeET.visibility = View.GONE
        }

        view.TripChargeCL.setOnClickListener {
            view!!.selectRoundIVFreeEstimate.setImageResource(R.drawable.unselect_radio_one)
            view.selectRoundIVPaidEstimate.setImageResource(R.drawable.select_radio_one)
            freeEstimate = false
            estimate = true
            view.TripEstimateFeeET.visibility = View.VISIBLE
        }

        view.enterButtonEstimateAndFee.setOnClickListener {
            if (freeEstimate && !estimate) {
                estimationStatus = 1
                estimationFee = "0"
            } else {
                estimationStatus = 0
                estimationFee = view.TripEstimateFeeET.toString()
            }

            showProgressDialog("")
            mServiceManId?.let { it1 ->
                mUpdateEstimatePresenter.updateEstimate(
                    it1.toInt(),
                    estimationStatus.toString(),
                    estimationFee
                )
            }
        }

        return view
    }

    fun showToast(msg: String) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateEstimateCompleted(mPost: UpdateEstimateResponse) {
        destroyDialog()
        if (mPost.status == 1) {
            showToast("Estimate Updated")
        } else {
            showToast("Something went wrong")
        }
    }

    override fun onUpdateEstimateFailed(msg: String) {
        destroyDialog()
        showToast(msg)
    }
}
