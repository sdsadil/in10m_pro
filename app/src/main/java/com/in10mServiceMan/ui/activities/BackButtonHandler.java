package com.in10mServiceMan.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;

import com.in10mServiceMan.R;

public class BackButtonHandler {
    private Context context;

    public BackButtonHandler(Context context) {
        this.context = context;
    }

    public void onClick() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogDanger);
        alertDialog.setTitle(context.getResources().getString(R.string.app_name));
        alertDialog.setIcon(R.mipmap.ic_launcher_logo_one_round);
        alertDialog.setMessage(context.getResources().getString(R.string.are_you_sure_you_want_to_leave_the_application));
        alertDialog.setPositiveButton(context.getResources().getString(R.string.yes),
                (dialog, which) -> {
                    ((Activity) context).finishAffinity();
                });
        alertDialog.setNegativeButton(context.getResources().getString(R.string.no),
                (dialog, which) -> {
                    dialog.cancel();
                });
        alertDialog.show();
    }
}
