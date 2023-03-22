package com.in10mServiceMan.ui.fragments.past_bookings

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.my_bookings.BookingHistoryInterface
import com.in10mServiceMan.ui.activities.my_bookings.ServiceHistoryData
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.booking_list_item.view.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class BookingsAdapter(
    var context: Context,
    var bookingList: List<ServiceHistoryData>,
    var v: BookingHistoryInterface,
) : RecyclerView.Adapter<BookingsAdapter.BookingVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingVH {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.booking_list_item, parent, false)
        return BookingVH(v)
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }

    override fun onBindViewHolder(holder: BookingVH, position: Int) {
        val booking = bookingList[position]
        holder.itemView.findViewById<TextView>(R.id.ServiceManName).text = booking.customer_name

        val img = booking.customer_image
        if (img != "")
            Picasso.get().load(img).placeholder(R.drawable.user_dummy_avatar).fit()
                .into(holder.itemView.serviceManIV)
        /*holder.itemView.thumbsUpTV.text = booking.customerDetails[0].totalTumbsUp.toString()
        holder.itemView.thumbsDownTV.text = booking.customerDetails[0].totalTumbsDown.toString()*/

        //"${booking.services!![0]} - ${getStatusText(booking.serviceStatus!!)}"
        holder.itemView.workCategoryTV.text = "${booking.services[0]}"
        holder.itemView.workStatusTV.text = getStatusText(booking.service_status)
        holder.itemView.textView26.text = mDateFormatConverter(booking.service_date.toString())
        /*   if (booking.price != 0.0)
             holder.itemView.textViewTIME.text = ("KD " + doubleFormatter(booking.price))
         else
             holder.itemView.textViewTIME.text = context.resources.getString(R.string.kd_0_000)

        holder.itemView.setOnClickListener {
              SharedPreferencesHelper.putInt(context, Constants.SharedPrefs.User.COMMON_ID, bookingList[position].booking_id)
              v.adapterTransaction()
          }*/

        holder.itemView.locationDetailsTV.text = booking.address

        val serviceColor = booking.service_color

        val mDrawable =
            ContextCompat.getDrawable(context, R.drawable.r_circle)!!.constantState!!.newDrawable()
                .mutate()//holder.ImageViewColor.getDrawable();
        mDrawable.colorFilter =
            PorterDuffColorFilter(Color.parseColor(serviceColor), PorterDuff.Mode.SRC_IN)
        holder.itemView.view4.setImageDrawable(mDrawable)
    }

    inner class BookingVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    private fun mDateFormatConverter(inputDate: String): String {
        val format1 = SimpleDateFormat("yyyy-MM-dd")
        val format2 = SimpleDateFormat("dd MMM yyyy")
        val date = format1.parse(inputDate)
        return format2.format(date)

    }

    private fun getStatusText(status: Int): String {
        return when (status) {
            1 -> context.getString(R.string.requested)
            2 -> context.getString(R.string.accepted)
            3 -> context.getString(R.string.arrived)
            4 -> context.getString(R.string.ongoing)
            5 -> context.getString(R.string.reached)
            6 -> context.getString(R.string.completed)
            7 -> context.getString(R.string.customer_canceled)
            8 -> context.getString(R.string.serviceman_canceled)
            else -> ""
        }
    }

    private fun doubleFormatter(amount: Double?): String {
        val df = DecimalFormat("#.00")
        return df.format(amount)
    }
}