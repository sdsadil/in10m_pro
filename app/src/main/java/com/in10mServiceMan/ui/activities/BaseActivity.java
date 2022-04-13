package com.in10mServiceMan.ui.activities;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.in10mServiceMan.R;
import com.in10mServiceMan.models.DeviceTokenPojo;
import com.in10mServiceMan.ui.apis.APIClient;
import com.in10mServiceMan.utils.AppProgressBar;
import com.in10mServiceMan.utils.Constants;
import com.in10mServiceMan.utils.LoadingDialog;
import com.in10mServiceMan.utils.SharedPreferencesHelper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class BaseActivity extends AppCompatActivity implements ServiceConnection {

    LoadingDialog mProgressDialog;
    static Context mContext;
    private SinchService.SinchServiceInterface mSinchServiceInterface;
//    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        mContext = this;
        mProgressDialog = new LoadingDialog(this);
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyboard();
        setLangFunc();
//        callDeviceTokenApi();
    }

    public void showProgressDialog(int mContent) {
//        mProgressDialog = new LoadingDialog(this);
//        mProgressDialog.showProgressDialog(getString(mContent));
        AppProgressBar.showProgressDialog(this);
    }

    public void showProgressDialog(String mContent) {
//        mProgressDialog = new LoadingDialog(this);
//        mProgressDialog.showProgressDialog(mContent);
        AppProgressBar.showProgressDialog(this);
    }

    public void showProgressDialog(String mContent, boolean mCancelable) {
//        mProgressDialog = new LoadingDialog(this);
//        mProgressDialog.showProgressDialog(mContent, mCancelable);
        AppProgressBar.showProgressDialog(this);
    }

    public void showProgressDialog(String mContent, boolean mCancelable, int progress) {
//        mProgressDialog = new LoadingDialog(this);
//        mProgressDialog.showProgressDialog(mContent, mCancelable);
        AppProgressBar.showProgressDialog(this);

    }

    public void updateProgress(int progress) {
//        mProgressDialog = new LoadingDialog(this);
//        mProgressDialog.showProgressDialog("" + progress + "%");
        AppProgressBar.showProgressDialog(this);
    }


    public void destroyDialog() {
      /*  try {
            if (mProgressDialog != null) {
                mProgressDialog.destroyDialog();
            }
            mProgressDialog = null;
        } catch (Exception mE) {
        }*/
        AppProgressBar.dismissProgressDialog();
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (SinchService.class.getName().equals(componentName.getClassName())) {
            mSinchServiceInterface = (SinchService.SinchServiceInterface) iBinder;
            onServiceConnected();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        if (SinchService.class.getName().equals(componentName.getClassName())) {
            mSinchServiceInterface = null;
            onServiceDisconnected();
        }
    }

    protected void onServiceConnected() {
        // for subclasses
    }

    protected void onServiceDisconnected() {
        // for subclasses
    }

    protected SinchService.SinchServiceInterface getSinchServiceInterface() {
        return mSinchServiceInterface;
    }

    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SinchService.MESSAGE_PERMISSIONS_NEEDED:
                    Bundle bundle = msg.getData();
                    String requiredPermission = bundle.getString(SinchService.REQUIRED_PERMISSION);
                    ActivityCompat.requestPermissions(BaseActivity.this, new String[]{requiredPermission}, 0);
                    break;
            }
        }
    });

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean granted = grantResults.length > 0;
        for (int grantResult : grantResults) {
            granted &= grantResult == PackageManager.PERMISSION_GRANTED;
        }
        if (granted) {
            // Toast.makeText(this, "You may now place a call", Toast.LENGTH_LONG).show();
        } else {
            // Toast.makeText(this, "This application needs permission to use your microphone and camera to function properly.", Toast.LENGTH_LONG).show();
        }
        mSinchServiceInterface.retryStartAfterPermissionGranted();
    }

    private void bindService() {
        Intent serviceIntent = new Intent(this, SinchService.class);
        serviceIntent.putExtra(SinchService.MESSENGER, messenger);
        getApplicationContext().bindService(serviceIntent, this, BIND_AUTO_CREATE);
    }

    public void ShowToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void languageChangeDialogView() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.language_popup);
        final Window window = dialog.getWindow();
        Objects.requireNonNull(window).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout llEnglish_LangPopUpLay = dialog.findViewById(R.id.llEnglish_LangPopUpLay);
        LinearLayout llArabic_LangPopUpLay = dialog.findViewById(R.id.llArabic_LangPopUpLay);
        AppCompatImageView ivClose_LangPopUp = dialog.findViewById(R.id.ivClose_LangPopUp);

        ivClose_LangPopUp.setOnClickListener(v -> dialog.dismiss());

        llEnglish_LangPopUpLay.setOnClickListener(v -> {
            SharedPreferencesHelper.INSTANCE.putBoolean(mContext, Constants.SharedPrefs.User.IS_LANG_ARB, false);
            setLanguage(this, "en");
            dialog.dismiss();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        });
        llArabic_LangPopUpLay.setOnClickListener(v -> {
            SharedPreferencesHelper.INSTANCE.putBoolean(mContext, Constants.SharedPrefs.User.IS_LANG_ARB, true);
            setLanguage(this, "ar");
            dialog.dismiss();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        });
        dialog.show();
    }

    public void setLangFunc() {
        boolean isLangArabic = SharedPreferencesHelper.INSTANCE.getBoolean(mContext,
                Constants.SharedPrefs.User.IS_LANG_ARB, false);
        if (isLangArabic) {
            setLanguage(this, "ar");
        } else {
            setLanguage(this, "en");
        }
    }

    public void setLanguage(Context c, String lang) {
        Locale localeNew = new Locale(lang);
        Locale.setDefault(localeNew);

        Resources res = c.getResources();
        Configuration newConfig = new Configuration(res.getConfiguration());
        newConfig.locale = localeNew;
        newConfig.setLayoutDirection(localeNew);
        res.updateConfiguration(newConfig, res.getDisplayMetrics());
        newConfig.setLocale(localeNew);
        c.createConfigurationContext(newConfig);
    }

    public void callDeviceTokenApi() {
        String isLoggedIn = SharedPreferencesHelper.INSTANCE.getString(mContext,
                Constants.SharedPrefs.User.Companion.getAUTH_TOKEN(), "");
        boolean isDeviceTokenUpdated = SharedPreferencesHelper.INSTANCE.getBoolean(mContext,
                Constants.SharedPrefs.User.IS_DEVICETOKEN_UPDATED, false);
        if (!isLoggedIn.equals("")) {
            if (!isDeviceTokenUpdated) {
                String userId = SharedPreferencesHelper.INSTANCE.getString(mContext,
                        Constants.SharedPrefs.User.Companion.getUSER_ID(), "");
                String deviceToken = SharedPreferencesHelper.INSTANCE.getfcmDeviceToken(mContext);
                JsonObject jsonObject = null;
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("device_token", deviceToken);
                    JsonParser jsonParser = new JsonParser();
                    jsonObject = (JsonObject) jsonParser.parse(jsonBody.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Call<DeviceTokenPojo> modelObservable = APIClient.getApiInterface().updateDeviceToken(Integer.parseInt(userId), jsonObject);
                modelObservable.enqueue(new Callback<DeviceTokenPojo>() {
                    @Override
                    public void onResponse(@NotNull Call<DeviceTokenPojo> call, @NotNull Response<DeviceTokenPojo> response) {
                        Log.e("onResponse ", "" + response.isSuccessful());
                        SharedPreferencesHelper.INSTANCE.getBoolean(mContext,
                                Constants.SharedPrefs.User.IS_DEVICETOKEN_UPDATED, true);
                    }

                    @Override
                    public void onFailure(@NotNull Call<DeviceTokenPojo> call, @NotNull Throwable t) {
                        Log.e("onFailure", t.getMessage());
                        SharedPreferencesHelper.INSTANCE.getBoolean(mContext,
                                Constants.SharedPrefs.User.IS_DEVICETOKEN_UPDATED, false);
                    }
                });
            }
        }
    }
}
