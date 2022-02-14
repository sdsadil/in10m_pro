package com.in10mServiceMan.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.in10mServiceMan.R;

public class CustomBookButtonView extends AppCompatButton {
    public CustomBookButtonView(Context context, AttributeSet attrs) {
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
