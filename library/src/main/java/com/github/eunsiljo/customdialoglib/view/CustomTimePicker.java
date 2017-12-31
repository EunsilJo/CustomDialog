package com.github.eunsiljo.customdialoglib.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;

/**
 * Created by EunsilJo on 2017. 12. 5..
 */

public class CustomTimePicker extends TimePicker{
    public CustomTimePicker(Context context) {
        super(context);
        init();
    }

    public CustomTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setPadding(0, 0, 0, 0);
    }

    public boolean setPickerWidth(int width) {
        if (width < 0) return false;
        try {
            Class<?> clsParent = Class.forName("com.android.internal.R$id");
            NumberPicker clsHour = (NumberPicker) findViewById(clsParent.getField("hour").getInt(null));
            NumberPicker clsMinute = (NumberPicker) findViewById(clsParent.getField("minute").getInt(null));
            NumberPicker clsAmPm = (NumberPicker) findViewById(clsParent.getField("amPm").getInt(null));

            ((LinearLayout.LayoutParams)clsHour.getLayoutParams()).width = width;
            ((LinearLayout.LayoutParams)clsMinute.getLayoutParams()).width = width;
            ((LinearLayout.LayoutParams)clsAmPm.getLayoutParams()).width = width;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setDividerDrawable(@DrawableRes int dividerRes) {
        if (dividerRes == 0) return false;
        try {
            Class<?> clsParent = Class.forName("com.android.internal.R$id");
            NumberPicker clsHour = (NumberPicker) findViewById(clsParent.getField("hour").getInt(null));
            NumberPicker clsMinute = (NumberPicker) findViewById(clsParent.getField("minute").getInt(null));
            NumberPicker clsAmPm = (NumberPicker) findViewById(clsParent.getField("amPm").getInt(null));

            Class<?> clsNumberPicker = Class.forName("android.widget.NumberPicker");
            Field clsSelectionDivider = clsNumberPicker.getDeclaredField("mSelectionDivider");

            clsSelectionDivider.setAccessible(true);
            clsSelectionDivider.set(clsHour, getResources().getDrawable(dividerRes));
            clsSelectionDivider.set(clsMinute, getResources().getDrawable(dividerRes));
            clsSelectionDivider.set(clsAmPm, getResources().getDrawable(dividerRes));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
