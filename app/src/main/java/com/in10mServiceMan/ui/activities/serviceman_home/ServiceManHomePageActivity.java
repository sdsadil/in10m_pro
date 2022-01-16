package com.in10mServiceMan.ui.activities.serviceman_home;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.activities.model_classes.serviceman_home_navigation_item_model.ServicemanDrawerItemModel;
import com.in10mServiceMan.ui.activities.profile.MyAccountActivity;
import com.in10mServiceMan.ui.activities.select_service.SelectServiceActivity;
import com.in10mServiceMan.ui.adapter.adapterDrawerContent.ServicemanDrawerContentAdapter;
import com.in10mServiceMan.ui.listener.OnRecycleItemClickListener;

import java.util.ArrayList;

public class ServiceManHomePageActivity extends AppCompatActivity {

    ArrayList<ServicemanDrawerItemModel> drawerItemArrayList = new ArrayList<>();
    ServicemanDrawerContentAdapter servicemanDrawerContentAdapter ;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewDrawerItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_man_home_page);

        recyclerViewDrawerItem = findViewById(R.id.recycler_view_navigation);


        String itemsArray[]=new String[]{
                getResources().getString(R.string.my_account),
                getResources().getString(R.string.service_history),
                getResources().getString(R.string.about),
                getResources().getString(R.string.privacy),
                getResources().getString(R.string.contact),
                getResources().getString(R.string.settings)};

        for(int i=0; i<itemsArray.length;i++)
        {
            ServicemanDrawerItemModel item=new ServicemanDrawerItemModel();
            item.setContentName(itemsArray[i]);
            drawerItemArrayList.add(item);
        }

        linearLayoutManager = new LinearLayoutManager(ServiceManHomePageActivity.this);
        servicemanDrawerContentAdapter = new ServicemanDrawerContentAdapter(ServiceManHomePageActivity.this,drawerItemArrayList);
        recyclerViewDrawerItem.setLayoutManager(linearLayoutManager);
        recyclerViewDrawerItem.setAdapter(servicemanDrawerContentAdapter);

        recyclerViewDrawerItem.addOnItemTouchListener(new OnRecycleItemClickListener(this, new OnRecycleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                switch (position)
                {
                    case 0:

                        startActivity(new Intent(ServiceManHomePageActivity.this,MyAccountActivity.class));

                        break;
                }

            }
        }));

    }


}
