package com.in10mServiceMan.ui.activities.company_registration

import android.util.Log
import com.in10mServiceMan.ui.activities.signup.SignupOneResponse
import com.in10mServiceMan.ui.activities.signup.SignupThreeResponse
import com.in10mServiceMan.ui.activities.signup.SignupstepTwoResponse
import com.in10mServiceMan.ui.apis.APIClient
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class CompanySignupInteractor(val listener: ICompanySignupInteractorListener) : ICompanySignupInteractor {
    override fun signupLevelTwo(header: String, userID: String, profilePicData: String) {
        val userId = RequestBody.create(MediaType.parse("text/plain"), userID)
        var body: MultipartBody.Part? = null
        val file = File(profilePicData)
        val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
        body = MultipartBody.Part.createFormData("profile_picture", file.name, reqFile)

        val profilePicRequest = APIClient.getApiInterface().companyProfilePictureUpdate(header, userId, body)
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

    override fun verifyMobile(header: String, otp: String, email: String, mobile: String) {

        val request = APIClient.getApiInterface().verifyOtp("Bearer $header", otp, mobile, email)
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

    override fun signUpUserCompany(type: Int, firstName: String, lastName: String, dob: String, street: String, houseNo: String, city: String, state: String, country: String, zip: String, email: String, countryCode: String, phone: String, password: String, services: String, companyName: String) {

        val registerStepOne = APIClient.getApiInterface().SignupOneCompany(
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
                services,
                companyName
        )
        registerStepOne.enqueue(object : Callback<SignupOneResponse> {
            override fun onResponse(call: Call<SignupOneResponse>, response: Response<SignupOneResponse>) {
                if (response.isSuccessful) {
                    listener.onSignUpFirstCompleted(response.body()!!)
                } else {
                    listener.onFailed("Something went wrong")
                }
            }

            override fun onFailure(call: Call<SignupOneResponse>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })

    }

    override fun signupLevelThreeDebitCard(userID: String, email: String, estimateType: String, estimationFee: String, payoutType: String, expiryMonth: String, expiryYear: String, cardNumber: String, paymentType: String, street: String, houseNo: String, city: String, state: String, country: String, SSN: String, zip: String) {
        val request = APIClient.getApiInterface().registrationLevelDebitCard("Bearer " + APIClient.token, userID, email, estimateType, estimationFee, payoutType, expiryMonth, expiryYear, cardNumber, paymentType, street, houseNo, city, state, country, SSN, zip)
        request.enqueue(object : Callback<SignupThreeResponse> {
            override fun onResponse(call: Call<SignupThreeResponse>, response: Response<SignupThreeResponse>) {
                if (response.isSuccessful) {
                    Log.d("response ", response.body().toString())
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

    override fun signupLevelThreeBankPay(userID: String, email: String, estimateType: String, estimationFee: String, payoutType: String, accountNumer: String, RoutingNumber: String, SSN: String, paymentType: String, street: String, houseNo: String, city: String, state: String, country: String, zip: String) {
        val request = APIClient.getApiInterface().registrationLevelBankPay("Bearer " + APIClient.token, userID, email, estimateType, estimationFee, payoutType, accountNumer, RoutingNumber, SSN, paymentType, street, houseNo, city, state, country, zip)
        request.enqueue(object : Callback<SignupThreeResponse> {
            override fun onResponse(call: Call<SignupThreeResponse>, response: Response<SignupThreeResponse>) {
                if (response.isSuccessful) {
                    Log.d("response ", response.body().toString())
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

    override fun signupLevelThreeDirectCash(userID: String, email: String, estimateType: String, estimationFee: String, dob: String, payoutType: String) {
        val request = APIClient.getApiInterface().registrationLevelThreeCash("Bearer " + APIClient.token, userID, email, estimateType, estimationFee,dob, payoutType)
        request.enqueue(object : Callback<SignupThreeResponse> {
            override fun onResponse(call: Call<SignupThreeResponse>, response: Response<SignupThreeResponse>) {
                if (response.isSuccessful) {
                    Log.d("response ", response.body().toString())
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
}