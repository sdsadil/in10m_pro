package com.in10mServiceMan.ui.activities.vehicle_details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.in10mServiceMan.R
import com.in10mServiceMan.db.WagonItem
import com.in10mServiceMan.utils.WKey
import kotlinx.android.synthetic.main.activity_vehicle_detail.*
import kotlinx.android.synthetic.main.app_bar_transparent.*

class VehicleDetailActivity : AppCompatActivity() {


    private lateinit var wagonItem: WagonItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_detail)

        initToolbar()

        if (intent.extras != null) {

            if (intent.extras!!.containsKey(WKey.WAGON_ITEM)) {
                wagonItem = intent.extras!!.getSerializable(WKey.WAGON_ITEM) as WagonItem
                setData()
            }
        }

        doneCL.setOnClickListener {
            supportFinishAfterTransition()
        }

    }

    private fun setData() {
        carIV.setImageResource(wagonItem.wagonImage)
        carIV.isSelected = true
        carNameTV.text= wagonItem.wagonName
    }


    private fun initToolbar() {
        setSupportActionBar(appBar as Toolbar)
        titleTV.text = getString(R.string.vehicle_details)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> {
                supportFinishAfterTransition()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}
