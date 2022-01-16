package com.in10mServiceMan.ui.activities.item_details

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.image_list_item.view.*
import java.io.File

class ValueImagesAdapter(var activity: Activity, var imageList: MutableList<String> = ArrayList()) : RecyclerView.Adapter<ValueImagesAdapter.ImageVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageVH {
       val v= LayoutInflater.from(parent.context).inflate(R.layout.image_list_item,parent,false)
        return ImageVH(v)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ImageVH, position: Int) {

/*
        Picasso.with(activity).load(File(imageList[position])).fit().into(holder.itemView.itemIV)
*/
        Picasso.get().load(File(imageList[position])).fit().into(holder.itemView.itemIV)

        holder.itemView.setOnClickListener {
            imageList.removeAt(position)
            notifyDataSetChanged()
        }
    }


    inner class ImageVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}