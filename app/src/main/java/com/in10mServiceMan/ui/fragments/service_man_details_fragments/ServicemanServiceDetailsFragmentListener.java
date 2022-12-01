package com.in10mServiceMan.ui.fragments.service_man_details_fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.in10mServiceMan.models.HomeService;
import com.in10mServiceMan.models.RequestLinkServiceWithServiceMan;
import com.in10mServiceMan.models.ResponseServiceWithSubService;
import com.in10mServiceMan.models.Service;
import com.in10mServiceMan.models.SubService;
import com.in10mServiceMan.models.viewmodels.ServiceWithSubService;
import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.activities.service_man_details.ServiceManDetailsActivity;
import com.in10mServiceMan.ui.activities.services.ServiceData;
import com.in10mServiceMan.ui.activities.services.ServicesActivity;
import com.in10mServiceMan.ui.activities.sub_services.SubServicesActivity;
import com.in10mServiceMan.ui.adapter.ServicemanSelectedServiceAdapter;
import com.in10mServiceMan.ui.apis.APIClient;
import com.in10mServiceMan.ui.apis.ApiInterface;
import com.in10mServiceMan.ui.interfaces.EditTextValuePass;
import com.in10mServiceMan.ui.interfaces.OnDataPass;
import com.in10mServiceMan.ui.listener.EditSubServicesListener;
import com.in10mServiceMan.ui.listener.RemoveServiceExperienceListener;
import com.in10mServiceMan.utils.LoadingDialog;
import com.in10mServiceMan.utils.localStorage;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ServicemanServiceDetailsFragmentListener extends Fragment implements RemoveServiceExperienceListener, EditTextValuePass, EditSubServicesListener {


    private ServicemanSelectedServiceAdapter servicemanSelectedServiceAdapter;
    private ServiceManDetailsActivity mContext;
    private RecyclerView recyclerView;
    private TextView txtViewChooseService, chooseService;
    Intent intent;
    /*
        private ArrayList<ServiceWithSubService> serviceWithSubServices = new ArrayList<>();
    */
    private ArrayList<Service> serviceWithSubServices = new ArrayList<>();

    private int REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES = 111;
    private int REQUEST_CODE_SELECTION_SUBSERVICES_ONLY = 222;

    OnDataPass dataPasser;
    String UserId;
    LoadingDialog loadingDialog;

    public ServicemanServiceDetailsFragmentListener() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_serviceman_service_details, container, false);

        txtViewChooseService = view.findViewById(R.id.autocomplete_choose_service);
        chooseService = view.findViewById(R.id.choose_service);
        ImageView imgDone = view.findViewById(R.id.img_view_done_services);
        recyclerView = view.findViewById(R.id.recycler_view);
        intent = mContext.getIntent();
        loadingDialog = new LoadingDialog(mContext);
        UserId = new localStorage(getActivity()).getLoggedInUser().getCustomerId().toString();
        imgDone.setOnClickListener(v -> {
            if (serviceWithSubServices != null && !serviceWithSubServices.isEmpty()) {

                ArrayList<RequestLinkServiceWithServiceMan> serviceWithServiceMEN = new ArrayList<>();
                for (Service aa : serviceWithSubServices) {
                    for (SubService bb : aa.getSubService()) {

                        RequestLinkServiceWithServiceMan cc = new RequestLinkServiceWithServiceMan();
                        cc.setServiceId(aa.getServiceId().toString());
                        cc.setSubServiceId("");
                        cc.setStatus("1");
                        cc.setTotalExperience("1");
                        cc.setUserId(UserId);
                        serviceWithServiceMEN.add(cc);
                    }
                }
                callApiTOLinkServicesAndSubService(serviceWithServiceMEN);
            } else {
                Toast.makeText(mContext, "please select some services first.", Toast.LENGTH_SHORT).show();
            }
        });

        // private ArrayList<ServicemanSelectedServiceModel> selectedServiceModelArrayList = new ArrayList<>();;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        servicemanSelectedServiceAdapter = new ServicemanSelectedServiceAdapter(serviceWithSubServices, mContext, ServicemanServiceDetailsFragmentListener.this, this, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(servicemanSelectedServiceAdapter);
        loadExistingServices();
        loadServicemanExperience();

        txtViewChooseService.setOnClickListener(v -> {
            Intent intentStartServiceSelection = new Intent(mContext, ServicesActivity.class);
            startActivityForResult(intentStartServiceSelection, REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES);
        });

        chooseService.setOnClickListener(v -> {

            Intent intentStartServiceSelection = new Intent(mContext, ServicesActivity.class);
            startActivityForResult(intentStartServiceSelection, REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES);
            //startActivityForResult(REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES,new Intent(mContext,SelectServiceActivity.class));
        });

        return view;
    }

    private void loadExistingServices() {
        APIClient.getApiInterface().getExistingServiceDetails(Integer.parseInt(UserId)).enqueue(new Callback<HomeService>() {
            @Override
            public void onResponse(Call<HomeService> call, Response<HomeService> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null && response.body().getData().size() > 0) {
                    for (Service service : response.body().getData()) {
                        ServiceWithSubService serviceWithSubService = new ServiceWithSubService();
                        serviceWithSubService.setService(service);
                        serviceWithSubService.setSubServices(service.getSubService());
                        /*if (service.getSubService().size() > 0)
                            serviceWithSubService.setExperience(Integer.parseInt(service.getSubService().get(0).getCreatedAt()));*/
                        serviceWithSubServices.add(service);
                    }
                    loadServicemanExperience();
                }
            }

            @Override
            public void onFailure(Call<HomeService> call, Throwable t) {

            }
        });
    }


    public static ServicemanServiceDetailsFragmentListener newInstance(String text) {

        ServicemanServiceDetailsFragmentListener f = new ServicemanServiceDetailsFragmentListener();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onAttach(Context context) {
        mContext = (ServiceManDetailsActivity) context;
        super.onAttach(context);
        dataPasser = (OnDataPass) mContext;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //requestCode =  intent.getIntExtra("requestCode",requestCode);

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES && resultCode == RESULT_OK) {
            if (data.getStringExtra("service") != null) {
/*
                ServiceWithSubService serviceWithSubService = new Gson().fromJson(data.getStringExtra("service"), ServiceWithSubService.class);
*/
                Service serviceWithSubService = new Gson().fromJson(data.getStringExtra("service"), Service.class);

                if (!checkIfAlreadyExist(serviceWithSubService)) {
                    serviceWithSubServices.add(serviceWithSubService);
                }
                loadServicemanExperience();
            }
        }
    }

    /*private boolean checkIfAlreadyExist(ServiceWithSubService serviceWithSubService) {
        for (int i = 0; i < serviceWithSubServices.size(); i++) {
            ServiceWithSubService aa = serviceWithSubServices.get(i);
            if (aa.getService().getService_id().equals(serviceWithSubService.getService().getService_id())) {
                serviceWithSubServices.set(i, serviceWithSubService);
                return true;
            }
        }
        return false;
    }*/
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

    @Override
    public void removeService(int position) {
        serviceWithSubServices.remove(position);
        loadServicemanExperience();
    }

    void callApiTOLinkServicesAndSubService(ArrayList<RequestLinkServiceWithServiceMan> servicemanBodyModel) {
        ApiInterface loginServiceInterface = APIClient.getApiInterface();

        Call<Void> call = loginServiceInterface.linkServiceAndSubService(servicemanBodyModel);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                dataPasser.onDataPass(1);
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
                dataPasser.onDataPass(1);
            }
        });
    }

    /*private ServiceWithSubService getServiceWithId(int serviceId) {
        for (int i = 0; i < serviceWithSubServices.size(); i++) {
            ServiceWithSubService aa = serviceWithSubServices.get(i);
            if (serviceId == aa.getService().getService_id()) {
                return aa;
            }
        }
        return null;
    }*/

    private Service getServiceWithId(int serviceId) {
        for (int i = 0; i < serviceWithSubServices.size(); i++) {
            Service aa = serviceWithSubServices.get(i);
            if (serviceId == aa.getServiceId()) {
                return aa;
            }
        }
        return null;
    }

    /*@Override
    public void onDataPass(int serviceId, String EditTextValue) {
        ServiceWithSubService serviceWithSubService = getServiceWithId(serviceId);
        for (int i = 0; i < serviceWithSubServices.size(); i++) {
            ServiceWithSubService aa = serviceWithSubServices.get(i);
            if (aa.getService().getService_id() == serviceId) {

                int val;
                try {
                    val = Integer.parseInt(EditTextValue);
                } catch (NumberFormatException nfe) {
                    val = 1;
                }

                serviceWithSubService.setExperience(val);
                serviceWithSubServices.set(i, serviceWithSubService);
            }
        }
    }*/
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

/*
                serviceWithSubService.setExperienc(val);
*/
                serviceWithSubServices.set(i, serviceWithSubService);
            }
        }
    }

    @Override
    public void onEditClick(int position, ServiceWithSubService serviceWithSubService) {

        loadingDialog.showProgressDialog("");
        APIClient.getApiInterface().getServiceDetails(serviceWithSubService.getService().getServiceId()).enqueue(new Callback<ResponseServiceWithSubService>() {
            @Override
            public void onResponse(Call<ResponseServiceWithSubService> call, Response<ResponseServiceWithSubService> response) {
                loadingDialog.destroyDialog();
                if (response.isSuccessful() && (response.body() != null ? response.body().getData().size() : 0) > 0) {
                    String json = new Gson().toJson(response.body().getData().get(0));
                    Intent intent = new Intent(mContext, SubServicesActivity.class);
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

    @Override
    public void onDeleteClick(int position, ServiceData serviceData) {

    }
}
