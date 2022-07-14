package com.in10mServiceMan.ui.activities.signup

import com.google.gson.JsonElement

interface ISignupview {
    fun onSignUpFirstCompleted(mResponse: SignupOneResponse)
    fun onFailed(msg: String)
    fun onProfilePictureUpdated(mData: SignupstepTwoResponse)
    fun onStepThreeCompleted(mData: SignupThreeResponse)
    fun onNumberVerified(mData: SignupOneResponse)
}

interface ISignupPresenter {

    fun verifyMobile(header: String, otp: String, email: String, mobile: String)

    fun signUpUser(type: Int,
                   firstName: String,
                   lastName: String,
                   dob: String,
                   street: String,
                   houseNo: String,
                   city: String,
                   state: String,
                   country: String,
                   zip: String,
                   email: String,
                   countryCode: String,
                   phone: String,
                   password: String,
                   services: String,
                   country_id: String
    )
    fun signupLevelTwo(header: String, userID: String, profilePicData: String, stateId: String, certificate: String)
    fun signupLevelThreeDebitCard(userID: String,
                                  email: String,
                                  estimateType: String,
                                  estimationFee: String,
                                  payoutType: String,
                                  expiryMonth: String,
                                  expiryYear: String,
                                  cardNumber: String,
                                  paymentType: String,
                                  street: String,
                                  houseNo: String,
                                  city: String,
                                  state: String,
                                  country: String,
                                  SSN: String,
                                  zip: String)

    fun signupLevelThreeBankPay(userID: String,
                                email: String,
                                estimateType: String,
                                estimationFee: String,
                                payoutType: String,
                                accountNumer: String,
                                RoutingNumber: String,
                                SSN: String,
                                paymentType: String,
                                street: String,
                                houseNo: String,
                                city: String,
                                state: String,
                                country: String,
                                zip: String)

    fun signupLevelThreeDirectCash(userID: String,
                                   email: String,
                                   estimateType: String,
                                   estimationFee: String,
                                   dob: String,
                                   payoutType: String)
}

interface ISignupInteractor : ISignupPresenter

interface ISignupInteractorListener : ISignupview