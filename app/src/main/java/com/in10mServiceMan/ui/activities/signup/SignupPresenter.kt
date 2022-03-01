package com.in10mServiceMan.ui.activities.signup

import com.google.gson.JsonElement

class SignupPresenter(val view: ISignupview) : ISignupInteractor, ISignupInteractorListener {

    override fun onNumberVerified(mData: SignupOneResponse) {
        view.onNumberVerified(mData)
    }

    override fun verifyMobile(header: String, otp: String, email: String, mobile: String) {
        mInteractor.verifyMobile(header, otp, email, mobile)
    }

    override fun signupLevelThreeDebitCard(userID: String, email: String, estimateType: String, estimationFee: String, payoutType: String, expiryMonth: String, expiryYear: String, cardNumber: String, paymentType: String, street: String, houseNo: String, city: String, state: String, country: String, SSN: String, zip: String) {
        mInteractor.signupLevelThreeDebitCard(userID, email, estimateType, estimationFee, payoutType, expiryMonth, expiryYear, cardNumber, paymentType, street, houseNo, city, state, country, SSN, zip)
    }

    override fun signupLevelThreeBankPay(userID: String, email: String, estimateType: String, estimationFee: String, payoutType: String, accountNumer: String, RoutingNumber: String, SSN: String, paymentType: String, street: String, houseNo: String, city: String, state: String, country: String, zip: String) {
        mInteractor.signupLevelThreeBankPay(userID, email, estimateType, estimationFee, payoutType, accountNumer, RoutingNumber, SSN, paymentType, street, houseNo, city, state, country, zip)
    }

    override fun signupLevelThreeDirectCash(userID: String, email: String, estimateType: String, estimationFee: String, dob: String, payoutType: String) {
        mInteractor.signupLevelThreeDirectCash(userID, email, estimateType, estimationFee, dob,payoutType)
    }

    override fun onStepThreeCompleted(mData: SignupThreeResponse) {
        view.onStepThreeCompleted(mData)
    }

    override fun signupLevelTwo(header: String, userID: String, profilePicData: String, stateId: String, certificate: String) {
        mInteractor.signupLevelTwo(header, userID, profilePicData, stateId, certificate)
    }

    override fun onProfilePictureUpdated(mData: SignupstepTwoResponse) {
        view.onProfilePictureUpdated(mData)
    }


    override fun onSignUpFirstCompleted(mResponse: SignupOneResponse) {
        view.onSignUpFirstCompleted(mResponse)
    }

    override fun onFailed(msg: String) {
        view.onFailed(msg)
    }

    override fun signUpUser(type: Int, firstName: String, lastName: String, dob: String, street: String, houseNo: String, city: String, state: String, country: String, zip: String, email: String, countryCode: String, phone: String, password: String, services: String) {
        mInteractor.signUpUser(
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
    }

    val mInteractor = SignupInteractor(this)
}