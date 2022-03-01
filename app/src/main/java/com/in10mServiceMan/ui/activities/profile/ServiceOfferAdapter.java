package com.in10mServiceMan.ui.activities.profile;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.activities.services.ServiceData;
import com.in10mServiceMan.utils.Constants;
import com.in10mServiceMan.utils.SharedPreferencesHelper;
import com.in10mServiceMan.utils.localStorage;

import java.util.List;

public class ServiceOfferAdapter extends RecyclerView.Adapter<ServiceOfferAdapter.ServiceOfferAdapterViewHolder> {

    private List<ServiceData> selectedServiceModelArrayList;
    private Context context;
    boolean isLangArabic;
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
        isLangArabic = SharedPreferencesHelper.INSTANCE.getBoolean(context,
                Constants.SharedPrefs.User.IS_LANG_ARB, false);
        if (isLangArabic) {
            holder.textViewServiceName.setText(selectedServiceModelArrayList.get(position).getArName());
        } else {
            holder.textViewServiceName.setText(selectedServiceModelArrayList.get(position).getServiceName());
        }
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
