package com.in10mServiceMan.ui.activities.fare_details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.fragments.fare_details.FareDetailsFragment
import kotlinx.android.synthetic.main.activity_fare_details.*
import kotlinx.android.synthetic.main.app_bar_transparent.*

class FareDetailsActivity : AppCompatActivity() {

    private lateinit var fareDetailsPagerAdapter: FareDetailsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fare_details)

        initToolbar()

        initPager()
    }

    private fun initPager() {

        val titles = arrayOf(
            getString(R.string.small_car),
            getString(R.string.half_lorry), getString(R.string.truck)
        )

        val tabImages = arrayOf(
            R.drawable.ic_small_car_selector,
            R.drawable.ic_half_lorry_selector, R.drawable.ic_truck_selector
        )

        val fragments = listOf(
            FareDetailsFragment().getInstance(titles[0]),
            FareDetailsFragment().getInstance(titles[1]),
            FareDetailsFragment().getInstance(titles[2])
        )

        fareDetailsPagerAdapter =
            FareDetailsPagerAdapter(supportFragmentManager, titles, tabImages, fragments)
        pager.adapter = fareDetailsPagerAdapter
        sliding_tabs.setupWithViewPager(pager)

        for (i in 0 until sliding_tabs.tabCount) {
            val tab = sliding_tabs.getTabAt(i)
            tab?.customView = fareDetailsPagerAdapter.getTabView(this, i)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(appBar as Toolbar)
        titleTV.text = getString(R.string.fare_details)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
