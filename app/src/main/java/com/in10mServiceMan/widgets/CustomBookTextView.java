package com.in10mServiceMan.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.in10mServiceMan.R;

public class CustomBookTextView extends AppCompatTextView {
    public CustomBookTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode())
            applyFont(context);
    }

    @SuppressLint("NewApi")
    private void applyFont(Context context) {
        Typeface typeface = context.getResources().getFont(R.font.unineuebook);
        setTypeface(typeface);
    }
}
