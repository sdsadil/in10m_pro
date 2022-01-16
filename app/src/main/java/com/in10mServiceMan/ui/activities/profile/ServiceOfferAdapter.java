package com.in10mServiceMan.ui.activities.profile;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
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

public class ServiceOfferAdapter extends RecyclerView.Adapter<ServiceOfferAdapter.ServiceOfferAdapterViewHolder> {

    private List<ServiceData> selectedServiceModelArrayList;
    private Context context;
   /* private RemoveServiceExperienceListener removeServiceExperienceListener;
    private EditSubServicesListener editSubServicesListener;
    private EditTextValuePass textValuePasser;*/

    public ServiceOfferAdapter(List<ServiceData> selectedServiceModelArrayList, Context context) {
        this.selectedServiceModelArrayList = selectedServiceModelArrayList;
        this.context = context;
        /*this.removeServiceExperienceListener = removeServiceExperienceListener;
        this.editSubServicesListener = editSubServicesListener;
        this.textValuePasser = textValuePasser;*/
    }

    @NonNull
    @Override
    public ServiceOfferAdapter.ServiceOfferAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_serviceman_selected_services, parent, false);

        return new ServiceOfferAdapterViewHolder(view);
    }

    private int getUserId() {
        return new localStorage(context).getLoggedInUser().getCustomerId();
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceOfferAdapter.ServiceOfferAdapterViewHolder holder, int position) {

        holder.textViewServiceName.setText(selectedServiceModelArrayList.get(position).getServiceName());
        Drawable mDrawable = ContextCompat.getDrawable(context, R.drawable.r_circle).getConstantState().newDrawable().mutate();//holder.ImageViewColor.getDrawable();
        mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(selectedServiceModelArrayList.get(position).getServiceColor()), PorterDuff.Mode.SRC_IN));
        holder.ImageViewColor.setImageDrawable(mDrawable);
        holder.textViewYearExperience.setText(String.valueOf(selectedServiceModelArrayList.get(position).getExperience()));

        holder.imgCrossIcon.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return selectedServiceModelArrayList.size();
    }

    class ServiceOfferAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView textViewServiceName, txtViewSubServiceName;
        TextView textViewYearExperience;
        ImageView imgCrossIcon, img_edit_icon;
        ImageView ImageViewColor;

        ServiceOfferAdapterViewHolder(View itemView) {
            super(itemView);
            ImageViewColor = itemView.findViewById(R.id.ImageViewColor);
            textViewServiceName = itemView.findViewById(R.id.service_name);
            txtViewSubServiceName = itemView.findViewById(R.id.subService);
            textViewYearExperience = itemView.findViewById(R.id.ServiceExperienceTV);
            imgCrossIcon = itemView.findViewById(R.id.img_cross_icon);
            img_edit_icon = itemView.findViewById(R.id.img_edit_icon);
        }
    }


}
