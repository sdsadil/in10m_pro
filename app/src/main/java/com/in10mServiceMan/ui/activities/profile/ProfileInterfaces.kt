package com.in10mServiceMan.ui.activities.profile

import com.in10mServiceMan.Models.CustomerCompleteProfile
import com.in10mServiceMan.Models.CustomerCompleteProfileAfterUpdate
import com.in10mServiceMan.Models.RequestUpdateServiceMan

interface IProfileView {
    fun onDPUpdated(mData: ImageUpdateResponse)
    fun onFailed(msg: String)
    fun onProfileUpdated(mData: CustomerCompleteProfileAfterUpdate)
    fun onCompleteProfileReceived(metaData: CustomerCompleteProfile)
}

interface IProfilePresenter {
    fun updateProfilePicture(header: String, serviceManId: String, imageUri: String)
    fun updateProfile(header: String, request: RequestUpdateServiceMan)
    fun getCompleteProfile(userId:String)
}

interface IProfileInteractor : IProfilePresenter
interface IProfileInteractorListener : IProfileView