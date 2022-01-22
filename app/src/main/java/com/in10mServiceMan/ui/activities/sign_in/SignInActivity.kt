package com.in10mServiceMan.ui.activities.sign_in

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.set_up_profile.SetUpProfileActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.app_bar_center_title.*
import com.in10mServiceMan.ui.base.In10mBaseActivity

class SignInActivity : In10mBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initActionBar()

        verifyOtpCL.setOnClickListener {
            startActivity(Intent(this, SetUpProfileActivity::class.java))
        }
    }

    private fun initActionBar() {
        setSupportActionBar(appBar as Toolbar)
        titleTV.text = getString(R.string.sign_in)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
