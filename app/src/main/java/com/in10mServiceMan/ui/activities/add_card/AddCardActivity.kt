package com.in10mServiceMan.ui.activities.add_card

import com.in10mServiceMan.ui.base.In10mBaseActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.app_bar_transparent.*

class AddCardActivity : In10mBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(appBar as Toolbar)
        titleTV.text = getString(R.string.item_details)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {

            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
