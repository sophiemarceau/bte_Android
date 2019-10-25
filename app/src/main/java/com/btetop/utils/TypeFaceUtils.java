package com.btetop.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.btetop.application.BteTopApplication;

public class TypeFaceUtils {

    public static TextView setTVTypeFace(Context context, TextView view) {
        Typeface typeface = Typeface.createFromAsset(BteTopApplication.getInstance().getAssets(), "font/DINMittelschrift-Alternate.otf");
        view.setTypeface(typeface);
        return view;
    }
}
