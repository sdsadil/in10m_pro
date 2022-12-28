package com.in10mServiceMan.ui.custom_views

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.wagon_button.view.*

/**
 * Created by Rohit on 24/03/18.
 */
class WagonButton(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context!!, attrs) {

    init {

        View.inflate(context, R.layout.wagon_button, this)
        if (!isInEditMode) {
            val arr = context?.obtainStyledAttributes(attrs, R.styleable.WagonButton)

            val buttonText = arr?.getString(R.styleable.WagonButton_button_text)
           // val buttonImage = arr!!.getInt(R.styleable.WagonButton_button_image,R.drawable.ic_corner_right)

            wagonTV.text = buttonText
           // wagonIV.setImageResource(buttonImage)

            arr?.recycle()
        }
    }
}