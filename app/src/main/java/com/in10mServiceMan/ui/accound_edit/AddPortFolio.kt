package com.in10mServiceMan.ui.accound_edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.*
import com.in10mServiceMan.ui.activities.BaseFragment

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.apis.APIClient
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

import android.widget.*
import com.in10mServiceMan.models.*
import com.in10mServiceMan.ui.activities.signup.SignupstepTwoResponse
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.cropper.CropImage
import com.in10mServiceMan.utils.cropper.CropImageView
import kotlinx.android.synthetic.main.addportfolio_lay.*
import kotlinx.android.synthetic.main.addportfolio_lay.view.*
import kotlinx.android.synthetic.main.fragment_signup_details.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * A simple [Fragment] subclass.
 */
class AddPortFolio : BaseFragment() {

    var imageUri: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.addportfolio_lay, container, false)

        view.ivAdd_PortFolioLay.setOnClickListener {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                .setOutputCompressQuality(50)
                .setFixAspectRatio(false)
                .start(context!!, this)
        }
        view.ivSend_PortFolioLay.setOnClickListener {
            addPortFolio(imageUri)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                imageUri = resultUri.path.toString()
                cvImage_PortFolioLay.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    fun addPortFolio(
        portFolioImg: String
    ) {
        val header =
            SharedPreferencesHelper.getString(context, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val userId = RequestBody.create(
            MediaType.parse("text/plain"),
            SharedPreferencesHelper.getString(context, Constants.SharedPrefs.User.USER_ID, "")
                .toString(),
        )
        var body: MultipartBody.Part? = null
        val file = File(portFolioImg)
        val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
        body = MultipartBody.Part.createFormData("profile_pictures[0]", file.name, reqFile)

        val profilePicRequest = APIClient.getApiInterface()
            .addPortfolio(header, userId, body)
        profilePicRequest.enqueue(object : Callback<PortfolioPojo> {
            override fun onResponse(
                call: Call<PortfolioPojo>,
                response: Response<PortfolioPojo>
            ) {
                if (response.isSuccessful) {
                    Log.d("onResponse-1", response.body().toString())
//                    listener.onProfilePictureUpdated(response.body()!!)
                } else {
                    Log.d("onResponse-2 ", "Something went wrong")
                }
            }

            override fun onFailure(call: Call<PortfolioPojo>, t: Throwable) {
                Log.d("onFailure", "Something went wrong")
            }
        })

    }
}
