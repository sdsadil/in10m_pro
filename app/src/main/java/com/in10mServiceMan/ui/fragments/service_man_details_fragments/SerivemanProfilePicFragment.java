package com.in10mServiceMan.ui.fragments.service_man_details_fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

//import com.amazonaws.mobile.client.AWSMobileClient;
import com.in10mServiceMan.models.CompleteProfile;
import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.activities.profile.ProfileCompletedActivity;
import com.in10mServiceMan.ui.activities.service_man_details.ServiceManDetailsActivity;
import com.in10mServiceMan.ui.interfaces.ImageViewPass;
import com.in10mServiceMan.ui.interfaces.OnDataPass;
import com.in10mServiceMan.ui.interfaces.OnDataStringPass;
import com.in10mServiceMan.utils.localStorage;
;
import com.squareup.picasso.Picasso;


public class SerivemanProfilePicFragment extends Fragment implements View.OnClickListener,OnDataStringPass {


    ImageView imgDoneProfile,imgCamera,proPic;
    private ServiceManDetailsActivity mContext;
    CompleteProfile userProfile;

    ImageViewPass imageViewPass;
    OnDataPass dataPasser;
    public SerivemanProfilePicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_seriveman_profile_pic, container, false);

        imgDoneProfile = v.findViewById(R.id.img_view_done_profile);
        imgCamera = v.findViewById(R.id.img_camera);
        proPic = v.findViewById(R.id.choose_pic);

        if(new localStorage(getActivity()).getCompleteCustomer() !=null)
        {
            userProfile=new localStorage(getActivity()).getCompleteCustomer();
            Picasso.get().load(userProfile.getImage()).placeholder(R.drawable.dummy_user).fit().into(proPic);

        }else{

        }


        imgDoneProfile.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
        initAmazonAWS();
        return v;
    }
    private void initAmazonAWS() {
//        AWSMobileClient.getInstance().initialize(mContext).execute();
    }
    public static SerivemanProfilePicFragment newInstance(String text) {

        SerivemanProfilePicFragment f = new SerivemanProfilePicFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_camera:
                dataPasser.onDataPass(11);
                imageViewPass.ImageViewPass(proPic);
                // getValueImage();
                break;

            case R.id.img_view_done_profile:

                startActivity(new Intent(mContext,ProfileCompletedActivity.class));

                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        mContext = (ServiceManDetailsActivity) context;
        dataPasser = (OnDataPass) context;
        imageViewPass =(ImageViewPass) context;
        super.onAttach(context);
    }



    @Override
    public void onDataPassString(String data) {
        if(!data.isEmpty()){
            Toast.makeText(getActivity(), "Got Image", Toast.LENGTH_SHORT).show();
            // image got
            Picasso.get().load(data).placeholder(R.drawable.dummy_user).fit().into(proPic);
        }
    }
}
