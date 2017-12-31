package com.github.eunsiljo.customdialoglib.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.View;

import com.github.eunsiljo.customdialoglib.R;

import java.util.Calendar;

/**
 * Created by EunsilJo on 2017. 12. 5..
 */

public class CustomStartEndPickerDialog extends CustomDialog implements View.OnClickListener{

    protected CustomStartEndPickerDialog.Builder mBuilder;
    
    private View startEndPicker;
    private CustomTimePicker startPicker;
    private CustomTimePicker endPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CustomStartEndPickerDialog(CustomStartEndPickerDialog.Builder builder) {
        super(builder);
        mBuilder = builder;

        setContentView(R.layout.dialog_custom_startendpicker);
    }

    @Override
    protected void initDialogLayout() {
        startEndPicker = findViewById(R.id.startEndPicker);
        startPicker = (CustomTimePicker)findViewById(R.id.startPicker);
        endPicker = (CustomTimePicker)findViewById(R.id.endPicker);

        int width = getContext().getResources().getDimensionPixelSize(R.dimen.picker_width_s);
        startPicker.setPickerWidth(width);
        endPicker.setPickerWidth(width);
    }

    @Override
    protected void initDialogListener() {

    }

    @Override
    protected void initDialogData(){
        startPicker.setDividerDrawable(getColorDrawable());
        endPicker.setDividerDrawable(getColorDrawable());

        startPicker.setCurrentHour(mBuilder.startHour);
        startPicker.setCurrentMinute(mBuilder.startMinute);
        startPicker.setIs24HourView(true);

        endPicker.setCurrentHour(mBuilder.endHour);
        endPicker.setCurrentMinute(mBuilder.endMinute);
        endPicker.setIs24HourView(true);
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
            CustomStartEndPickerDialog.DialogStartEndPickerCallback callback = mBuilder.startEndPickerCallback;
            if(callback != null){
                callback.onStartEndSet(this, startEndPicker,
                        startPicker.getCurrentHour(), startPicker.getCurrentMinute(),
                        endPicker.getCurrentHour(), endPicker.getCurrentMinute());
            }
        }
        if(mBuilder.autoDismiss) dismiss();
    }

    /**
     * The class used to construct a CustomStartEndPickerDialog.
     */
    public static class Builder extends CustomDialog.Builder{
        protected DialogColor dividerColor = DialogColor.ACCENT;
        protected int startHour;
        protected int startMinute;
        protected int endHour;
        protected int endMinute;
        protected CustomStartEndPickerDialog.DialogStartEndPickerCallback startEndPickerCallback;


        public Builder(@NonNull Context context) {
            super(context);
            Calendar calendar = Calendar.getInstance();
            this.startHour = calendar.get(Calendar.HOUR_OF_DAY);
            this.startMinute = calendar.get(Calendar.MINUTE);
            this.endHour = calendar.get(Calendar.HOUR_OF_DAY) + 1;
            this.endMinute = calendar.get(Calendar.MINUTE);
        }

        public CustomStartEndPickerDialog.Builder dividerColor(DialogColor dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        public CustomStartEndPickerDialog.Builder startHour(int startHour) {
            if (startHour < 0) return this;
            if (startHour >= 24) startHour = startHour - 24;
            this.startHour = startHour;
            return this;
        }

        public CustomStartEndPickerDialog.Builder startMinute(int startMinute) {
            if (startMinute < 0) return this;
            this.startMinute = startMinute;
            return this;
        }

        public CustomStartEndPickerDialog.Builder endHour(int endHour) {
            if (endHour < 0) return this;
            if (endHour >= 24) endHour = endHour - 24;
            this.endHour = endHour;
            return this;
        }

        public CustomStartEndPickerDialog.Builder endMinute(int endMinute) {
            if (endMinute < 0) return this;
            this.endMinute = endMinute;
            return this;
        }

        public CustomStartEndPickerDialog.Builder startEndPickerCallback(@NonNull CustomStartEndPickerDialog.DialogStartEndPickerCallback callback) {
            this.startEndPickerCallback = callback;
            return this;
        }

        @Override
        @UiThread
        public CustomStartEndPickerDialog build() {
            return new CustomStartEndPickerDialog(this);
        }

        @Override
        @UiThread
        public CustomStartEndPickerDialog show() {
            CustomStartEndPickerDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }

    public interface DialogStartEndPickerCallback {

        void onStartEndSet(@NonNull CustomStartEndPickerDialog dialog, View view,
                       int startHourOfDay, int startMinute, int endHourOfDay, int endMinute);
    }
}