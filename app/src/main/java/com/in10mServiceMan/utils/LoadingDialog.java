package com.in10mServiceMan.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;

import androidx.core.content.ContextCompat;

import com.in10mServiceMan.R;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;

/**
 * Created by HashInclude on 28-10-2016.
 * Copyright © 2016 HashInclude IO
 */

public class LoadingDialog {

    SimpleArcDialog mProgressDialog;
    Context mContext;

    public LoadingDialog(Context mContext) {
        this.mProgressDialog = new SimpleArcDialog(mContext);
        this.mContext = mContext;
    }

    public void updateProgressDialog(String mContent) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.getLoadingTextView().setText(mContent);
        }
    }

    public void showProgressDialog(String mContent) {
        mProgressDialog = new SimpleArcDialog(mContext);
        ArcConfiguration mConfiguration = new ArcConfiguration(mContext);
        mConfiguration.setText((mContent));
        mConfiguration.setColors(new int[]{ContextCompat.getColor(mContext, R.color.colorPrimaryDark)});
        mProgressDialog.setConfiguration(mConfiguration);
        mProgressDialog.show();
        mProgressDialog.setCancelable(true);
        mProgressDialog.getLoadingTextView().setGravity(Gravity.CENTER);
    }

    public void showProgressDialog(String mContent, boolean mCancelable) {
        mProgressDialog = new SimpleArcDialog(mContext);
        ArcConfiguration mConfiguration = new ArcConfiguration(mContext);
        mConfiguration.setText((mContent));
        mConfiguration.setColors(new int[]{ContextCompat.getColor(mContext, R.color.colorPrimaryDark)});
        mProgressDialog.setConfiguration(mConfiguration);
        mProgressDialog.show();
        mProgressDialog.setCancelable(mCancelable);
        mProgressDialog.getLoadingTextView().setGravity(Gravity.CENTER);
    }

    public void destroyDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class LoadingDialogCancelListener implements DialogInterface.OnCancelListener {
        @Override
        public void onCancel(DialogInterface dialogInterface) {

        }
    }


}
