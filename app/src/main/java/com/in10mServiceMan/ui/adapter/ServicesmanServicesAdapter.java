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
import com.in10mServiceMan.db.ServiceItem;
import com.in10mServiceMan.ui.activities.model_classes.ServicesListModel;
import com.in10mServiceMan.ui.listener.ServiceSelectionListener;

import java.util.ArrayList;
import java.util.Random;

public class ServicesmanServicesAdapter extends RecyclerView.Adapter<ServicesmanServicesAdapter.ServicemanServicesAdapterViewHolder> {


    int[] viewColor = new int[]{R.color.danger,R.color.greenyBlue,R.color.lightBlueBg,R.color.colorPrimary};
    Random random = new Random();

    ArrayList<ServiceItem> servicesList;
    Context context;
    ServiceSelectionListener serviceSelectionListener;

    public ServicesmanServicesAdapter(ArrayList<ServiceItem> servicesList, Context context,ServiceSelectionListener serviceSelectionListener) {
        this.servicesList = servicesList;
        this.context = context;
        this.serviceSelectionListener = serviceSelectionListener;
    }

    @NonNull
    @Override
    public ServicesmanServicesAdapter.ServicemanServicesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_choose_service,parent,false);

        return new ServicemanServicesAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ServicesmanServicesAdapter.ServicemanServicesAdapterViewHolder holder, int position) {

        int n = random.nextInt(4);
        holder.serviceName.setText(servicesList.get(position).getServiceName());
        holder.imgServiceIcon.setImageDrawable(context.getResources().getDrawable(servicesList.get(position).getServiceImage()));

        holder.sideView.setBackgroundColor(context.getResources().getColor(viewColor[n]));

        holder.relativeLayoutService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceSelectionListener.selectSubService(position, "101");
            }
        });

    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public class ServicemanServicesAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName;
        RelativeLayout relativeLayoutService;
        ImageView imgServiceIcon;
        View sideView;

        public ServicemanServicesAdapterViewHolder(View itemView) {
            super(itemView);

            serviceName = itemView.findViewById(R.id.txt_view_service);
            relativeLayoutService = itemView.findViewById(R.id.relative);
            imgServiceIcon = itemView.findViewById(R.id.img_service_icon);
            sideView = itemView.findViewById(R.id.side_view);
        }
    }
}
