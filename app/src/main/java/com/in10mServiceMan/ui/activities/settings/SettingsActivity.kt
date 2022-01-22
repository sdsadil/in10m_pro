package com.in10mServiceMan.ui.activities.settings

import android.content.Intent
import com.in10mServiceMan.ui.base.In10mBaseActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.enter_mobile_no.EnterPhoneNumberActivity
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.app_bar_transparent.*

class SettingsActivity : In10mBaseActivity() {

    var isToggleOn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        button_close.setOnClickListener {
            finish()
        }
        toggleBtn.setOnClickListener {
            if (isToggleOn) {
                toggleBtn.setImageResource(R.drawable.greytoggle)
                isToggleOn = false
            } else {
                toggleBtn.setImageResource(R.drawable.greentoggle)
                isToggleOn = true
            }

        }
        // logout
        textView33.setOnClickListener {
            localStorage(this@SettingsActivity).logoutUser()
            startActivity(Intent(this@SettingsActivity, EnterPhoneNumberActivity::class.java))
            finishAffinity()
        }
    }
}
