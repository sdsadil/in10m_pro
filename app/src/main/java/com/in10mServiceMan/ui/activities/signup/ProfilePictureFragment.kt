package com.in10mServiceMan.ui.activities.signup


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.in10mServiceMan.R
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.cropper.CropImage
import com.in10mServiceMan.utils.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_profile_picture.*
import kotlinx.android.synthetic.main.fragment_profile_picture.view.*

class ProfilePictureFragment : Fragment() {

    var isProfileImage = "";
    var isFrontImage = "";
    var isBackImage = "";

    interface NextFragmentInterfaceFour {
        fun toNextFragmentFour(imageUri: String)
    }

    companion object {
        var imageUri = ""
        var frontimageUri = ""
        var backimageUri = ""
        private var mListener: NextFragmentInterfaceFour? = null
        fun newInstance(mNextFragmentListener: NextFragmentInterfaceFour): ProfilePictureFragment {
            mListener = mNextFragmentListener
            return ProfilePictureFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_picture, container, false)

        view.camera.setOnClickListener {
            isProfileImage = "true"
            isFrontImage = "false"
            isBackImage = "false"
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                .setOutputCompressQuality(50)
                .setFixAspectRatio(false)
                .start(context!!, this)
        }

        view.ivFrontCivilIDcamera.setOnClickListener {
            isProfileImage = "false"
            isFrontImage = "true"
            isBackImage = "false"

            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                .setOutputCompressQuality(50)
                .setFixAspectRatio(false)
                .start(context!!, this)
        }
        view.ivBackCivilIDcamera.setOnClickListener {
            isProfileImage = "false"
            isFrontImage = "false"
            isBackImage = "true"
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                .setOutputCompressQuality(50)
                .setFixAspectRatio(false)
                .start(context!!, this)
        }
        view.profilePictureOkButton.setOnClickListener {
            if (imageUri.isEmpty()) {
                Toast.makeText(
                    this.context,
                    getString(R.string.please_select_an_image),
                    Toast.LENGTH_SHORT
                ).show()
            }else if (frontimageUri.isEmpty()) {
                Toast.makeText(
                    this.context,
                    getString(R.string.please_select_an_image1),
                    Toast.LENGTH_SHORT
                ).show()
            }else if (backimageUri.isEmpty()) {
                Toast.makeText(
                    this.context,
                    getString(R.string.please_select_an_image2),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mListener?.toNextFragmentFour(imageUri)
            }
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                if (isProfileImage == "true") {
                    imageUri = resultUri.path.toString()
                    userAvatar.setImageURI(resultUri)
                    SharedPreferencesHelper.putString(
                        this.context,
                        Constants.SharedPrefs.User.USER_IMAGE,
                        resultUri.toString()
                    )
                } else if (isFrontImage == "true") {
                    frontimageUri = resultUri.path.toString()
                    ivFrontCivilID.setImageURI(resultUri)
                    SharedPreferencesHelper.putString(
                        this.context,
                        Constants.SharedPrefs.User.CIVIL_FRONT_IMAGE,
                        resultUri.toString()
                    )
                } else if (isBackImage == "true") {
                    backimageUri = resultUri.path.toString()
                    ivBackCivilID.setImageURI(resultUri)
                    SharedPreferencesHelper.putString(
                        this.context,
                        Constants.SharedPrefs.User.CIVIL_BACK_IMAGE,
                        resultUri.toString()
                    )
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

}
