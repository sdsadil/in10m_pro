package com.in10mServiceMan.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.in10mServiceMan.ui.accound_edit.AddPortFolio
import com.in10mServiceMan.ui.accound_edit.MyAccountEdit
import com.in10mServiceMan.ui.accound_edit.Profile
import com.in10mServiceMan.ui.accound_edit.Services

private const val NUM_TABS = 3

class AccountViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    mContext: Context,
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val mcontext: Context = mContext

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return Profile(mcontext)
            1 -> return Services(mcontext)
        }
        return AddPortFolio(mcontext)
    }
}