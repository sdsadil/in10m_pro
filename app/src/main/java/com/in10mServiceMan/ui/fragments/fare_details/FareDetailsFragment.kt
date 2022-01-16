package com.in10mServiceMan.ui.fragments.fare_details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.in10mServiceMan.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FareDetailsFragment : Fragment() {

    private var vehicleType =""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fare_details, container, false)
    }


    fun getInstance(vehicleType: String): FareDetailsFragment{

        val fragment= this
        fragment.vehicleType= vehicleType
        return fragment
    }
}
