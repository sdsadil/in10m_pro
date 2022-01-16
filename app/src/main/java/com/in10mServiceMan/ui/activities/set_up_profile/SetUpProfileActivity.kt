package com.in10mServiceMan.ui.activities.set_up_profile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.home.HomeActivity
import com.in10mServiceMan.ui.base.In10mBaseActivity
import kotlinx.android.synthetic.main.activity_set_up_profile.*
import kotlinx.android.synthetic.main.app_bar_center_title.*

class SetUpProfileActivity : In10mBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_up_profile)

        initActionBar()

        completeProfileBT.setOnClickListener {

            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        nameET.onFocusChangeListener = onFocusListner
        nameET.setOnTouchListener(onTouchListener)

        emailET.onFocusChangeListener = onFocusListner
        emailET.setOnTouchListener(onTouchListener)

        referalET.onFocusChangeListener = onFocusListner
        referalET.setOnTouchListener(onTouchListener)
    }

    private fun initActionBar() {
        setSupportActionBar(appBar as Toolbar)
        titleTV.text = getString(R.string.setup_profile)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private val onFocusListner = View.OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            Handler().postDelayed({
                parentSV.smoothScrollTo(0, v.y.toInt() - 50)
            }, 400)

        }
    }

    private val onTouchListener = View.OnTouchListener { v, event ->

        if (event.action == MotionEvent.ACTION_DOWN) {
            Handler().postDelayed({
                parentSV.smoothScrollTo(0, v.y.toInt() - 50)
            }, 400)
        }

        return@OnTouchListener false
    }
}
