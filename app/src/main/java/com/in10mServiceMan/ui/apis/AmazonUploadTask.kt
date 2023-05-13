package com.in10mServiceMan.ui.apis

import android.os.AsyncTask
import android.util.Log

import com.in10mServiceMan.utils.in10mApplication
import com.in10mServiceMan.utils.Constants
import java.io.File
import java.lang.Exception


/**
 * Created by HashInclude on 05-05-2018
 * Copyright © 2018 HashInclude IO
 */
//class AmazonUploadTask(var mFilePathList: ArrayList<String>, var mTaskListener: AmazonUploadTaskListener, var mFolder: String,var mobile:String) : AsyncTask<Unit, Unit, Unit>() {
//
//    var mUrlList = arrayListOf<String>()
//    var mUrlHashMap: HashMap<String, String> = hashMapOf()
//    var mCurrentUploadIndex = 0
//
//    private var mTaskCompleted: Boolean = false
//    private var mError = false
//
//    private var mMaximumUpload = 0
//
//
//    override fun onProgressUpdate(vararg values: Unit?) {
//        super.onProgressUpdate(*values)
//        mTaskCompleted = mFilePathList.size == mCurrentUploadIndex
//        if (mTaskCompleted) {
//            mTaskListener.uploadTaskSuccess(mUrlList, mUrlHashMap)
//        } else if (mError) {
//            mTaskListener.uploadTaskFailed("")
//        } else {
//            mTaskListener.uploadTaskProgress(mCurrentUploadIndex, mFilePathList.size)
//        }
//    }
//
//
//    override fun doInBackground(vararg p0: Unit?) {
//        mTaskCompleted = false
//
//        try {
//            val transferUtility = TransferUtility.builder()
//                    .context(in10mApplication.instance)
//                    .awsConfiguration(AWSMobileClient.getInstance().configuration)
//                    .s3Client(AmazonS3Client(AWSMobileClient.getInstance().credentialsProvider))
//                    .build()
//
//            if (mFilePathList.isNotEmpty()) {
//                mMaximumUpload = mFilePathList.size
//                initFileUpload(transferUtility, mFilePathList.get(0))
//            } else {
//                mTaskCompleted = false
//                mError = true
//                onProgressUpdate()
//            }
//        } catch (e: Exception) {
//            mTaskCompleted = false
//            mError = true
//            onProgressUpdate()
//        }
//    }
//
//
//    private fun initFileUpload(transferUtility: TransferUtility, mFilePath: String) {
//        var mFileName =
//                mFolder + "/" +mobile+"-"+ System.currentTimeMillis() + ".jpg"
//        val uploadObserver = transferUtility.upload(mFileName,
//                File(mFilePath), CannedAccessControlList.PublicRead)
//
//        // Attach a listener to the observer to get state update and progress notifications
//        uploadObserver.setTransferListener(object : TransferListener {
//            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
//                val percentDonef = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
//                val percentDone = percentDonef.toInt()
//                Log.d("YourActivity", "ID:" + id + " bytesCurrent: " + bytesCurrent
//                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
//            }
//
//            override fun onStateChanged(id: Int, state: TransferState?) {
//                if (TransferState.COMPLETED == state) {
//                    mCurrentUploadIndex++
//                    var mUrl = "https://in10mdevimages.s3." + Constants.Bucket.REGION + ".amazonaws.com/" + mFileName
//                    mUrlList.add(mUrl)
//                    mUrlHashMap.put(mFilePath, mUrl)
//                    if (mFilePathList.size != mCurrentUploadIndex) {
//                        initFileUpload(transferUtility, mFilePathList.get(mCurrentUploadIndex))
//                    }
//                    onProgressUpdate()
//                } else if (TransferState.FAILED == state) {
//                    mError = true
//                    onProgressUpdate()
//                }
//            }
//
//            override fun onError(id: Int, ex: Exception?) {
//                mError = true
//                onProgressUpdate()
//            }
//
//
//        })
//
//    }
//
//    interface AmazonUploadTaskListener {
//        fun uploadTaskFailed(mMessage: String)
//        fun uploadTaskSuccess(mUrlList: ArrayList<String>, mUrlHashMap: HashMap<String, String>)
//        fun uploadTaskProgress(mCurrentUploadIndex: Int, mSize: Int)
//    }
//
//}