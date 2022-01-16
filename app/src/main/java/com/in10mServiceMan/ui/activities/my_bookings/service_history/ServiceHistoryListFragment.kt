package com.in10mServiceMan.ui.activities.my_bookings.service_history

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.in10m.ui.activities.BaseFragment
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.my_bookings.BookingHistoryInterface
import com.in10mServiceMan.ui.activities.my_bookings.ServiceHistoryData
import com.in10mServiceMan.ui.activities.my_bookings.ServiceHistoryResponse
import com.in10mServiceMan.ui.fragments.past_bookings.BookingsAdapter
import kotlinx.android.synthetic.main.fragment_servicelist.view.*

class ServiceHistoryListFragment : BaseFragment(), BookingHistoryInterface {
    override fun adapterTransaction() {
    }

    private var historyResponse: String = ""
    private var historyPageType: Boolean = false
    private var mData: ServiceHistoryResponse? = null
    private lateinit var bookingsAdapter: BookingsAdapter

    companion object {
        val SERVICE_HISTORY: String = "serviceHistory"
        val PAGE_TYPE: String = "pageType"
        fun getInstance(historyResponse: String, historyPageType: Boolean): ServiceHistoryListFragment {
            var serviceHistoryListFragment: ServiceHistoryListFragment = ServiceHistoryListFragment()

            var bundle = Bundle()
            bundle.putString(SERVICE_HISTORY, historyResponse)
            bundle.putBoolean(PAGE_TYPE, historyPageType)

            serviceHistoryListFragment.arguments = bundle

            return serviceHistoryListFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_servicelist, container, false)

        splitServiceHistory(mData?.data as List<ServiceHistoryData>,view)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            historyResponse = bundle.getString(SERVICE_HISTORY).toString()
            historyPageType = bundle.getBoolean(PAGE_TYPE)
            mData = Gson().fromJson(historyResponse, ServiceHistoryResponse::class.java)
        }
    }

    private fun splitServiceHistory(allServiceHistoryList: List<ServiceHistoryData>, mView:View) {
        var completedServiceHistoryList: ArrayList<ServiceHistoryData> = ArrayList()
        var canceledServiceHistoryList: ArrayList<ServiceHistoryData> = ArrayList()

        for (i in allServiceHistoryList.indices) {
            if (allServiceHistoryList[i].service_status == 6) {
                completedServiceHistoryList.add(allServiceHistoryList[i])
            } else if (allServiceHistoryList[i].service_status == 7 || allServiceHistoryList[i].service_status == 8) {
                canceledServiceHistoryList.add(allServiceHistoryList[i])
            }
        }
        if (historyPageType) {
            bookingsAdapter = BookingsAdapter(this.context!!, completedServiceHistoryList, this)
            mView.upcomingRV.layoutManager = LinearLayoutManager(this.context!!)
            mView.upcomingRV.adapter = bookingsAdapter
        } else {
            bookingsAdapter = BookingsAdapter(this.context!!, canceledServiceHistoryList, this)
            mView.upcomingRV.layoutManager = LinearLayoutManager(this.context!!)
            mView.upcomingRV.adapter = bookingsAdapter
        }
        Log.d("cancelled list", Gson().toJson(canceledServiceHistoryList))
        Log.d("completed list", Gson().toJson(completedServiceHistoryList))

    }
}