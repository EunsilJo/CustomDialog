package com.github.eunsiljo.customdialoglib.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import java.lang.reflect.Field;


/**
 * Created by EunsilJo on 2017. 8. 21..
 */

public class CustomDatePicker extends DatePicker {
    public CustomDatePicker(Context context) {
        super(context);
        setPadding(0,0,0,0);
    }

    public CustomDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPadding(0,0,0,0);
    }

    public CustomDatePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPadding(0,0,0,0);
    }

    public boolean setShowYear(boolean show) {
        try {
            Class<?> clsParent = Class.forName("com.android.internal.R$id");
            NumberPicker clsYear = (NumberPicker) findViewById(clsParent.getField("year").getInt(null));
            if(show) {
                clsYear.setVisibility(VISIBLE);
            }else{
                clsYear.setVisibility(GONE);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setShowMonth(boolean show) {
        try {
            Class<?> clsParent = Class.forName("com.android.internal.R$id");
            NumberPicker clsMonth = (NumberPicker) findViewById(clsParent.getField("month").getInt(null));
            if(show) {
                clsMonth.setVisibility(VISIBLE);
            }else{
                clsMonth.setVisibility(GONE);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setShowDay(boolean show) {
        try {
            Class<?> clsParent = Class.forName("com.android.internal.R$id");
            NumberPicker clsDay = (NumberPicker) findViewById(clsParent.getField("day").getInt(null));
            if(show) {
                clsDay.setVisibility(VISIBLE);
            }else{
                clsDay.setVisibility(GONE);
            }
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
            NumberPicker clsYear = (NumberPicker) findViewById(clsParent.getField("year").getInt(null));
            NumberPicker clsMonth = (NumberPicker) findViewById(clsParent.getField("month").getInt(null));
            NumberPicker clsDay = (NumberPicker) findViewById(clsParent.getField("day").getInt(null));

            Class<?> clsNumberPicker = Class.forName("android.widget.NumberPicker");
            Field clsSelectionDivider = clsNumberPicker.getDeclaredField("mSelectionDivider");

            clsSelectionDivider.setAccessible(true);
            clsSelectionDivider.set(clsYear, getResources().getDrawable(dividerRes));
            clsSelectionDivider.set(clsMonth, getResources().getDrawable(dividerRes));
            clsSelectionDivider.set(clsDay, getResources().getDrawable(dividerRes));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}