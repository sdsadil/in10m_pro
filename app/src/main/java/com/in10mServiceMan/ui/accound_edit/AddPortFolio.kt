package com.in10mServiceMan.ui.accound_edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.in10mServiceMan.R
import com.in10mServiceMan.models.PortfolioPojo
import com.in10mServiceMan.ui.activities.BaseFragment
import com.in10mServiceMan.ui.adapter.PortFolioAdapter
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.cropper.CropImage
import com.in10mServiceMan.utils.cropper.CropImageView
import kotlinx.android.synthetic.main.addportfolio_lay.*
import kotlinx.android.synthetic.main.addportfolio_lay.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddPortFolio : BaseFragment() {
    val imageUriList: ArrayList<String> = ArrayList()
    var linearLayoutManager: LinearLayoutManager? = null
    var portFolioAdapter: PortFolioAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.addportfolio_lay, container, false)

        linearLayoutManager = LinearLayoutManager(context)
        view.rvPortfolioList_PortFolioLay.layoutManager = linearLayoutManager
        portFolioAdapter =
            PortFolioAdapter(
                imageUriList,
                context
            )
        view.rvPortfolioList_PortFolioLay.adapter = portFolioAdapter

        view.ivAdd_PortFolioLay.setOnClickListener {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                .setOutputCompressQuality(50)
                .setFixAspectRatio(false)
                .start(context!!, this)
        }
        view.ivSend_PortFolioLay.setOnClickListener {
            addPortFolio()
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                imageUriList.add(resultUri.path.toString())
                portFolioAdapter?.notifyDataSetChanged()
                if (imageUriList.size > 0) {
                    ivSend_PortFolioLay.visibility = View.VISIBLE
                    rvPortfolioList_PortFolioLay.visibility = View.VISIBLE
                    tvPortfolioList_PortFolioLay.visibility = View.GONE
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    fun addPortFolio() {
        val header =
            SharedPreferencesHelper.getString(context, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val userId = RequestBody.create(
            MediaType.parse("text/plain"),
            SharedPreferencesHelper.getString(context, Constants.SharedPrefs.User.USER_ID, "")
                .toString(),
        )

        //Single Multipart
//        var body: MultipartBody.Part? = null
//        val file = File(portFolioImg)
//        val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
//        body = MultipartBody.Part.createFormData("profile_pictures[0]", file.name, reqFile)

        //Multiple Multipart
        val bodyList: ArrayList<MultipartBody.Part> = ArrayList()
        for (i in 0 until imageUriList.size) {
            val file1 = File(imageUriList[i])
            val reqFile1 = RequestBody.create(MediaType.parse("image/*"), file1)
            val body1: MultipartBody.Part? = MultipartBody.Part.createFormData(
                "profile_pictures[$i]",
                file1.name,
                reqFile1
            )
            body1?.let { bodyList.add(it) }
        }

        val profilePicRequest = APIClient.getApiInterface()
            .addPortfolio(header, userId, bodyList)
        profilePicRequest.enqueue(object : Callback<PortfolioPojo> {
            override fun onResponse(
                call: Call<PortfolioPojo>,
                response: Response<PortfolioPojo>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        context,
                        context?.resources?.getString(R.string.something_went_wrong),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<PortfolioPojo>, t: Throwable) {
                Toast.makeText(
                    context,
                    context?.resources?.getString(R.string.something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}