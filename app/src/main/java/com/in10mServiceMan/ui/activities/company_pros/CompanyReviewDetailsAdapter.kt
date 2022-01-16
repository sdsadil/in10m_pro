package com.in10mServiceMan.ui.activities.company_pros

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API.ServicemanDetailsReview
import kotlinx.android.synthetic.main.card_review_listt.view.*

class CompanyReviewDetailsAdapter(val mList: List<ServicemanDetailsReview>, val context: Context): RecyclerView.Adapter<CompanyReviewDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.card_review_listt, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        Glide.with(context).load(mList[p1].image).placeholder(R.drawable.dummy_user).into(p0.itemview.circleImageView)
        p0.itemview.tv_reviewer_name.text = mList[p1].name + " " + mList[p1].lastname
        p0.itemview.thumbsUpTV1.text = mList[p1].thumbs_up.toString()
        p0.itemview.thumbsDownTV1.text = mList[p1].thumbs_down.toString()

        if (mList[p1].description == null)
            p0.itemview.tv_review_text.visibility = View.GONE
        else
            p0.itemview.tv_review_text.text = mList[p1].description
    }

    class ViewHolder(var itemview: View) : RecyclerView.ViewHolder(itemview)
}