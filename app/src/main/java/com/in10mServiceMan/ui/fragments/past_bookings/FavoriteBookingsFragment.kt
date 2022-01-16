package com.in10mServiceMan.ui.fragments.past_bookings


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.fragment_past_bookings.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteBookingsFragment : Fragment() {

    private lateinit var bookingsAdapter: BookingsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past_bookings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRV()
    }

    private fun initRV() {

        //bookingsAdapter = BookingsAdapter()
       // pastBookingsRV.layoutManager = LinearLayoutManager(activity)
       // pastBookingsRV.adapter = bookingsAdapter
    }
}
