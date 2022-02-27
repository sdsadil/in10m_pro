package com.in10mServiceMan.utils;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.in10mServiceMan.R;

public class AppProgressBar extends DialogFragment {
    private static final String TAG = AppProgressBar.class.getSimpleName();
    static Dialog dialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.diaglog_progressbar, container, false);
        ProgressBar progressBar = rootView.findViewById(R.id.pbLoader_DialogPb);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorBlack), android.graphics.PorterDuff.Mode.MULTIPLY);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog.getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
            }
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
    }

    public static void showProgressDialog(AppCompatActivity activity) {
        try {
            FragmentManager fm = activity.getSupportFragmentManager();
            AppProgressBar fragment = new AppProgressBar();
            fragment.show(fm, "Progress_Dialog");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalStateException illegalStateEx) {
            illegalStateEx.printStackTrace();
        }
    }

    public static void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        } else {
            Log.e(TAG, "--------- No action performed here ---------");
        }
    }
}
