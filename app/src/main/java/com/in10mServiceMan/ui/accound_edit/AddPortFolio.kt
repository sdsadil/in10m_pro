package com.in10mServiceMan.ui.accound_edit

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import com.in10mServiceMan.ui.activities.BaseFragment

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.profile.IProfileView
import com.in10mServiceMan.ui.activities.profile.ImageUpdateResponse
import com.in10mServiceMan.ui.activities.profile.ProfilePresenter
import com.in10mServiceMan.ui.activities.profile.ServiceOfferAdapter
import com.in10mServiceMan.ui.activities.services.ServicesResponse
import com.in10mServiceMan.ui.activities.signup.State
import com.in10mServiceMan.ui.activities.signup.StatesResponse
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import com.in10mServiceMan.utils.spinnerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.net.Uri
import androidx.core.content.FileProvider

import android.os.Build
import android.widget.*
import androidx.appcompat.widget.AppCompatTextView
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.in10mServiceMan.BuildConfig
import com.in10mServiceMan.models.*
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
}
