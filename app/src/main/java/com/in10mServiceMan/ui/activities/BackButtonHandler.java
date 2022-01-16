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
        AlertDialog.Builder alertDialog;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialog = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            alertDialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        }
        // Setting Dialog Title
        alertDialog.setTitle("in10m Serviceman");
        alertDialog.setIcon(R.mipmap.ic_launcher_logo_one_round);
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to leave the application?");
        // Setting Icon to Dialog
        // alertDialog.setIcon(R.drawable.dialog_icon);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                (dialog, which) -> {
                    //((Activity)context).finish();
                    ((Activity) context).finishAffinity();
                    /*System.exit(0);*/
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                (dialog, which) -> {
                    // Write your code here to invoke NO event
                    dialog.cancel();
                });
        // Showing Alert Message
        alertDialog.show();
    }
}
