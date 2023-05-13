package com.in10mServiceMan.ui.activities.service_man_details;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.in10mServiceMan.models.CompleteProfile;
import com.in10mServiceMan.models.CustomerCompleteProfileAfterUpdate;
import com.in10mServiceMan.models.RequestUpdateServiceMan;
import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.activities.BackButtonHandler;
//import com.in10mServiceMan.ui.apis.AmazonUploadTask;
import com.in10mServiceMan.ui.apis.APIClient;
import com.in10mServiceMan.ui.fragments.service_man_details_fragments.SerivemanProfilePicFragment;
import com.in10mServiceMan.ui.fragments.service_man_details_fragments.ServicemanBAsicDetailsFragment;
import com.in10mServiceMan.ui.fragments.service_man_details_fragments.ServicemanServiceDetailsFragmentListener;
import com.in10mServiceMan.ui.interfaces.ImageViewPass;
import com.in10mServiceMan.ui.interfaces.OnDataPass;
import com.in10mServiceMan.utils.ImageFetcher;
import com.in10mServiceMan.utils.LoadingDialog;
import com.in10mServiceMan.utils.PicassoCache;
import com.in10mServiceMan.utils.localStorage;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ServiceManDetailsActivity extends AppCompatActivity implements OnDataPass, ImageFetcher.OnImageAddedCallback, ImageViewPass {

    String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    int permsRequestCode = 1;

    ViewPager pager;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    BackButtonHandler buttonHandler;
    int selectedIndex = 0;
    boolean isProfileUpdated = false;
    ImageFetcher imageFetcher;
    String mImageUploadPath;
    LoadingDialog loadingDialog;
    ImageView proPic;
    // OnDataStringPass pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_man_details);
        if (!hasPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, permsRequestCode);
        }

        dotsLayout = findViewById(R.id.layoutDots);
        pager = findViewById(R.id.view_pager_service_details);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(viewPagerPageChangeListener);
        buttonHandler = new BackButtonHandler(this);
        addBottomDots(0);
        imageFetcher = new ImageFetcher();
        loadingDialog = new LoadingDialog(this);

    }


    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        switch (selectedIndex) {
            case 0:
                buttonHandler.onClick();
                break;
            case 1:
                pager.setCurrentItem(0);
                break;
            case 2:
                pager.setCurrentItem(1);
                break;
        }
        return;
    }

    @Override
    public void onDataPass(int data) {

        if (data == 0) {
            isProfileUpdated = true;
            // Service man updated
            pager.setCurrentItem(1);
        } else if (data == 1) {
            // Service man linked
            pager.setCurrentItem(2);
        } else if (data == 11) {
            // user is trying to change/update the image
            getValueImage();
        }
    }


    public boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void getValueImage() {
        imageFetcher.showSelectImageDialog(this, this, 111);
    }

    @Override
    public void onImageAdded(@NotNull String path, int requestCode) {
        // Toast.makeText(this,"ImageAdded :"+path,Toast.LENGTH_LONG).show();
        mImageUploadPath = path;

        CompleteProfile profile = new localStorage(this).getCompleteCustomer();
        if (mImageUploadPath != null && !mImageUploadPath.isEmpty()) {
            PicassoCache.getPicassoInstance(this).load(new File(mImageUploadPath)).fit().into(proPic);
            loadingDialog.showProgressDialog("");
            uploadImages(mImageUploadPath, profile.getName(), profile.getMobile());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageFetcher.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImages(String mFilePath, String mName, String mEmail) {
//        if (mFilePath != null) {
//            ArrayList<String> myList = new ArrayList<String>(Arrays.asList(mFilePath.split(",")));
//            AmazonUploadTask amazonUploadTask = new AmazonUploadTask(myList, new AmazonUploadTask.AmazonUploadTaskListener() {
//                @Override
//                public void uploadTaskFailed(@NotNull String mMessage) {
//
//                    loadingDialog.destroyDialog();
//                }
//
//                @Override
//                public void uploadTaskSuccess(@NotNull ArrayList<String> mUrlList, @NotNull HashMap<String, String> mUrlHashMap) {
//                    mImageUploadPath = mUrlList.get(0);
//                    updateProfile();
//                }
//
//                @Override
//                public void uploadTaskProgress(int mCurrentUploadIndex, int mSize) {
//
//                }
//            }, "profileimage", mEmail);
//
//            amazonUploadTask.execute();
//        }
    }

    private void updateProfile() {
        CompleteProfile profile = new localStorage(this).getCompleteCustomer();
        RequestUpdateServiceMan rq = new RequestUpdateServiceMan();
        rq.setServiceproviderExperience(profile.getTotalExperience());
        rq.setServiceproviderWorkingAs(profile.getWorkingAs());
        rq.setServiceproviderId(profile.getId().toString());
        rq.setServiceproviderName(profile.getName());
        rq.setServiceproviderLastname(profile.getLastname());
        rq.setServiceproviderDob(profile.getDob());
        rq.setServiceproviderMobile(profile.getMobile());
        rq.setServiceproviderCountryCode(profile.getCountryCode());
        rq.setServiceproviderEmail(profile.getEmail());
        rq.setServiceproviderGender(profile.getGender());
        rq.setServiceproviderStreetName(profile.getStreetName());
        rq.setServiceproviderPincode(profile.getPincode());
        rq.setServiceproviderAddress1(profile.getAddress1());
        rq.setServiceproviderAdddress2(profile.getAdddress2());
        rq.setServiceproviderCity(profile.getCity());
        rq.setServiceproviderCountry(profile.getCountry());
        rq.setServiceproviderLanguage("en");
        rq.setServiceproviderLatitude(profile.getLatitude());
        rq.setServiceproviderLongitude(profile.getLongitude());
        rq.setServiceproviderImage(profile.getImage());
        rq.setServiceproviderRating(profile.getRating());
        rq.setServiceproviderCivilId(profile.getCivilId());
        rq.setServiceproviderImage(mImageUploadPath);

        Log.i("eeeeUPDATEimage", new Gson().toJson(rq));

        Call<CustomerCompleteProfileAfterUpdate> call = APIClient.getApiInterface().updateServiceManProfile(rq);

        call.enqueue(new Callback<CustomerCompleteProfileAfterUpdate>() {
            @Override
            public void onResponse(Call<CustomerCompleteProfileAfterUpdate> call, Response<CustomerCompleteProfileAfterUpdate> response) {
                loadingDialog.destroyDialog();

                if (response.isSuccessful()) {
                    // Toast.makeText(getContext())
                    Log.i("eeeeBody", new Gson().toJson(response.body()));
                    new localStorage(ServiceManDetailsActivity.this).saveCompleteCustomer(response.body().getData().get(0));

                } else {
                    try {
                        Log.i("eeeeERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerCompleteProfileAfterUpdate> call, Throwable t) {
                Log.i("eeeeERROR22", t.getMessage());
                loadingDialog.destroyDialog();
            }
        });
    }

    @Override
    public void ImageViewPass(ImageView imageView) {
        proPic = imageView;
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return ServicemanBAsicDetailsFragment.newInstance("FirstFragment, Instance 1");
                case 1:
                    return ServicemanServiceDetailsFragmentListener.newInstance("SecondFragment, Instance 1");
                case 2:
                    return SerivemanProfilePicFragment.newInstance("ThirdFragment, Instance 1");
                default:
                    return ServicemanBAsicDetailsFragment.newInstance("FirstFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            selectedIndex = position;
            addBottomDots(position);
            if (position != 0 && !isProfileUpdated) {
                AlertDialog.Builder alertDialog;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog = new AlertDialog.Builder(ServiceManDetailsActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    alertDialog = new AlertDialog.Builder(ServiceManDetailsActivity.this, R.style.MyDialogTheme);
                }


                // Setting Dialog Title
                // alertDialog.setTitle("Leave application?");
                // Setting Dialog Message
                alertDialog.setMessage("Please update your profile details before proceeding any further.");
                // Setting Icon to Dialog
                // alertDialog.setIcon(R.drawable.dialog_icon);
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("OK",
                        (dialog, which) -> pager.setCurrentItem(0));
                // Showing Alert Message
                alertDialog.show();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void addBottomDots(int currentPage) {
        dots = new TextView[3];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.starInactive));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
