package com.marvinjoshayush.foodtime;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class ViewMaker {
    public final static LinearLayout.LayoutParams MATCH_MATCH = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
    );

    public final static LinearLayout.LayoutParams MATCH_WRAP = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );

    public final static LinearLayout.LayoutParams WRAP_WRAP = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );

    // Without specified margins or already in params
    public static TextView createBasicTextView(Context context, LinearLayout.LayoutParams params, String text,
                                               int color, int size, int alignment, int typeface) {
        TextView val = new TextView(context);
        val.setLayoutParams(params);
        val.setText(text);
        val.setTextColor(ContextCompat.getColor(context, color));
        val.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        val.setTextAlignment(alignment);
        val.setTypeface(null, typeface);
        return val;
    }

    // With specified margins
    public static TextView createBasicTextView(Context context, LinearLayout.LayoutParams params, String text, int color, int size,
                                               int alignment, int typeface, int mLeft, int mTop, int mRight, int mBottom) {
        params.setMargins(mLeft, mTop, mRight, mBottom);
        return createBasicTextView(context, params, text, color, size, alignment, typeface);
    }

    // With alignment
    public static TextView createBasicTextView(Context context, LinearLayout.LayoutParams params, String text, int color, int size, int alignment) {
        return createBasicTextView(context, params, text, color, size, alignment, Typeface.NORMAL);
    }

    // Without alignment
    public static TextView createBasicTextView(Context context, LinearLayout.LayoutParams params, String text, int color, int size) {
        return createBasicTextView(context, params, text, color, size, TextView.TEXT_ALIGNMENT_VIEW_START, Typeface.NORMAL);
    }
}
