package com.in10mServiceMan.ui.activities.company_registration


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
import kotlinx.android.synthetic.main.fragment_company_profile_picture.*
import kotlinx.android.synthetic.main.fragment_company_profile_picture.view.*

class CompanyProfilePictureFragment : Fragment() {

    interface NextFragmentInterfaceFour {
        fun toNextFragmentFour(imageUri: String)
    }

    companion object {
        var imageUri = ""
        private var mListener: NextFragmentInterfaceFour? = null
        fun newInstance(mNextFragmentListener: NextFragmentInterfaceFour): CompanyProfilePictureFragment {
            mListener = mNextFragmentListener
            return CompanyProfilePictureFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_company_profile_picture, container, false)

        view.camera.setOnClickListener {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setOutputCompressQuality(50)
                    .setFixAspectRatio(false)
                    .start(context!!, this)
        }
        view.profilePictureOkButton.setOnClickListener {
            if (imageUri.isEmpty()) {
                Toast.makeText(this.context, "Please select an Image", Toast.LENGTH_SHORT).show()
            } else {
                mListener?.toNextFragmentFour(imageUri)
            }
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode === Activity.RESULT_OK) {
                val resultUri = result.uri
                imageUri = resultUri.path.toString()
                userAvatar.setImageURI(resultUri)

                SharedPreferencesHelper.putString(this.context, Constants.SharedPrefs.User.USER_IMAGE, resultUri.toString())
            } else if (resultCode === CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

}
