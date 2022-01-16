package com.in10mServiceMan.ui.activities.on_going_trips

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.activity_on_going_trips.*
import kotlinx.android.synthetic.main.app_bar_transparent.*


class OnGoingTripsActivity : AppCompatActivity() {

    private var onGoingTripsAdapter = OnGoingTripsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_going_trips)

        initToolbar()
        initRV()


    }


    private fun initRV() {

        tripsRV.layoutManager= LinearLayoutManager(this)
        tripsRV.adapter= onGoingTripsAdapter
        tripsRV.isNestedScrollingEnabled=false
    }

    private fun initToolbar() {
        setSupportActionBar(appBar as Toolbar)
        titleTV.text = getString(R.string.my_account)
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
