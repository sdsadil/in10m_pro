package com.in10mServiceMan.ui.activities.select_service;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.in10mServiceMan.R;
import com.in10mServiceMan.db.Dummy;
import com.in10mServiceMan.db.ServiceItem;
import com.in10mServiceMan.ui.activities.model_classes.ServicesListModel;
import com.in10mServiceMan.ui.adapter.ServicesmanServicesAdapter;
import com.in10mServiceMan.ui.listener.ServiceSelectionListener;

import java.util.ArrayList;

public class SelectServiceActivity extends AppCompatActivity implements ServiceSelectionListener {


    ArrayList<ServicesListModel> servicesList;

    ArrayList<ServiceItem> serviceItemArrayList = new ArrayList<>();
    ArrayList<String> subServiceNameList = new ArrayList<>();

    RecyclerView recyclerViewServicemanServices;
    ServicesmanServicesAdapter servicesmanServicesAdapter;
    LinearLayoutManager linearLayoutManager;

    Intent intent;

    private int REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);


        recyclerViewServicemanServices = findViewById(R.id.recycler_view_choose_service);
        recyclerViewServicemanServices.setNestedScrollingEnabled(true);

        intent = getIntent();

        if (Dummy.INSTANCE.getServicesList() != null)
            serviceItemArrayList.addAll(Dummy.INSTANCE.getServicesList());
        /*servicesList = new ArrayList<>();
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));
        servicesList.add(new ServicesListModel("plumbing","abc"));*/
        servicesmanServicesAdapter = new ServicesmanServicesAdapter(serviceItemArrayList, SelectServiceActivity.this, SelectServiceActivity.this);
        linearLayoutManager = new LinearLayoutManager(SelectServiceActivity.this);
        recyclerViewServicemanServices.setLayoutManager(linearLayoutManager);
        recyclerViewServicemanServices.setAdapter(servicesmanServicesAdapter);


        //servicesmanServicesAdapter.notifyDataSetChanged();
    }

    @Override
    public void selectSubService(int position, String serviceID) {

        Intent intentSelectSubService = new Intent(SelectServiceActivity.this, SelectSubServiceActivity.class);
        intentSelectSubService.putExtra("serviceName", serviceItemArrayList.get(position).getServiceName());

        startActivityForResult(intentSelectSubService, REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES);
        //finish();
        //startActivity(new Intent(SelectServiceActivity.this,SelectSubServiceActivity.class));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES && resultCode == RESULT_OK) {
            subServiceNameList.addAll(data.getStringArrayListExtra("listOfSubServices"));
            intent.putStringArrayListExtra("listOfSubServices", subServiceNameList);
            intent.putExtra("serviceName", data.getStringExtra("serviceName"));
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
