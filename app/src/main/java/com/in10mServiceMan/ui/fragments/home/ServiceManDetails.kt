package com.in10mServiceMan.ui.fragments.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import com.in10mServiceMan.R
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.in10mServiceMan.utils.RequestCode


class ServiceManDetails(): DialogFragment() {

    var serviceManCallBack:ServiceManCallBack? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_serviceman_info, container, false)

        rootView.findViewById<ImageView>(R.id.button_close).setOnClickListener(View.OnClickListener { dismiss() })

        val action=arguments!!.getInt("action")
        if(action==RequestCode.FromHome)
        {
            rootView.findViewById<Button>(R.id.btnCancelBooking).visibility=View.GONE
            rootView.findViewById<LinearLayout>(R.id.lvCallButtonView).visibility=View.GONE
            rootView.findViewById<Button>(R.id.callButtonView).visibility=View.GONE

            rootView.findViewById<LinearLayout>(R.id.lvButtonView).visibility=View.VISIBLE
            rootView.findViewById<Button>(R.id.btnBookNow).visibility=View.VISIBLE
        }else{
            rootView.findViewById<Button>(R.id.btnCancelBooking).visibility=View.VISIBLE
            rootView.findViewById<LinearLayout>(R.id.lvCallButtonView).visibility=View.VISIBLE
            rootView.findViewById<Button>(R.id.callButtonView).visibility=View.VISIBLE

            rootView.findViewById<LinearLayout>(R.id.lvButtonView).visibility=View.GONE
            rootView.findViewById<Button>(R.id.btnBookNow).visibility=View.GONE
        }

        rootView.findViewById<Button>(R.id.btnShowOnMap).setOnClickListener{
            serviceManCallBack?.showOnMap()
            dismiss()
        }
        rootView.findViewById<Button>(R.id.btnCancelBooking).setOnClickListener{
            serviceManCallBack?.cancelBooking()
            dismiss()
        }
        rootView.findViewById<LinearLayout>(R.id.lvButtonView).setOnClickListener{
            serviceManCallBack?.bookNow()
            dismiss()
        }
        rootView.findViewById<Button>(R.id.btnBookNow).setOnClickListener{
            serviceManCallBack?.bookNow()
            dismiss()
        }
        rootView.findViewById<LinearLayout>(R.id.lvCallButtonView).setOnClickListener{
            serviceManCallBack?.callNow()
            dismiss()
        }
        rootView.findViewById<Button>(R.id.callButtonView).setOnClickListener {
            serviceManCallBack?.callNow()
            dismiss()
        }

        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        serviceManCallBack= context as ServiceManCallBack
    }


}
interface ServiceManCallBack{
    fun bookNow()
    fun callNow()
    fun showOnMap()
    fun cancelBooking()
    //fun favBtnClicked()
}
