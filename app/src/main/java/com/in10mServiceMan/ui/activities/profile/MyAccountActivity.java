package com.in10mServiceMan.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.activities.model_classes.ServicemanSelectedServiceModel;
import com.in10mServiceMan.ui.activities.select_service.SelectServiceActivity;
import com.in10mServiceMan.ui.activities.service_man_details.ServiceManDetailsActivity;
import com.in10mServiceMan.ui.activities.serviceman_home.ServiceManHomePageActivity;
import com.in10mServiceMan.ui.adapter.ServicemanSelectedServiceAdapter;
import com.in10mServiceMan.ui.listener.RemoveServiceExperienceListener;

import java.util.ArrayList;

public class MyAccountActivity extends AppCompatActivity implements RemoveServiceExperienceListener {

    private ArrayList<ServicemanSelectedServiceModel> selectedServiceModelArrayList;
    private LinearLayoutManager linearLayoutManager;
    private ServicemanSelectedServiceAdapter servicemanSelectedServiceAdapter;
    private RecyclerView recyclerView;

    private TextView txtViewChooseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        recyclerView = findViewById(R.id.recycler_view_account);

        txtViewChooseService = findViewById(R.id.autocomplete_choose_service);

        txtViewChooseService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAccountActivity.this,SelectServiceActivity.class));
            }
        });


       // selectedServiceModelArrayList = new ArrayList<>();
      //  selectedServiceModelArrayList.add(new ServicemanSelectedServiceModel("abcdefg","qwertyasdf",5));
      //  selectedServiceModelArrayList.add(new ServicemanSelectedServiceModel("abcdefg","qwertyasdf",5));
      //  selectedServiceModelArrayList.add(new ServicemanSelectedServiceModel("abcdefg","qwertyasdf",5));
       // selectedServiceModelArrayList.add(new ServicemanSelectedServiceModel("abcdefg","qwertyasdf",5));

       // linearLayoutManager = new LinearLayoutManager(MyAccountActivity.this);
        //servicemanSelectedServiceAdapter = new ServicemanSelectedServiceAdapter(selectedServiceModelArrayList,MyAccountActivity.this,MyAccountActivity.this);
       // recyclerView.setLayoutManager(linearLayoutManager);
       // recyclerView.setAdapter(servicemanSelectedServiceAdapter);
    }

    @Override
    public void removeService(int position) {

    }
}
