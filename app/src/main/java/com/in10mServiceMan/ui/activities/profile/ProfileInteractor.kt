package com.in10mServiceMan.ui.activities.profile

import com.in10mServiceMan.models.CustomerCompleteProfile
import com.in10mServiceMan.models.CustomerCompleteProfileAfterUpdate
import com.in10mServiceMan.models.RequestUpdateServiceMan
import com.in10mServiceMan.ui.apis.APIClient
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileInteractor(val listener: IProfileInteractorListener) : IProfileInteractor {
    override fun getCompleteProfile(userId: String) {
        val callServiceProviders = APIClient.getApiInterface().getCompleteProfile(userId.toInt())
        callServiceProviders.enqueue(object : Callback<CustomerCompleteProfile> {
            override fun onResponse(
                call: Call<CustomerCompleteProfile>,
                response: Response<CustomerCompleteProfile>,
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.data != null) {
                        listener.onCompleteProfileReceived(data)
                    }
                } else {
                    listener.onFailed(response.message())
                }
            }

            override fun onFailure(call: Call<CustomerCompleteProfile>, t: Throwable) {
                listener.onFailed("Error in loading Complete profile")
            }
        })
    }

    override fun updateProfile(header: String, request: RequestUpdateServiceMan) {
        val callServiceProviders = APIClient.getApiInterface().updateServiceManProfile(request)
        callServiceProviders.enqueue(object : Callback<CustomerCompleteProfileAfterUpdate> {
            override fun onResponse(
                call: Call<CustomerCompleteProfileAfterUpdate>,
                response: Response<CustomerCompleteProfileAfterUpdate>,
            ) {
                listener.onProfileUpdated(response.body()!!)
            }

            override fun onFailure(call: Call<CustomerCompleteProfileAfterUpdate>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })
    }

    override fun updateProfilePicture(header: String, serviceManId: String, imageUri: String) {
        val serviceManIdBody = RequestBody.create(MediaType.parse("text/plain"), serviceManId)
        var body: MultipartBody.Part? = null
        val file = File(imageUri)
        val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
        body = MultipartBody.Part.createFormData("profile_picture", file.name, reqFile)

        val profilePicRequest = APIClient.getApiInterface()
            .UpdateServiceManProfilePicture("Bearer $header", serviceManIdBody, body)
        profilePicRequest.enqueue(object : Callback<ImageUpdateResponse> {
            override fun onResponse(
                call: Call<ImageUpdateResponse>,
                response: Response<ImageUpdateResponse>,
            ) {
                if (response.isSuccessful) {
                    listener.onDPUpdated(response.body()!!)
                } else {
                    listener.onFailed("Something went wrong")
                }
            }

            override fun onFailure(call: Call<ImageUpdateResponse>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })
    }
}