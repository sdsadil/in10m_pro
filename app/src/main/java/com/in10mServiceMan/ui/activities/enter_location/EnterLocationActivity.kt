package com.in10mServiceMan.ui.activities.enter_location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.activity_enter_location.*
import kotlinx.android.synthetic.main.app_bar_transparent.*

class EnterLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_location)

        initToolbar()

        confirmCL.setOnClickListener { finish() }
    }

    private fun initToolbar() {
        setSupportActionBar(appBar as Toolbar)
        titleTV.text = getString(R.string.enter_address)
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
