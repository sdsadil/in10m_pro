package com.in10mServiceMan.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.in10mServiceMan.Models.RequestRemoveSubServicesModel;
import com.in10mServiceMan.Models.Service;
import com.in10mServiceMan.Models.SubService;
import com.in10mServiceMan.Models.ViewModels.ServiceWithSubService;
import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.activities.model_classes.ServicemanSelectedServiceModel;
import com.in10mServiceMan.ui.activities.services.ServiceData;
import com.in10mServiceMan.ui.activities.services.ServicesResponse;
import com.in10mServiceMan.ui.apis.LoginAPI;
import com.in10mServiceMan.ui.interfaces.EditTextValuePass;
import com.in10mServiceMan.ui.listener.EditSubServicesListener;
import com.in10mServiceMan.ui.listener.RemoveServiceExperienceListener;
import com.in10mServiceMan.utils.localStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicemanSelectedServiceAdapter extends RecyclerView.Adapter<ServicemanSelectedServiceAdapter.ServicemanSelectedServiceAdapterViewHolder> {

    private List<Service> selectedServiceModelArrayList;
    private Context context;
    private RemoveServiceExperienceListener removeServiceExperienceListener;
    private EditSubServicesListener editSubServicesListener;
    private EditTextValuePass textValuePasser;

    public ServicemanSelectedServiceAdapter(List<Service> selectedServiceModelArrayList, Context context, RemoveServiceExperienceListener removeServiceExperienceListener, EditTextValuePass textValuePasser, EditSubServicesListener editSubServicesListener) {
        this.selectedServiceModelArrayList = selectedServiceModelArrayList;
        this.context = context;
        this.removeServiceExperienceListener = removeServiceExperienceListener;
        this.editSubServicesListener = editSubServicesListener;
        this.textValuePasser = textValuePasser;
    }

    @NonNull
    @Override
    public ServicemanSelectedServiceAdapter.ServicemanSelectedServiceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_serviceman_selected_services, parent, false);

        return new ServicemanSelectedServiceAdapterViewHolder(view);
    }

    private int getUserId() {
        return new localStorage(context).getLoggedInUser().getCustomerId();
    }

    @Override
    public void onBindViewHolder(@NonNull ServicemanSelectedServiceAdapter.ServicemanSelectedServiceAdapterViewHolder holder, int position) {

        holder.textViewServiceName.setText(selectedServiceModelArrayList.get(0).getServiceName());
        holder.ImageViewColor.setBackgroundColor(Color.parseColor(selectedServiceModelArrayList.get(0).getServiceColor()));
        /*holder.textViewYearExperiance.setText(selectedServiceModelArrayList.get(0));*/
        //selectedServiceModelArrayList.get(position).getService().getServiceName()

       /* StringBuilder subServices = new StringBuilder();
        ArrayList<Integer> subServiceIds=new ArrayList<>();
        for(SubService subSer:selectedServiceModelArrayList.get(position).getSubServices()){
            subServices.append(subSer.getName()).append(",");
            subServiceIds.add(subSer.getId());
        }
        String subService=subServices.substring(0,subServices.length()-1);
        holder.txtViewSubServiceName.setText(subService);
        int serviceId=selectedServiceModelArrayList.get(position).getService().getService_id();

        ServiceWithSubService withSubService=selectedServiceModelArrayList.get(position);

        holder.edit_text_exp_year.setText(String.valueOf(selectedServiceModelArrayList.get(position).getExperience()));
        holder.edit_text_exp_year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                     textValuePasser.onDataPass(serviceId,s.toString());
                }else{
                    textValuePasser.onDataPass(serviceId,String.valueOf(0));
                }
            }
        });

       // holder.ImageViewColor.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.r_circle));

        String serviceColor=selectedServiceModelArrayList.get(position).getService().getServiceColor();
        Log.i("eeeCOLOR",serviceColor);
        Drawable mDrawable = ContextCompat.getDrawable(context, R.drawable.r_circle).getConstantState().newDrawable().mutate();//holder.ImageViewColor.getDrawable();
       // Drawable mDrawable = drawable.getConstantState().newDrawable();//holder.ImageViewColor.getDrawable();
        mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(serviceColor),PorterDuff.Mode.SRC_IN));
        holder.ImageViewColor.setImageDrawable(mDrawable);

       // Picasso.with(context).load(mDrawable)

        //catch (Exception c){

        //}

        holder.imgCrossIcon.setOnClickListener(v -> {
            RequestRemoveSubServicesModel removeSubServicesModel =new RequestRemoveSubServicesModel();
            removeSubServicesModel.setServicemanId(getUserId());
            removeSubServicesModel.setSubServices(subServiceIds);

            Log.i("eeeUNLINK",new Gson().toJson(removeSubServicesModel));

            LoginAPI.loginUser().removeSubServices(removeSubServicesModel).enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    removeServiceExperienceListener.removeService(position);
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {

                }
            });
        });
        holder.img_edit_icon.setOnClickListener(v->{
            editSubServicesListener.onEditClick(position,withSubService);
        });*/
    }

    @Override
    public int getItemCount() {
        return selectedServiceModelArrayList.size();
    }

    class ServicemanSelectedServiceAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView textViewServiceName, txtViewSubServiceName;
        TextView textViewYearExperiance;
        ImageView imgCrossIcon, img_edit_icon;
        ImageView ImageViewColor;

        ServicemanSelectedServiceAdapterViewHolder(View itemView) {
            super(itemView);
            ImageViewColor = itemView.findViewById(R.id.ImageViewColor);
            textViewServiceName = itemView.findViewById(R.id.service_name);
            txtViewSubServiceName = itemView.findViewById(R.id.subService);
            textViewYearExperiance = itemView.findViewById(R.id.ServiceExperienceTV);
            imgCrossIcon = itemView.findViewById(R.id.img_cross_icon);
            img_edit_icon = itemView.findViewById(R.id.img_edit_icon);
        }
    }
}
