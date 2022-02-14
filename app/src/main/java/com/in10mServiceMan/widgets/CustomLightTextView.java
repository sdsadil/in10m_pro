package com.in10mServiceMan.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.in10mServiceMan.R;

public class CustomLightTextView extends AppCompatTextView {
    public CustomLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode())
            applyFont(context);
    }

    @SuppressLint("NewApi")
    private void applyFont(Context context) {
        Typeface typeface = context.getResources().getFont(R.font.unineuelight);
        setTypeface(typeface);
    }
}
