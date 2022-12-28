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
class WagonSmallButton(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context!!, attrs) {

    init {
        View.inflate(context, R.layout.wagon_small_button, this)
        if (!isInEditMode) {
            val arr = context?.obtainStyledAttributes(attrs, R.styleable.WagonButton)

            val buttonText = arr?.getString(R.styleable.WagonButton_button_text)

            wagonTV.text = buttonText

            arr?.recycle()
        }
    }
}