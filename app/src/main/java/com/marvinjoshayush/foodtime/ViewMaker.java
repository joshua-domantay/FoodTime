package com.marvinjoshayush.foodtime;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class ViewMaker {
    public final static LayoutParams MATCH_MATCH = new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
    );

    public final static LayoutParams MATCH_WRAP = new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
    );

    public final static LayoutParams WRAP_WRAP = new LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
    );

    /** ImageView **/
    public static ImageView createBasicImageView(Context context, LayoutParams params, String imgStr, int gravity) {
        ImageView val = new ImageView(context);
        params.gravity = gravity;
        val.setLayoutParams(params);
        int img = context.getResources().getIdentifier(imgStr, "drawable", context.getPackageName());
        val.setImageResource(img);
        return val;
    }

    // With margins
    public static ImageView createBasicImageView(Context context, LayoutParams params, String imgStr, int gravity,
                                                 int mLeft, int mTop, int mRight, int mBottom) {
        params.setMargins(dpToPix(context.getResources(), mLeft), dpToPix(context.getResources(), mTop),
                dpToPix(context.getResources(), mRight), dpToPix(context.getResources(), mBottom));
        return createBasicImageView(context, params, imgStr, gravity);
    }

    // Without background / background is null
    public static ImageView createBasicImageView(Context context, LayoutParams params, String imgStr, int gravity, boolean nullBackground) {
        ImageView val = createBasicImageView(context, params, imgStr, gravity);
        if(nullBackground) { val.setBackground(null); }
        return val;
    }

    // With background
    public static ImageView createBasicImageView(Context context, LayoutParams params, String imgStr, int gravity, int background) {
        ImageView val = createBasicImageView(context, params, imgStr, gravity, false);
        val.setBackgroundColor(background);
        return val;
    }

    /** ImageButton **/
    public static ImageButton createBasicImageButton(Context context, LayoutParams params, String imgStr, int gravity) {
        ImageButton val = new ImageButton(context);
        params.gravity = gravity;
        val.setLayoutParams(params);
        int img = context.getResources().getIdentifier(imgStr, "drawable", context.getPackageName());
        val.setImageResource(img);
        return val;
    }

    // With margins
    public static ImageButton createBasicImageButton(Context context, LayoutParams params, String imgStr,
                                                     int gravity, int mLeft, int mTop, int mRight, int mBottom) {
        params.setMargins(dpToPix(context.getResources(), mLeft), dpToPix(context.getResources(), mTop),
                dpToPix(context.getResources(), mRight), dpToPix(context.getResources(), mBottom));
        return createBasicImageButton(context, params, imgStr, gravity);
    }

    // Without background / background is null
    public static ImageButton createBasicImageButton(Context context, LayoutParams params, String imgStr, int gravity, boolean nullBackground) {
        ImageButton val = createBasicImageButton(context, params, imgStr, gravity);
        if(nullBackground) { val.setBackground(null); }
        return val;
    }

    // With background
    public static ImageButton createBasicImageButton(Context context, LayoutParams params, String imgStr, int gravity, int background) {
        ImageButton val = createBasicImageButton(context, params, imgStr, gravity, false);
        val.setBackgroundColor(background);
        return val;
    }

    /** TEXTVIEW **/
    // Without specified margins or already in params
    public static TextView createBasicTextView(Context context, LayoutParams params, String text,
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
    public static TextView createBasicTextView(Context context, LayoutParams params, String text, int color, int size,
                                               int alignment, int typeface, int mLeft, int mTop, int mRight, int mBottom) {
        params.setMargins(dpToPix(context.getResources(), mLeft), dpToPix(context.getResources(), mTop),
                dpToPix(context.getResources(), mRight), dpToPix(context.getResources(), mBottom));
        return createBasicTextView(context, params, text, color, size, alignment, typeface);
    }

    // With alignment
    public static TextView createBasicTextView(Context context, LayoutParams params, String text, int color, int size, int alignment) {
        return createBasicTextView(context, params, text, color, size, alignment, Typeface.NORMAL);
    }

    // Without alignment
    public static TextView createBasicTextView(Context context, LayoutParams params, String text, int color, int size) {
        return createBasicTextView(context, params, text, color, size, TextView.TEXT_ALIGNMENT_VIEW_START, Typeface.NORMAL);
    }

    public static int dpToPix(Resources resource, int dp) {
        int pix = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resource.getDisplayMetrics()
        );
        return pix;
    }
}
