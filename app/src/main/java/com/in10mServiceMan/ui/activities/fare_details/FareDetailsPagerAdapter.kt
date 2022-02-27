package com.in10mServiceMan.ui.activities.fare_details

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.fare_tab_view.view.*

class FareDetailsPagerAdapter(
    fm: FragmentManager?, private var titles: Array<String> = arrayOf(),
    private var tabImages: Array<Int> = arrayOf(),
    private var fragments: List<Fragment> = ArrayList()
) : FragmentStatePagerAdapter(
    fm!!
) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    fun getTabView(context: Context?, position: Int): View {

        val tabView = LayoutInflater.from(context).inflate(R.layout.fare_tab_view, null)
        tabView.vehicleIV.setImageResource(tabImages[position])
        tabView.vehicleNameTV.text = titles[position]
        return tabView
    }
}