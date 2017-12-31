package com.github.eunsiljo.customdialoglib.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TimePicker;

import com.github.eunsiljo.customdialoglib.R;

import java.util.Calendar;

/**
 * Created by EunsilJo on 2017. 12. 5..
 */

public class CustomTimePickerDialog extends CustomDialog implements View.OnClickListener{

    protected CustomTimePickerDialog.Builder mBuilder;

    private CustomTimePicker timePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CustomTimePickerDialog(CustomTimePickerDialog.Builder builder) {
        super(builder);
        mBuilder = builder;

        setContentView(R.layout.dialog_custom_timepicker);
    }

    @Override
    protected void initDialogLayout() {
        timePicker = (CustomTimePicker)findViewById(R.id.timePicker);
    }

    @Override
    protected void initDialogListener() {

    }

    @Override
    protected void initDialogData(){
        timePicker.setDividerDrawable(getColorDrawable());

        timePicker.setCurrentHour(mBuilder.hour);
        timePicker.setCurrentMinute(mBuilder.minute);
        timePicker.setIs24HourView(mBuilder.is24HourView);
    }

    private int getColorDrawable(){
        int drawable;
        switch (mBuilder.dividerColor){
            case PRIMARY:
                drawable = R.drawable.divider_primary;
                break;
            case PRIMARYDARK:
                drawable = R.drawable.divider_primarydark;
                break;
            default:
                drawable = R.drawable.divider_accent;
        }
        return drawable;
    }

    @Override
    public void onClick(View v) {
        if(v == positiveButton || v == neutralButton){
            CustomTimePickerDialog.DialogTimePickerCallback callback = mBuilder.timePickerCallback;
            if(callback != null){
                callback.onTimeSet(this, timePicker, timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            }
        }
        if(mBuilder.autoDismiss) dismiss();
    }

    /**
     * The class used to construct a CustomTimePickerDialog.
     */
    public static class Builder extends CustomDialog.Builder{
        protected DialogColor dividerColor = DialogColor.ACCENT;
        protected int hour;
        protected int minute;
        protected boolean is24HourView = false;
        protected CustomTimePickerDialog.DialogTimePickerCallback timePickerCallback;


        public Builder(@NonNull Context context) {
            super(context);
            Calendar calendar = Calendar.getInstance();
            this.hour = calendar.get(Calendar.HOUR_OF_DAY);
            this.minute = calendar.get(Calendar.MINUTE);
        }

        public CustomTimePickerDialog.Builder dividerColor(DialogColor dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        public CustomTimePickerDialog.Builder hour(int hour) {
            if (hour < 0) return this;
            if (hour >= 24) hour = hour - 24;
            this.hour = hour;
            return this;
        }

        public CustomTimePickerDialog.Builder minute(int minute) {
            if (minute < 0) return this;
            this.minute = minute;
            return this;
        }

        public CustomTimePickerDialog.Builder is24HourView(boolean is24HourView) {
            this.is24HourView = is24HourView;
            return this;
        }

        public CustomTimePickerDialog.Builder timePickerCallback(@NonNull CustomTimePickerDialog.DialogTimePickerCallback callback) {
            this.timePickerCallback = callback;
            return this;
        }

        @Override
        @UiThread
        public CustomTimePickerDialog build() {
            return new CustomTimePickerDialog(this);
        }

        @Override
        @UiThread
        public CustomTimePickerDialog show() {
            CustomTimePickerDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }

    public interface DialogTimePickerCallback {

        void onTimeSet(@NonNull CustomTimePickerDialog dialog, TimePicker view, int hourOfDay, int minute);
    }
}