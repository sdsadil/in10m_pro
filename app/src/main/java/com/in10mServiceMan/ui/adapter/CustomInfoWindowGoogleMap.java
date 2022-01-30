package com.in10mServiceMan.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.in10mServiceMan.models.InfoWindowData;

import com.in10mServiceMan.R;
import com.in10mServiceMan.utils.Constants;
import com.squareup.picasso.Picasso;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private String image, name;

    public CustomInfoWindowGoogleMap(Context ctx) {
        context = ctx;
        this.image = Constants.GlobalSettings.Companion.getImage();
        this.name = Constants.GlobalSettings.Companion.getOwner();
        this.name = Constants.GlobalSettings.Companion.getOwner();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.custom_marker_info_window, null);

        AppCompatTextView details_tv = view.findViewById(R.id.infoWindowNameTV);
        ImageView img = view.findViewById(R.id.infoWindowImageIV);
        AppCompatTextView serviceName_tv=view.findViewById(R.id.infoWindowServiceNameTV);

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
        if (infoWindowData != null) {
            details_tv.setText(infoWindowData.getName());
            if (!infoWindowData.getImage().isEmpty())
                Picasso.get().load(infoWindowData.getImage()).placeholder(R.drawable.user_dummy_avatar).error(R.drawable.user_dummy_avatar).into(img);
                serviceName_tv.setText(infoWindowData.getServiceName());
        } else {
            details_tv.setText(name);
            if (!image.isEmpty())
                Picasso.get().load(image).placeholder(R.drawable.user_dummy_avatar).error(R.drawable.user_dummy_avatar).into(img);

        }
        return view;
    }
}
