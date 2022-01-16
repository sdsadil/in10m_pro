package com.in10mServiceMan.ui.activities.rating

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.in10mServiceMan.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_reviews_layout.view.*
import java.text.SimpleDateFormat

class ReviewsAdapter(var context: Context, val reviewList: List<ReviewsData>) : RecyclerView.Adapter<ReviewsAdapter.ReviewVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.custom_reviews_layout, parent, false)
        return ReviewVH(v)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: ReviewVH, position: Int) {
        val review = reviewList[position]

        holder.itemView.serviceTitleTV.text = review?.serviceName
        if (review?.description != null)
            holder.itemView.reviewDescriptionTV.text = review?.description
        else
            holder.itemView.reviewDescriptionTV.visibility = View.GONE
        if (review?.priceRating == 1) {
            holder.itemView.reviewPriceIV.setImageResource(R.drawable.tumbsupgray)
        } else {
            holder.itemView.reviewPriceIV.setImageResource(R.drawable.tumbsdowngray)
        }
        if (review?.knowledgeRating == 1) {
            holder.itemView.reviewKnowledgeIV.setImageResource(R.drawable.tumbsupgray)
        } else {
            holder.itemView.reviewKnowledgeIV.setImageResource(R.drawable.tumbsdowngray)
        }
        if (review?.overallRating == 1) {
            holder.itemView.reviewOverallIV.setImageResource(R.drawable.tumbsupgray)
        } else {
            holder.itemView.reviewOverallIV.setImageResource(R.drawable.tumbsdowngray)
        }
        if (review.customerImage != null)
            Picasso.get().load(review.customerImage).placeholder(R.drawable.user_dummy_avatar).error(R.drawable.user_dummy_avatar).into(holder.itemView.reviewerIconIV)
        holder.itemView.reviewerNameTV.text = review.customerName
        holder.itemView.reviewerLocationTV.text = review.serviceAddress
        holder.itemView.reviewerDateTV.text = mDateFormatConverter(review.serviceDate!!)
        val mDrawable = ContextCompat.getDrawable(context, R.drawable.r_circle)!!.constantState!!.newDrawable().mutate()//holder.ImageViewColor.getDrawable();
        // Drawable mDrawable = drawable.getConstantState().newDrawable();//holder.ImageViewColor.getDrawable();
        mDrawable.colorFilter = PorterDuffColorFilter(Color.parseColor(review.serviceColor!!), PorterDuff.Mode.SRC_IN)
        holder.itemView.serviceIconIV.setImageDrawable(mDrawable)
    }


    inner class ReviewVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    private fun mDateFormatConverter(inputDate: String): String {
        val format1 = SimpleDateFormat("yyyy-MM-dd")
        val format2 = SimpleDateFormat("dd MMM yyyy")
        val date = format1.parse(inputDate)
        return format2.format(date)

    }

    fun getStatusText(status: Int): String {
        return when (status) {
            1 -> "Requested"
            2 -> "Accepted"
            3 -> "Arrived"
            4 -> "Ongoing"
            5 -> "Reached"
            6 -> "Completed"
            7 -> "Customer Canceled"
            8 -> "Serviceman Canceled"
            else -> ""
        }
    }
}