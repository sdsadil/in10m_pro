package com.in10mServiceMan.ui.fragments.service_man_details_fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.in10mServiceMan.models.CompleteProfile;
import com.in10mServiceMan.models.CustomerCompleteProfile;
import com.in10mServiceMan.models.CustomerCompleteProfileAfterUpdate;
import com.in10mServiceMan.models.CustomerUser;
import com.in10mServiceMan.models.RequestUpdateServiceMan;
import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.apis.LoginAPI;
import com.in10mServiceMan.ui.interfaces.OnDataPass;
import com.in10mServiceMan.utils.LoadingDialog;
import com.in10mServiceMan.utils.localStorage;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicemanBAsicDetailsFragment extends Fragment implements View.OnClickListener {

    View v;
    ImageView imgButtonClose, imgButtonDone;
    EditText edtFullName, edtDob, edtEmail, edtTotalExperiance;
    TextView txtViewGender, txtViewWorkingAs;
    AlertDialog alertDialog1;
    String fullName, dob, emailId, gender = "M", workingAs = "", TotalExperiance = "0";
    OnDataPass dataPasser;
    RequestUpdateServiceMan rq = new RequestUpdateServiceMan();
    LoadingDialog loadingDialog;
    Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
        mContext = context;
    }

    public ServicemanBAsicDetailsFragment() {
        // Required empty public constructor
    }

    public void hideKeyboard() {
       /* try {
            InputMethodManager inputMethodManager =(InputMethodManager)Objects.requireNonNull(getActivity()). getSystemService(INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getActivity().getCurrentFocus()).getWindowToken(), 0);
        }
        catch (Exception e){
            Log.d("eeeExtionHideKeyboard",e.getMessage());
        }*/
    }

    private void loadExistingProfile() {
        CustomerUser user = new localStorage(getActivity()).getLoggedInUser();
        LoginAPI.loginUser().getCompleteProfile(user.getCustomerId()).enqueue(new Callback<CustomerCompleteProfile>() {
            @Override
            public void onResponse(Call<CustomerCompleteProfile> call, Response<CustomerCompleteProfile> response) {
                if (response.isSuccessful()) {

                    CompleteProfile profile = response.body().getData();

                    rq.setServiceproviderExperience(profile.getExperience());
                    if (profile.getExperience() != null && !profile.getExperience().isEmpty()) {
                        edtTotalExperiance.setText(profile.getExperience());
                        TotalExperiance = profile.getExperience();
                    }
                    rq.setServiceproviderWorkingAs(profile.getWorkingAs());
                    workingAs = profile.getWorkingAs();
                    if (workingAs != null && !workingAs.isEmpty()) {
                        txtViewWorkingAs.setText(workingAs);
                    }
                    rq.setServiceproviderId(profile.getId().toString());
                    edtFullName.setText(profile.getName());
                    rq.setServiceproviderName(profile.getName());
                    rq.setServiceproviderLastname(profile.getLastname());
                    edtDob.setText(profile.getDob());
                    rq.setServiceproviderDob(profile.getDob());
                    rq.setServiceproviderMobile(profile.getMobile());
                    rq.setServiceproviderCountryCode(profile.getCountryCode());
                    edtEmail.setText(profile.getEmail());
                    rq.setServiceproviderEmail(profile.getEmail());
                    if (profile.getGender() != null)
                        txtViewGender.setText(profile.getGender().equals("M") ? "Male" : "Female");
                    gender = profile.getGender();
                    rq.setServiceproviderGender(gender);
                    rq.setServiceproviderStreetName(profile.getStreetName());
                    rq.setServiceproviderPincode(profile.getPincode());
                    rq.setServiceproviderAddress1(profile.getAddress1());
                    rq.setServiceproviderAdddress2(profile.getAdddress2());
                    rq.setServiceproviderCity(profile.getCity());
                    rq.setServiceproviderCountry(profile.getCountry());
                    rq.setServiceproviderLanguage("en");
                    rq.setServiceproviderLatitude(profile.getLatitude());
                    rq.setServiceproviderLongitude(profile.getLongitude());
                    rq.setServiceproviderImage(profile.getImage().isEmpty() ? "http://35.180.58.90/development/in10m/in10m/public/images/users/default_user_avatar.png" : profile.getImage());
                    //rq.setServiceproviderImage(profile.getImage());
                    rq.setServiceproviderRating(profile.getRating());
                    rq.setServiceproviderCivilId(profile.getCivilId());

                }
            }

            @Override
            public void onFailure(Call<CustomerCompleteProfile> call, Throwable t) {

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_serviceman_basic_details, container, false);
        loadingDialog = new LoadingDialog(mContext);
        edtFullName = v.findViewById(R.id.edt_full_name);
        edtTotalExperiance = v.findViewById(R.id.edit_text_total_eperiance);
        txtViewWorkingAs = v.findViewById(R.id.txt_view_working_as);
        edtDob = v.findViewById(R.id.edt_dob);
        edtEmail = v.findViewById(R.id.edt_email_id);
        txtViewGender = v.findViewById(R.id.txt_view_gender);

        txtViewGender.setOnClickListener(view -> {
            CreateAlertDialogWithRadioButtonGroup();
        });

        txtViewWorkingAs.setOnClickListener(view -> {
            CreateAlertDialogWithRadioButtonGroup2();
        });

        imgButtonClose = v.findViewById(R.id.button_close);
        imgButtonDone = v.findViewById(R.id.img_view_done);

          /*fullName = edtFullName.getText().toString();
          dob = edtDob.getText().toString();
          emailId = edtEmail.getText().toString();
          gender = txtViewGender.getText().toString();*/


        imgButtonDone.setOnClickListener(this);
        imgButtonClose.setOnClickListener(this);

         /* v.findViewById(R.id.mainLayout).setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent motionEvent) {
                  InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                  imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                  return false;
              }
          });*/
        loadExistingProfile();
        return v;
    }

    public void CreateAlertDialogWithRadioButtonGroup2() {


        // Toast.makeText(getActivity(),"hereDROPDOWN",Toast.LENGTH_SHORT).show();

        CharSequence[] values = {" Private ", " Contract ", " Company "};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select working as");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:
                        txtViewWorkingAs.setText("Private");
                        workingAs = "Private";
                        // Toast.makeText(getActivity(), "First Item Clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        txtViewWorkingAs.setText("Contract");
                        workingAs = "Contract";
                        //Toast.makeText(getActivity(), "Second Item Clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        txtViewWorkingAs.setText("Company");
                        workingAs = "Company";

                }
                hideKeyboard();
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }

    public void CreateAlertDialogWithRadioButtonGroup() {


        // Toast.makeText(getActivity(),"hereDROPDOWN",Toast.LENGTH_SHORT).show();

        CharSequence[] values = {" Male", " Female "};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Your Gender");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:
                        txtViewGender.setText("Male");
                        gender = "M";
                        // Toast.makeText(getActivity(), "First Item Clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        txtViewGender.setText("Female");
                        gender = "F";
                        //Toast.makeText(getActivity(), "Second Item Clicked", Toast.LENGTH_LONG).show();
                        break;
                }
                hideKeyboard();
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }

    public static ServicemanBAsicDetailsFragment newInstance(String text) {

        ServicemanBAsicDetailsFragment f = new ServicemanBAsicDetailsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_close:
                getActivity().finish();
                break;

            case R.id.img_view_done:

                fullName = edtFullName.getText().toString();
                dob = edtDob.getText().toString();
                emailId = edtEmail.getText().toString();
                TotalExperiance = edtTotalExperiance.getText().toString();
                //  gender = txtViewGender.getText().toString();

                if (fullName != null && !fullName.isEmpty() && dob != null && !dob.isEmpty() && emailId != null && !emailId.isEmpty() && gender != null && !gender.isEmpty() && workingAs != null && !workingAs.isEmpty()) {
                    if (isValidEmail(emailId))
                        callApiToSaveServiceManDetails();
                    else {
                        edtEmail.requestFocus();
                        Toast.makeText(getContext(), getString(R.string.please_enter_valid_email), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "All fields are mandatory.", Toast.LENGTH_SHORT).show();
                }

                break;
            // case R.id.txt_view_gender :
            //     CreateAlertDialogWithRadioButtonGroup();
            //  break;

        }
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public void callApiToSaveServiceManDetails() {

        CustomerUser user = new localStorage(getActivity()).getLoggedInUser();
        rq.setServiceproviderName(fullName);
        rq.setServiceproviderDob(dob);
        rq.setServiceproviderEmail(emailId);
        rq.setServiceproviderGender(gender);
        rq.setServiceproviderExperience(TotalExperiance);
        rq.setServiceproviderWorkingAs(workingAs);

        Log.i("eeeeUPDATE", new Gson().toJson(rq));

        Call<CustomerCompleteProfileAfterUpdate> call = LoginAPI.loginUser().updateServiceManProfile(rq);

        loadingDialog.showProgressDialog("");

        call.enqueue(new Callback<CustomerCompleteProfileAfterUpdate>() {
            @Override
            public void onResponse(Call<CustomerCompleteProfileAfterUpdate> call, Response<CustomerCompleteProfileAfterUpdate> response) {

                loadingDialog.destroyDialog();
                if (response.isSuccessful()) {
                    new localStorage(getActivity()).saveCompleteCustomer(response.body().getData().get(0));
                    dataPasser.onDataPass(0);
                } else {
                    try {
                        Log.i("Error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerCompleteProfileAfterUpdate> call, Throwable t) {
                loadingDialog.destroyDialog();
                Log.i("Error", t.getMessage());
            }
        });
    }
}
