package com.in10mServiceMan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in10mServiceMan.R;
import com.in10mServiceMan.db.SubServiceItem;
import com.in10mServiceMan.ui.activities.model_classes.SubServiceListModel;
import com.in10mServiceMan.ui.listener.SubServiceSelectionListener;

import java.util.ArrayList;

public class ServicemanSubServicesAdapter extends RecyclerView.Adapter<ServicemanSubServicesAdapter.ServicemanSubServicesAdapterViewHolder> {

    ArrayList<SubServiceItem> subServiceList;
    Context context;
    SubServiceSelectionListener subServiceSelectionListener;

    public ServicemanSubServicesAdapter(ArrayList<SubServiceItem> subServiceList, Context context, SubServiceSelectionListener subServiceSelectionListener) {
        this.subServiceList = subServiceList;
        this.context = context;
        this.subServiceSelectionListener = subServiceSelectionListener;
    }

    @NonNull
    @Override
    public ServicemanSubServicesAdapter.ServicemanSubServicesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sub_service_selection,parent,false);

        return new ServicemanSubServicesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicemanSubServicesAdapter.ServicemanSubServicesAdapterViewHolder holder, int position) {


        holder.txtViewSubServiceName.setText(subServiceList.get(position).getSubServiceName());

        holder.relativeSubService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.imgSelectedSubService.getVisibility() == View.VISIBLE)
                holder.imgSelectedSubService.setVisibility(View.GONE);
                else
                    holder.imgSelectedSubService.setVisibility(View.VISIBLE);

                subServiceSelectionListener.subServiceSelected(position,subServiceList.get(position).getSubServiceName());

            }
        });
        /*if(subServiceList.get(position).isSubServicesSelected())
            holder.imgSelectedSubService.setVisibility(View.VISIBLE);
        else
            holder.imgSelectedSubService.setVisibility(View.GONE);*/
    }

    @Override
    public int getItemCount() {
        return subServiceList.size();
    }

    public class ServicemanSubServicesAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView txtViewSubServiceName;
        ImageView imgSelectedSubService;
        RelativeLayout relativeSubService;

        public ServicemanSubServicesAdapterViewHolder(View itemView) {
            super(itemView);

            txtViewSubServiceName = itemView.findViewById(R.id.selectedSubServiceName);
            imgSelectedSubService = itemView.findViewById(R.id.selectedSubService);
            relativeSubService = itemView.findViewById(R.id.relative_sub_service);
        }
    }
}
