package com.in10mServiceMan.ui.activities.signup


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.in10mServiceMan.Models.HomeService
import com.in10mServiceMan.Models.Service
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.apis.LoginAPI
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.spinnerAdapter
import kotlinx.android.synthetic.main.fragment_certification_details.*
import kotlinx.android.synthetic.main.fragment_certification_details.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CertificationDetailsFragment : Fragment(), OfferedServiceAdapter.SelectedServiceCallback {
    override fun openSubService(item: Service) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun selectService(item: Service) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeSelectedService(item: Service) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mStatesList: List<State?>? = null
    var country: String = ""
    var country_id: String = ""
    var state: String = ""
    var mSelectedStateID: String = "3919"
    var isCertificateAvailable = "1"

    interface NextFragmentInterfaceFive {
        fun toNextFragmentFive(certificateStatus: String, stateId: String)
    }

    companion object {
        private var mListener: NextFragmentInterfaceFive? = null
        fun newInstance(mNextFragmentListener: NextFragmentInterfaceFive): CertificationDetailsFragment {
            mListener = mNextFragmentListener
            return CertificationDetailsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_certification_details, container, false)

        getStates()
        getServiceManServices()
        var myTypeSpinner = view.findViewById(R.id.availableStateSpinner) as Spinner
        var RadioYes = view.findViewById(R.id.certificationYesRadioButton) as ImageView
        var RadioNo = view.findViewById(R.id.certificationNoRadioButton) as ImageView
        RadioYes.setOnClickListener {
            view.issueStateTV.visibility = View.VISIBLE
            view.availableStateSpinner.visibility = View.VISIBLE
            view.offeredServicesRV.visibility = View.VISIBLE
            view.certificationYesRadioButton.setImageResource(R.drawable.select_radio_one)
            view.certificationNoRadioButton.setImageResource(R.drawable.unselect_radio_one)
            isCertificateAvailable = "1"
        }
        RadioNo.setOnClickListener {
            view.issueStateTV.visibility = View.GONE
            view.availableStateSpinner.visibility = View.GONE
            view.offeredServicesRV.visibility = View.GONE
            view.certificationNoRadioButton.setImageResource(R.drawable.select_radio_one)
            view.certificationYesRadioButton.setImageResource(R.drawable.unselect_radio_one)
            isCertificateAvailable = "0"
            mSelectedStateID = ""
        }

        myTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>, view: View,
                    position: Int, id: Long
            ) {
                if (mStatesList != null) {

                    /*  state = mStatesList!![position]!!.name!!
                      mSelectedStateID = mStatesList!![position]!!.id.toString()*/
                    state = myTypeSpinner.selectedItem.toString()
                    //mSelectedStateID = mStatesList!![myTypeSpinner.selectedItemPosition]!!.id.toString()
                    mSelectedStateID = getStateId(myTypeSpinner.selectedItemPosition).toString()
                    Log.d("State and ID", "$state $mSelectedStateID")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        view.enterButtonCertificates.setOnClickListener {
//            isCertificateAvailable = "0"
//            mSelectedStateID = ""
            mListener!!.toNextFragmentFive(isCertificateAvailable, mSelectedStateID)

        }
        return view
    }

    private fun getStates() {
        val homeCall = LoginAPI.loginUser().states
        homeCall.enqueue(object : Callback<StatesResponse> {
            override fun onResponse(call: Call<StatesResponse>, response: Response<StatesResponse>) {
                if (response.isSuccessful) {
                    bindData(response.body()!!)
                } else {

                }
            }

            override fun onFailure(call: Call<StatesResponse>, t: Throwable) {

            }
        })
    }

    private fun getServiceManServices() {

        val header = SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val homeCall = LoginAPI.loginUser().getExistingServiceDetailsWithHeader("Bearer $header", SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.USER_ID, "0")!!
            .toInt())
        homeCall.enqueue(object : Callback<HomeService> {
            override fun onResponse(call: Call<HomeService>, response: Response<HomeService>) {
                if (response.isSuccessful) {
                    bindRecyclerData(response.body()!!)
                } else {
                    Log.d("error", "Error")
                }
            }

            override fun onFailure(call: Call<HomeService>, t: Throwable) {
                Log.d("error", "Error")
            }
        })
    }

    fun bindData(body: StatesResponse) {
        mStatesList = body?.data?.states
        country = body?.data?.countryCode!!
        country_id = body?.data?.countryId.toString()
        var myStateList: ArrayList<String>? = ArrayList()
        if (mStatesList?.size!! > 0) {
            for (i in 0 until mStatesList!!.size) {
                myStateList?.add(mStatesList?.get(i)?.name!!)
            }
        }
        /*val adapter = ArrayAdapter(this.context, R.layout.custom_state_spinner_list, myStateList)//android.R.layout.simple_list_item_1
        availableStateSpinner.adapter = adapter*/
        val adapter = spinnerAdapter(this.context, R.layout.custom_state_spinner_list)
        if (myStateList != null) {
            adapter.addAll(myStateList)
        }
        adapter.add("STATE")
        availableStateSpinner.adapter = adapter
        availableStateSpinner.setSelection(adapter.count)
    }

    private fun getStateId(id: Int): Int {
        Log.d("selected position", id.toString())
        var data = 3919
        if (mStatesList != null) {
            if (id < mStatesList?.size!!)
                data = mStatesList!![id]?.id!!
        } else {
            data = 3919
        }
        Log.d("selected state Id", data.toString())
        return data
    }

    private fun bindRecyclerData(body: HomeService?) {

        offeredServicesRV.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        offeredServicesRV.adapter = OfferedServiceAdapter(this.context!!, this, body)
    }
}
