package com.in10mServiceMan.ui.activities.profile;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.activities.services.ServiceData;
import com.in10mServiceMan.ui.listener.EditSubServicesListener;
import com.in10mServiceMan.utils.Constants;
import com.in10mServiceMan.utils.SharedPreferencesHelper;
import com.in10mServiceMan.utils.localStorage;

import java.util.List;

public class ServiceOfferAdapter extends RecyclerView.Adapter<ServiceOfferAdapter.ServiceOfferAdapterViewHolder> {

    private List<ServiceData> selectedServiceModelArrayList;
    private Context context;
    boolean isLangArabic, iconVisible = false;
    EditSubServicesListener editSubServicesListener;


    public ServiceOfferAdapter(List<ServiceData> selectedServiceModelArrayList, Context context, EditSubServicesListener editSubServicesListener) {
        this.selectedServiceModelArrayList = selectedServiceModelArrayList;
        this.context = context;
        this.editSubServicesListener = editSubServicesListener;
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
        ServiceData serviceData = selectedServiceModelArrayList.get(position);
        isLangArabic = SharedPreferencesHelper.INSTANCE.getBoolean(context,
                Constants.SharedPrefs.User.IS_LANG_ARB, false);
        if (isLangArabic) {
            holder.textViewServiceName.setText(serviceData.getArName());
        } else {
            holder.textViewServiceName.setText(serviceData.getServiceName());
        }

        if (iconVisible) {
            holder.imgCrossIcon.setVisibility(View.VISIBLE);
            holder.textViewYearExperience.setEnabled(true);
        } else {
            holder.imgCrossIcon.setVisibility(View.GONE);
            holder.textViewYearExperience.setEnabled(false);
        }

        Drawable mDrawable = ContextCompat.getDrawable(context, R.drawable.r_circle).getConstantState().newDrawable().mutate();//holder.ImageViewColor.getDrawable();
        mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(serviceData.getServiceColor()), PorterDuff.Mode.SRC_IN));
        holder.ImageViewColor.setImageDrawable(mDrawable);
        holder.textViewYearExperience.setText(String.valueOf(serviceData.getTotalExperience()));

        holder.imgCrossIcon.setOnClickListener(view -> {
            editSubServicesListener.onDeleteClick(position, serviceData);
        });

        holder.textViewYearExperience.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int exp = Integer.parseInt(s.toString());
                    selectedServiceModelArrayList.get(holder.getAdapterPosition()).setTotalExperience(exp);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedServiceModelArrayList.size();
    }

    class ServiceOfferAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView textViewServiceName, txtViewSubServiceName;
        AppCompatEditText textViewYearExperience;
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

    public void setVisibleDelete(boolean iconVisible) {
        this.iconVisible = iconVisible;
        notifyDataSetChanged();
    }

    public List<ServiceData> getServiceDataList() {
       return selectedServiceModelArrayList;
    }
}
