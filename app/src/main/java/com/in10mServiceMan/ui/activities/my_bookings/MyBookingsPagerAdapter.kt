package com.in10mServiceMan.ui.activities.my_bookings

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.in10mServiceMan.R
import com.in10mServiceMan.utils.in10mApplication

class MyBookingsPagerAdapter(fm: FragmentManager?,
                             private var fragments:List<Fragment> = ArrayList()) : FragmentStatePagerAdapter(fm) {


    val titles= arrayOf(in10mApplication.instance?.getString(R.string.all),
            in10mApplication.instance?.getString(R.string.favorite))

    override fun getItem(position: Int): Fragment= fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}