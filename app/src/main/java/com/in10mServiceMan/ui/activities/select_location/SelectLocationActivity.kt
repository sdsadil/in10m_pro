package com.in10mServiceMan.ui.activities.select_location

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.enter_location.EnterLocationActivity
import com.in10mServiceMan.utils.RequestCode
import kotlinx.android.synthetic.main.activity_select_location.*


class SelectLocationActivity : AppCompatActivity(), SelectLocationAdapter.SelectLocationCallbacks {

    override fun onLocationSelected() {

        if (fromLocationET.hasFocus()) {
            fromLocationET.setText(getString(R.string.dummy_loc))
            toLocationET.requestFocus()
        } else {
            toLocationET.setText(getString(R.string.dummy_loc))
            setResult(RequestCode.REQUEST_LOCATION)
            finish()
        }
    }

    override fun onEnterLocationSelected() {

        startActivityForResult(Intent(this, EnterLocationActivity::class.java), RequestCode.ENTER_LOCATION)
    }

    private lateinit var selectLocationAdapter: SelectLocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)

        backIV.setOnClickListener { supportFinishAfterTransition() }



        initRV()
    }

    private fun initRV() {

        selectLocationAdapter = SelectLocationAdapter(this)
        locationRV.layoutManager = LinearLayoutManager(this)
        locationRV.adapter = selectLocationAdapter
    }

//    private fun showComponents() {
//
//        val constraintSet = ConstraintSet()
//        constraintSet.clone(this, R.layout.activity_select_location)
//
//        val transition = ChangeBounds()
//        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
//        transition.duration = 1200
//
//        TransitionManager.beginDelayedTransition(topLayout, transition)
//        constraintSet.applyTo(topLayout)
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }
}
