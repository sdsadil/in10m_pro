package com.in10mServiceMan.ui.activities.select_service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.in10mServiceMan.R;
import com.in10mServiceMan.db.Dummy;
import com.in10mServiceMan.db.SubServiceItem;
import com.in10mServiceMan.ui.activities.model_classes.SubServiceListModel;
import com.in10mServiceMan.ui.adapter.ServicemanSubServicesAdapter;
import com.in10mServiceMan.ui.listener.SubServiceSelectionListener;

import java.util.ArrayList;

public class SelectSubServiceActivity extends AppCompatActivity implements SubServiceSelectionListener {

    RecyclerView recyclerViewSubService;
    ImageView imgServiceIcon, imgSubmitSubServices;
    TextView txtViewServiceName;
    LinearLayoutManager linearLayoutManager;
    ArrayList<SubServiceItem> subServiceList = new ArrayList<>();
    ServicemanSubServicesAdapter servicemanSubServicesAdapter;
    ArrayList<String> subServiceNameList = new ArrayList<>();
    ArrayList<Integer> positionOfService = new ArrayList<>();

    boolean changesInitiate;

    Intent intent;
    String serviceName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sub_service);

        recyclerViewSubService = findViewById(R.id.recyclerViewSubService);
        imgServiceIcon = findViewById(R.id.imgServiceIcon);
        txtViewServiceName = findViewById(R.id.txtViewServiceName);
        imgSubmitSubServices = findViewById(R.id.img_view_done_services);

        intent = getIntent();
        if (intent.getStringExtra("serviceName") != null)
            serviceName = intent.getStringExtra("serviceName");

        imgSubmitSubServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changesInitiate) {
                    intent.putStringArrayListExtra("listOfSubServices", subServiceNameList);
                    intent.putExtra("serviceName", serviceName);
                    //intent.putExtra("requestCode",111);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    finish();
                }
            }
        });

        if (Dummy.INSTANCE.getSubServicesList() != null)
            subServiceList.addAll(Dummy.INSTANCE.getSubServicesList());

        /*subServiceList = new ArrayList<>();
        subServiceList.add(new SubServiceListModel("sink",true));
        subServiceList.add(new SubServiceListModel("abc",false));
        subServiceList.add(new SubServiceListModel("abhcjskjdkls dshj",true));
        subServiceList.add(new SubServiceListModel("jhsdjhs djshdj",false));
        subServiceList.add(new SubServiceListModel("sink",true));
        subServiceList.add(new SubServiceListModel("abc",false));
        subServiceList.add(new SubServiceListModel("abhcjskjdkls dshj",true));
        subServiceList.add(new SubServiceListModel("jhsdjhs djshdj",false));
        subServiceList.add(new SubServiceListModel("sink",true));
        subServiceList.add(new SubServiceListModel("abc",false));
        subServiceList.add(new SubServiceListModel("abhcjskjdkls dshj",true));
        subServiceList.add(new SubServiceListModel("jhsdjhs djshdj",false));*/

        servicemanSubServicesAdapter = new ServicemanSubServicesAdapter(subServiceList, SelectSubServiceActivity.this, SelectSubServiceActivity.this);
        linearLayoutManager = new LinearLayoutManager(SelectSubServiceActivity.this);
        recyclerViewSubService.setLayoutManager(linearLayoutManager);
        recyclerViewSubService.setAdapter(servicemanSubServicesAdapter);

    }

    @Override
    public void subServiceSelected(int poition, String name) {

        changesInitiate = true;

        if (positionOfService.contains(poition))
            subServiceNameList.remove(poition);
        else {
            positionOfService.add(Integer.valueOf(poition));
            subServiceNameList.add(name);
        }


    }
}
