package com.in10mServiceMan.ui.activities.profile

import com.in10mServiceMan.Models.CustomerCompleteProfile
import com.in10mServiceMan.Models.CustomerCompleteProfileAfterUpdate
import com.in10mServiceMan.Models.RequestUpdateServiceMan

class ProfilePresenter(val view: IProfileView) : IProfileInteractor, IProfileInteractorListener {
    override fun onCompleteProfileReceived(metaData: CustomerCompleteProfile) {
        view.onCompleteProfileReceived(metaData)
    }

    override fun getCompleteProfile(userId: String) {
       mIterator.getCompleteProfile(userId)
    }

    override fun onProfileUpdated(mData: CustomerCompleteProfileAfterUpdate) {
        view.onProfileUpdated(mData)
    }

    override fun updateProfile(header: String, request: RequestUpdateServiceMan) {
        mIterator.updateProfile(header, request)
    }

    override fun onDPUpdated(mData: ImageUpdateResponse) {
        view.onDPUpdated(mData)
    }

    override fun onFailed(msg: String) {
        view.onFailed(msg)
    }

    override fun updateProfilePicture(header: String, serviceManId: String, imageUri: String) {
        mIterator.updateProfilePicture(header, serviceManId, imageUri)
    }

    private val mIterator = ProfileInteractor(this)
}