package com.in10mServiceMan.ui.activities.my_bookings

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.in10mServiceMan.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_company_booking_history.view.*

class CompanyBookingsAdapter(var context: Context, var bookingList: List<CSHData>) : RecyclerView.Adapter<CompanyBookingsAdapter.BookingVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.custom_company_booking_history, parent, false)
        return BookingVH(v)
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }

    override fun onBindViewHolder(holder: BookingVH, position: Int) {

        val data = bookingList[position]

        holder.itemView.findViewById<TextView>(R.id.ServiceManName).text = data.servicemanName

        val img = if (data.servicemanImage == null) "" else data.servicemanImage
        if (img != "")
            Picasso.get().load(img).placeholder(R.drawable.user_dummy_avatar).fit().into(holder.itemView.serviceManIV)
        /*holder.itemView.thumbsUpTV.text = booking.customerDetails[0].totalTumbsUp.toString()
        holder.itemView.thumbsDownTV.text = booking.customerDetails[0].totalTumbsDown.toString()*/

        holder.itemView.totalBookingTV.text = "Total Bookings :" + data.totalBookings.toString()
        holder.itemView.textViewTIME.text = "Total Amount : " + data.totalPrice

    }


    inner class BookingVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}