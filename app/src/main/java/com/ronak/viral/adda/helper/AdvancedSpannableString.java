package com.ronak.viral.adda.helper;

/**
 * Created by priyasindkar on 12-10-2015.
 */

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

public class AdvancedSpannableString extends SpannableString {

    public String mainString;
    private OnClickableSpanListner listner;

    public AdvancedSpannableString(CharSequence source) {
        super(source);
        mainString = source.toString();

    }

    public void setSpanClickListener(OnClickableSpanListner listner) {
        this.listner = listner;
    }

    public void setColor(int color, String subString) {

        int start = mainString.indexOf(subString);
        int end = start + subString.length();
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    public void setBold(String subString) {

        int start = mainString.indexOf(subString);
        int end = start + subString.length();
        StyleSpan span = new StyleSpan(Typeface.BOLD);
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void setItalic(String subString) {

        int start = mainString.indexOf(subString);
        int end = start + subString.length();
        StyleSpan span = new StyleSpan(Typeface.ITALIC);
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void setBoldItalic(String subString) {

        int start = mainString.indexOf(subString);
        int end = start + subString.length();
        StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void setCustomSizeOf(float size, String subString) {

        int start = mainString.indexOf(subString);
        int end = start + subString.length();
        RelativeSizeSpan span = new RelativeSizeSpan(size);
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    public void setStrikeThroughIn(String subString) {

        int start = mainString.indexOf(subString);
        int end = start + subString.length();
        StrikethroughSpan span = new StrikethroughSpan();
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    public void setUnderLine(String subString) {

        int start = mainString.indexOf(subString);
        int end = start + subString.length();
        UnderlineSpan span = new UnderlineSpan();
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    public void setClickableSpanTo(String subString) {

        int start = mainString.indexOf(subString);
        int end = start + subString.length();
        ClickableSpan span = new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                listner.onSpanClick();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };


        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    public void setUrlSpan(String url, String subString) {

        int start = mainString.indexOf(subString);
        int end = start + subString.length();
        URLSpan span = new URLSpan(url);
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    public static interface OnClickableSpanListner {
        public void onSpanClick();
    }


}