package com.in10mServiceMan.ui.activities.signup

import android.util.Log
import com.google.gson.Gson
import com.in10mServiceMan.ui.apis.LoginAPI
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SignupInteractor(var listener: ISignupInteractorListener) : ISignupInteractor {

    override fun verifyMobile(header: String, otp: String, email: String, mobile: String) {

        val request = LoginAPI.loginUser().verifyOtp("Bearer $header", otp, mobile, email)
        request.enqueue(object : Callback<SignupOneResponse> {
            override fun onResponse(call: Call<SignupOneResponse>, response: Response<SignupOneResponse>) {
                if (response.isSuccessful) {
                    Log.d("response ", response.body().toString())
                    listener.onNumberVerified(response.body()!!)
                } else {
                    listener.onFailed("Something went wrong")
                }
            }

            override fun onFailure(call: Call<SignupOneResponse>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })
    }

    override fun signupLevelThreeDirectCash(userID: String, email: String, estimateType: String, estimationFee: String, payoutType: String) {

        val request = LoginAPI.loginUser().registrationLevelThreeCash("Bearer " + LoginAPI.Token, userID, email, estimateType, estimationFee, payoutType)
        request.enqueue(object : Callback<SignupThreeResponse> {
            override fun onResponse(call: Call<SignupThreeResponse>, response: Response<SignupThreeResponse>) {
                if (response.isSuccessful) {
                    listener.onStepThreeCompleted(response.body()!!)
                } else {
                    listener.onFailed("Something went wrong")
                    Log.d("Message from backend", Gson().toJson(response.errorBody()).toString())
                }
            }

            override fun onFailure(call: Call<SignupThreeResponse>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })
    }

    override fun signupLevelThreeDebitCard(userID: String, email: String, estimateType: String, estimationFee: String, payoutType: String, expiryMonth: String, expiryYear: String, cardNumber: String, paymentType: String, street: String, houseNo: String, city: String, state: String, country: String, SSN: String, zip: String) {
        val request = LoginAPI.loginUser().registrationLevelDebitCard("Bearer " + LoginAPI.Token, userID, email, estimateType, estimationFee, payoutType, expiryMonth, expiryYear, cardNumber, paymentType, street, houseNo, city, state, country, SSN, zip)
        request.enqueue(object : Callback<SignupThreeResponse> {
            override fun onResponse(call: Call<SignupThreeResponse>, response: Response<SignupThreeResponse>) {
                if (response.isSuccessful) {
                    Log.d("Message from backend", response.body().toString())
                    listener.onStepThreeCompleted(response.body()!!)
                } else {
                    listener.onFailed("Something went wrong")
                    Log.d("Message from backend", Gson().toJson(response.errorBody()).toString())
                }
            }

            override fun onFailure(call: Call<SignupThreeResponse>, t: Throwable) {
                listener.onFailed(t.localizedMessage)

            }
        })

    }

    override fun signupLevelThreeBankPay(userID: String, email: String, estimateType: String, estimationFee: String, payoutType: String, accountNumer: String, RoutingNumber: String, SSN: String, paymentType: String, street: String, houseNo: String, city: String, state: String, country: String, zip: String) {

        val request = LoginAPI.loginUser().registrationLevelBankPay("Bearer " + LoginAPI.Token, userID, email, estimateType, estimationFee, payoutType, accountNumer, RoutingNumber, SSN, paymentType, street, houseNo, city, state, country, zip)
        request.enqueue(object : Callback<SignupThreeResponse> {
            override fun onResponse(call: Call<SignupThreeResponse>, response: Response<SignupThreeResponse>) {
                if (response.isSuccessful) {
                    Log.d("Message from backend", response.body().toString())
                    listener.onStepThreeCompleted(response.body()!!)
                } else {
                    listener.onFailed("Something went wrong")
                }
            }

            override fun onFailure(call: Call<SignupThreeResponse>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })
    }

    override fun signupLevelTwo(header: String, userID: String, profilePicData: String, stateId: String, certificate: String) {
        val userId = RequestBody.create(MediaType.parse("text/plain"), userID)
        val stateID = RequestBody.create(MediaType.parse("text/plain"), stateId)
        val certificateBody = RequestBody.create(MediaType.parse("text/plain"), certificate)
        var body: MultipartBody.Part? = null
        val file = File(profilePicData)
        val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
        body = MultipartBody.Part.createFormData("profile_picture", file.name, reqFile)

        val profilePicRequest = LoginAPI.loginUser().profilePictureUpdate(header, userId, stateID, certificateBody, body)
        profilePicRequest.enqueue(object : Callback<SignupstepTwoResponse> {
            override fun onResponse(call: Call<SignupstepTwoResponse>, response: Response<SignupstepTwoResponse>) {
                if (response.isSuccessful) {
                    Log.d("response ", response.body().toString())
                    listener.onProfilePictureUpdated(response.body()!!)
                } else {
                    listener.onFailed("Something went wrong")
                }
            }

            override fun onFailure(call: Call<SignupstepTwoResponse>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })

    }

    override fun signUpUser(type: Int, firstName: String, lastName: String, dob: String, street: String, houseNo: String, city: String, state: String, country: String, zip: String, email: String, countryCode: String, phone: String, password: String, services: String) {

        val registerStepOne = LoginAPI.loginUser().SignupOne(
                type,
                firstName,
                lastName,
                dob,
                street,
                houseNo,
                city,
                state,
                country,
                zip,
                email,
                countryCode,
                phone,
                password,
                services
        )
        registerStepOne.enqueue(object : Callback<SignupOneResponse> {
            override fun onResponse(call: Call<SignupOneResponse>, response: Response<SignupOneResponse>) {
                if (response.isSuccessful) {
                    Log.d("Message from backend", response.body().toString())
                    listener.onSignUpFirstCompleted(response.body()!!)
                } else {
                    Log.d("Message from backend", response.body().toString())
                    listener.onFailed("Something went wrong")
                }
            }

            override fun onFailure(call: Call<SignupOneResponse>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })
    }
}