package com.in10mServiceMan.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in10mServiceMan.ui.base.In10mBaseActivity;

import com.in10mServiceMan.Models.CompleteProfile;
import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.activities.home.HomeActivity;
import com.in10mServiceMan.ui.activities.serviceman_home.ServiceManHomePageActivity;
import com.in10mServiceMan.ui.activities.tracking_map.MapTrackingActivity;
import com.in10mServiceMan.utils.localStorage;
import com.squareup.picasso.Picasso;

public class ProfileCompletedActivity extends In10mBaseActivity {

    ImageView imgStartService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_completed);

        CompleteProfile user=new localStorage(this).getCompleteCustomer();

        ((TextView)findViewById(R.id.txt_view_user_name)).setText(user.getName());

        imgStartService = findViewById(R.id.img_done_start);
        ImageView img_user_profile = findViewById(R.id.img_user_profile);
        Picasso.get().load(user.getImage()).placeholder(R.drawable.dummy_user).fit().into(img_user_profile);

        imgStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileCompletedActivity.this,MapTrackingActivity.class));
            }
        });
    }
}
