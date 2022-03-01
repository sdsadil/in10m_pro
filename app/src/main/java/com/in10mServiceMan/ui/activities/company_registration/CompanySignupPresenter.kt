package com.in10mServiceMan.ui.activities.company_registration

import com.in10mServiceMan.ui.activities.signup.SignupOneResponse
import com.in10mServiceMan.ui.activities.signup.SignupThreeResponse
import com.in10mServiceMan.ui.activities.signup.SignupstepTwoResponse

class CompanySignupPresenter(val view: ICompanySignupView) : ICompanySignupPresenter, ICompanySignupInteractorListener {
    override fun signupLevelTwo(header: String, userID: String, profilePicData: String) {
        mInteractor.signupLevelTwo(header, userID, profilePicData)
    }

    override fun onSignUpFirstCompleted(mResponse: SignupOneResponse) {
        view.onSignUpFirstCompleted(mResponse)
    }

    override fun onFailed(msg: String) {
        view.onFailed(msg)
    }

    override fun onProfilePictureUpdated(mData: SignupstepTwoResponse) {
        view.onProfilePictureUpdated(mData)
    }

    override fun onStepThreeCompleted(mData: SignupThreeResponse) {
        view.onStepThreeCompleted(mData)
    }

    override fun onNumberVerified(mData: SignupOneResponse) {
        view.onNumberVerified(mData)
    }

    override fun verifyMobile(header: String, otp: String, email: String, mobile: String) {
        mInteractor.verifyMobile(header, otp, email, mobile)
    }

    override fun signUpUserCompany(type: Int, firstName: String, lastName: String, dob: String, street: String, houseNo: String, city: String, state: String, country: String, zip: String, email: String, countryCode: String, phone: String, password: String, services: String, companyName: String) {
        mInteractor.signUpUserCompany(type, firstName, lastName, dob, street, houseNo, city, state, country, zip, email, countryCode, phone, password, services, companyName)
    }

    override fun signupLevelThreeDebitCard(userID: String, email: String, estimateType: String, estimationFee: String, payoutType: String, expiryMonth: String, expiryYear: String, cardNumber: String, paymentType: String, street: String, houseNo: String, city: String, state: String, country: String, SSN: String, zip: String) {
        mInteractor.signupLevelThreeDebitCard(userID, email, estimateType, estimationFee, payoutType, expiryMonth, expiryYear, cardNumber, paymentType, street, houseNo, city, state, country, SSN, zip)
    }

    override fun signupLevelThreeBankPay(userID: String, email: String, estimateType: String, estimationFee: String, payoutType: String, accountNumer: String, RoutingNumber: String, SSN: String, paymentType: String, street: String, houseNo: String, city: String, state: String, country: String, zip: String) {
        mInteractor.signupLevelThreeBankPay(userID, email, estimateType, estimationFee, payoutType, accountNumer, RoutingNumber, SSN, paymentType, street, houseNo, city, state, country, zip)
    }

    override fun signupLevelThreeDirectCash(userID: String, email: String, estimateType: String, estimationFee: String, dob: String, payoutType: String) {
        mInteractor.signupLevelThreeDirectCash(userID, email, estimateType, estimationFee,dob, payoutType)
    }


    val mInteractor = CompanySignupInteractor(this)
}