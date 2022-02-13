package com.in10mServiceMan.ui.activities.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.in10mServiceMan.R
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.in10mApplication
import kotlinx.android.synthetic.main.navigation_list_item.view.*

/**
 * Created by Rohit on 25/03/18.
 */
class NavigationAdapter(val context: Context, var navigationCallbacks: NavigationCallbacks?) :
    RecyclerView.Adapter<NavigationAdapter.NavigationVH>() {

    var userType =
        SharedPreferencesHelper.getInt(context, Constants.SharedPrefs.User.PERSON_TYPE, 2)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationVH {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.navigation_list_item, parent, false)
        return NavigationVH(v)
    }

    override fun getItemCount(): Int {
        return if (userType == 3)
            context.resources.getStringArray(R.array.nav_title).size
        else
            context.resources.getStringArray(R.array.nav_titles).size
    }

    override fun onBindViewHolder(holder: NavigationVH, position: Int) {

        /*if (position == 0) {
            holder.itemView?.navCountTV?.visibility = View.VISIBLE //
        } else {
            holder.itemView?.navCountTV?.visibility = View.GONE
        }*/

        if (userType == 2) {
            holder.itemView.navtitleTV?.text =
                context.resources.getStringArray(R.array.nav_titles)[position]
        } else if (userType == 3) {
            holder.itemView.navtitleTV?.text =
                context.resources.getStringArray(R.array.nav_title)[position]
        }

        holder.itemView.setOnClickListener {
            if (userType == 2) {
                when (position) {

                    0 -> navigationCallbacks?.myAccount()

                    1 -> navigationCallbacks?.myBookings()

                    2 -> navigationCallbacks?.myEarnings()

                    3 -> navigationCallbacks?.about()

                    4 -> navigationCallbacks?.privacy()

                    5 -> navigationCallbacks?.contactUs()

                    6 -> navigationCallbacks?.settings()

                    7 -> navigationCallbacks?.logout()

                }
            } else if (userType == 3) {
                when (position) {

                    0 -> navigationCallbacks?.myAccount()

                    1 -> navigationCallbacks?.companyPros()

                    2 -> navigationCallbacks?.myBookings()

                    3 -> navigationCallbacks?.myEarnings()

                    4 -> navigationCallbacks?.about()

                    5 -> navigationCallbacks?.privacy()

                    6 -> navigationCallbacks?.contactUs()

                    7 -> navigationCallbacks?.settings()

                    8 -> navigationCallbacks?.logout()

                }
            }
        }
    }

    inner class NavigationVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    interface NavigationCallbacks {

        fun myAccount()

        fun companyPros()

        fun myBookings()

        fun myEarnings()

        fun contactUs()

        fun settings()

        fun privacy()

        fun about()

        fun logout()
    }
}