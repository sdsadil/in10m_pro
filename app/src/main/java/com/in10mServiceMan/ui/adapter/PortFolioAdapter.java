package com.in10mServiceMan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in10mServiceMan.R;

import java.util.ArrayList;

public class PortFolioAdapter extends RecyclerView.Adapter<PortFolioAdapter.MyViewHolder> {
    ArrayList<String> portFolioImage;
    Context context;

    public PortFolioAdapter(ArrayList<String> portFolioImage, Context context) {
        this.portFolioImage = portFolioImage;
        this.context = context;
    }

    @NonNull
    @Override
    public PortFolioAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addportfolio_itemlay, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PortFolioAdapter.MyViewHolder holder, int position) {
        Glide.with(context).load(portFolioImage.get(position)).placeholder(R.drawable.user_dummy_avatar)
                .into(holder.ivPortFolio_PortFolioItemLay);
    }

    @Override
    public int getItemCount() {
        return portFolioImage.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView ivPortFolio_PortFolioItemLay;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPortFolio_PortFolioItemLay = itemView.findViewById(R.id.ivPortFolio_PortFolioItemLay);
        }
    }
}
