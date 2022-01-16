package com.in10mServiceMan.ui.activities.services

import android.content.Context
import android.widget.ImageView
import com.in10mServiceMan.R
import com.squareup.picasso.Picasso

class GoogleStaticImage(val context: Context,val imageView: ImageView) {

    val userLat=29.374009
    val userLong=47.976461
    val url="https://maps.googleapis.com/maps/api/staticmap?center=${userLat},${userLong}&zoom=15&size=640x640&key=${context.getString(R.string.google_maps_key)}"

    public fun load(){
        Picasso.get().load(url).fit().into(imageView)
    }
}