package com.in10mServiceMan.ui.adapter.adapterDrawerContent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.activities.model_classes.serviceman_home_navigation_item_model.ServicemanDrawerItemModel;

import java.util.ArrayList;

public class ServicemanDrawerContentAdapter extends RecyclerView.Adapter<ServicemanDrawerContentAdapter.DrawerContentAdapterViewHolder> {

   private Context context;
   private ArrayList<ServicemanDrawerItemModel> servicemanDrawerItemList;

    public ServicemanDrawerContentAdapter(Context context, ArrayList<ServicemanDrawerItemModel> servicemanDrawerItemList) {
        this.context = context;
        this.servicemanDrawerItemList = servicemanDrawerItemList;
    }

    @NonNull
    @Override
    public ServicemanDrawerContentAdapter.DrawerContentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_drawer_content,parent,false);

        return new DrawerContentAdapterViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ServicemanDrawerContentAdapter.DrawerContentAdapterViewHolder holder, int position) {

        holder.drawerContent.setText(servicemanDrawerItemList.get(position).getContentName());

    }

    @Override
    public int getItemCount() {
        return servicemanDrawerItemList.size();
    }

    public class DrawerContentAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView drawerContent;

        public DrawerContentAdapterViewHolder(View itemView) {
            super(itemView);

            drawerContent = itemView.findViewById(R.id.txt_view_content);

        }
    }
}
