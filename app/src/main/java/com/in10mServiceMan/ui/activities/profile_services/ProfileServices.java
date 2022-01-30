package com.in10mServiceMan.ui.activities.profile_services;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.in10mServiceMan.models.HomeService;
import com.in10mServiceMan.models.RequestLinkServiceWithServiceMan;
import com.in10mServiceMan.models.ResponseServiceWithSubService;
import com.in10mServiceMan.models.Service;
import com.in10mServiceMan.models.viewmodels.ServiceWithSubService;
import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.activities.services.ServicesActivity;
import com.in10mServiceMan.ui.activities.sub_services.SubServicesActivity;
import com.in10mServiceMan.ui.adapter.ServicemanSelectedServiceAdapter;
import com.in10mServiceMan.ui.apis.LoginAPI;
import com.in10mServiceMan.ui.base.In10mBaseActivity;
import com.in10mServiceMan.ui.interfaces.EditTextValuePass;
import com.in10mServiceMan.ui.listener.EditSubServicesListener;
import com.in10mServiceMan.ui.listener.RemoveServiceExperienceListener;
import com.in10mServiceMan.utils.LoadingDialog;
import com.in10mServiceMan.utils.localStorage;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileServices extends In10mBaseActivity implements RemoveServiceExperienceListener, EditTextValuePass, EditSubServicesListener {


    private ServicemanSelectedServiceAdapter servicemanSelectedServiceAdapter;
    private RecyclerView recyclerView;
    private TextView txtViewChooseService, chooseService;
    Intent intent;
    private ArrayList<Service> serviceWithSubServices = new ArrayList<>();

    private int REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES = 111;

    //  OnDataPass dataPasser;
    String UserId;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_services);
        //  dataPasser = (OnDataPass) getApplicationContext();
        txtViewChooseService = findViewById(R.id.autocomplete_choose_service);
        chooseService = findViewById(R.id.choose_service);
        ImageView imgDone = findViewById(R.id.img_view_done_services);
        ImageView button_close = findViewById(R.id.button_close);
        loadingDialog = new LoadingDialog(this);
        button_close.setOnClickListener(v -> {
            finish();
        });
        recyclerView = findViewById(R.id.recycler_view);
        intent = getIntent();
        UserId = new localStorage(this).getLoggedInUser().getCustomerId().toString();
        imgDone.setOnClickListener(v -> {
            if (serviceWithSubServices != null && !serviceWithSubServices.isEmpty()) {

                ArrayList<RequestLinkServiceWithServiceMan> serviceWithServiceMEN = new ArrayList<>();
                for (Service aa : serviceWithSubServices) {
                    /*for(SubService bb: aa.getSubServices()){

                        RequestLinkServiceWithServiceMan cc=   new RequestLinkServiceWithServiceMan();
                        cc.setService_id(aa.getService().getService_id().toString());
                        cc.setSubServiceId(bb.getId().toString());
                        cc.setStatus("1");
                        cc.setTotalExperience(String.valueOf(aa.getExperience()));
                        cc.setUserId(UserId);
                        serviceWithServiceMEN.add(cc);
                    }*/
                    RequestLinkServiceWithServiceMan cc = new RequestLinkServiceWithServiceMan();
                    cc.setServiceId(aa.getServiceId().toString());
                    cc.setSubServiceId("");
                    cc.setStatus("1");
                    cc.setTotalExperience("");
                    cc.setUserId(UserId);
                    serviceWithServiceMEN.add(cc);

                }
                callApiTOLinkServicesAndSubService(serviceWithServiceMEN);
            } else {
                Toast.makeText(ProfileServices.this, getString(R.string.please_select_some_services_first), Toast.LENGTH_SHORT).show();
            }
        });

        // private ArrayList<ServicemanSelectedServiceModel> selectedServiceModelArrayList = new ArrayList<>();;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        servicemanSelectedServiceAdapter = new ServicemanSelectedServiceAdapter(serviceWithSubServices, this, ProfileServices.this, this, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(servicemanSelectedServiceAdapter);
        loadExistingServices();
        loadServicemanExperience();

        txtViewChooseService.setOnClickListener(v -> {
            Intent intentStartServiceSelection = new Intent(ProfileServices.this, ServicesActivity.class);
            startActivityForResult(intentStartServiceSelection, REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES);
        });

        chooseService.setOnClickListener(v -> {

            Intent intentStartServiceSelection = new Intent(ProfileServices.this, ServicesActivity.class);
            startActivityForResult(intentStartServiceSelection, REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES);
            //startActivityForResult(REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES,new Intent(mContext,SelectServiceActivity.class));
        });
    }

    private void loadExistingServices() {
        LoginAPI.loginUser().getExistingServiceDetails(Integer.parseInt(UserId)).enqueue(new Callback<HomeService>() {
            @Override
            public void onResponse(Call<HomeService> call, Response<HomeService> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null && response.body().getData().size() > 0) {
                    for (Service service : response.body().getData()) {
                        /*ServiceWithSubService serviceWithSubService =new ServiceWithSubService();
                        serviceWithSubService.setService(service);
                        serviceWithSubService.setSubServices(service.getSubService());
                        if(service.getSubService().size()>0)
                            serviceWithSubService.setExperience(Integer.parseInt(service.getSubService().get(0).getCreatedAt()));
                        serviceWithSubServices.add(serviceWithSubService);*/
                        Service serviceWithSubService = new Service();

                        serviceWithSubService.setSubService(service.getSubService());
                        /*if(service.getSubService().size()>0)
                            serviceWithSubService.setExperience(Integer.parseInt(service.getSubService().get(0).getCreatedAt()));*/
                        serviceWithSubServices.add(serviceWithSubService);
                    }
                    loadServicemanExperience();
                }
            }

            @Override
            public void onFailure(Call<HomeService> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //requestCode =  intent.getIntExtra("requestCode",requestCode);

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES && resultCode == RESULT_OK) {
            if (data.getStringExtra("service") != null) {
                Service serviceWithSubService = new Gson().fromJson(data.getStringExtra("service"), Service.class);
                if (!checkIfAlreadyExist(serviceWithSubService)) {
                    serviceWithSubServices.add(serviceWithSubService);
                }
                loadServicemanExperience();
            }
        }
    }

    private boolean checkIfAlreadyExist(Service serviceWithSubService) {
        for (int i = 0; i < serviceWithSubServices.size(); i++) {
            Service aa = serviceWithSubServices.get(i);
            if (aa.getServiceId().equals(serviceWithSubService.getServiceId())) {
                serviceWithSubServices.set(i, serviceWithSubService);
                return true;
            }
        }
        return false;
    }

    public void loadServicemanExperience() {
        if (serviceWithSubServices.isEmpty()) {
            chooseService.setVisibility(View.VISIBLE);
            txtViewChooseService.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        } else {
            chooseService.setVisibility(View.GONE);
            txtViewChooseService.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        servicemanSelectedServiceAdapter.notifyDataSetChanged();
    }

    void callApiTOLinkServicesAndSubService(ArrayList<RequestLinkServiceWithServiceMan> servicemanBodyModel) {
        LoginAPI.LoginService loginServiceInterface = LoginAPI.loginUser();

        Log.i("eeeLINK", new Gson().toJson(servicemanBodyModel));

        Call<Void> call = loginServiceInterface.linkServiceAndSubService(servicemanBodyModel);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Toast.makeText(ProfileServices.this, getResources().getString(R.string.services_updated_successfully), Toast.LENGTH_LONG).show();
                finish();
                // dataPasser.onDataPass(1);
                if (response.isSuccessful()) {

                } else {
                    try {
                        Log.i("eeeLINKerror2", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("eeeLINKerror", t.getMessage());
                Toast.makeText(ProfileServices.this, getResources().getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                // dataPasser.onDataPass(1);
            }
        });
    }

    private Service getServiceWithId(int serviceId) {
        for (int i = 0; i < serviceWithSubServices.size(); i++) {
            Service aa = serviceWithSubServices.get(i);
            if (serviceId == aa.getServiceId()) {
                return aa;
            }
        }
        return null;
    }

    @Override
    public void onDataPass(int serviceId, String EditTextValue) {
        Service serviceWithSubService = getServiceWithId(serviceId);
        for (int i = 0; i < serviceWithSubServices.size(); i++) {
            Service aa = serviceWithSubServices.get(i);
            if (aa.getServiceId() == serviceId) {

                int val;
                try {
                    val = Integer.parseInt(EditTextValue);
                } catch (NumberFormatException nfe) {
                    val = 1;
                }

                // serviceWithSubService.setExperience(val);
                serviceWithSubServices.set(i, serviceWithSubService);
            }
        }
    }

    @Override
    public void removeService(int position) {
        if (position < serviceWithSubServices.size())
            serviceWithSubServices.remove(position);
        loadServicemanExperience();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onEditClick(int position, ServiceWithSubService serviceWithSubService) {

        loadingDialog.showProgressDialog("");
        LoginAPI.loginUser().getServiceDetails(serviceWithSubService.getService().getServiceId()).enqueue(new Callback<ResponseServiceWithSubService>() {
            @Override
            public void onResponse(Call<ResponseServiceWithSubService> call, Response<ResponseServiceWithSubService> response) {
                loadingDialog.destroyDialog();
                if (response.isSuccessful() && (response.body() != null ? response.body().getData().size() : 0) > 0) {
                    String json = new Gson().toJson(response.body().getData().get(0));
                    Intent intent = new Intent(ProfileServices.this, SubServicesActivity.class);
                    intent.putExtra("service", json);
                    startActivityForResult(intent, REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES);
                }

            }

            @Override
            public void onFailure(Call<ResponseServiceWithSubService> call, Throwable t) {
                loadingDialog.destroyDialog();
            }
        });
    }
}
