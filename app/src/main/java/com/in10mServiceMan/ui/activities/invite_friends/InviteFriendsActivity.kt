package com.in10mServiceMan.ui.activities.invite_friends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.activity_invite_friends.*
import kotlinx.android.synthetic.main.app_bar_transparent.*

class InviteFriendsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_friends)

        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(appBar as Toolbar)
        titleTV.text = getString(R.string.invite_friends)
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
