package com.github.eunsiljo.customdialoglib.view;

import android.content.Context;
import java.util.Calendar;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.DatePicker;

import com.github.eunsiljo.customdialoglib.R;


/**
 * Created by EunsilJo on 2017. 8. 21..
 */

public class CustomDatePickerDialog extends CustomDialog implements View.OnClickListener{

    protected CustomDatePickerDialog.Builder mBuilder;

    private CustomDatePicker datePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CustomDatePickerDialog(CustomDatePickerDialog.Builder builder) {
        super(builder);
        mBuilder = builder;

        setContentView(R.layout.dialog_custom_datepicker);
    }

    @Override
    protected void initDialogLayout() {
        datePicker = (CustomDatePicker)findViewById(R.id.datePicker);
    }

    @Override
    protected void initDialogListener() {

    }

    @Override
    protected void initDialogData(){
        datePicker.setDividerDrawable(getColorDrawable());

        datePicker.setShowYear(mBuilder.showYear);
        datePicker.setShowMonth(mBuilder.showMonthOfYear);
        datePicker.setShowDay(mBuilder.showDayOfMonth);

        datePicker.updateDate(mBuilder.year, (mBuilder.monthOfYear - 1), mBuilder.dayOfMonth);
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
            CustomDatePickerDialog.DialogDatePickerCallback callback = mBuilder.datePickerCallback;
            if(callback != null){
                callback.onDateSet(this, datePicker, datePicker.getYear(), (datePicker.getMonth() + 1), datePicker.getDayOfMonth());
            }
        }
        if(mBuilder.autoDismiss) dismiss();
    }

    /**
     * The class used to construct a CustomDatePickerDialog.
     */
    public static class Builder extends CustomDialog.Builder{
        protected DialogColor dividerColor = DialogColor.ACCENT;
        protected int year;
        protected int monthOfYear;
        protected int dayOfMonth;
        protected CustomDatePickerDialog.DialogDatePickerCallback datePickerCallback;

        protected boolean showYear = true;
        protected boolean showMonthOfYear = true;
        protected boolean showDayOfMonth = true;


        public Builder(@NonNull Context context) {
            super(context);
            Calendar calendar = Calendar.getInstance();
            this.year = calendar.get(Calendar.YEAR);
            this.monthOfYear = calendar.get(Calendar.MONTH) + 1;
            this.dayOfMonth =calendar.get(Calendar.DAY_OF_MONTH);
        }

        public CustomDatePickerDialog.Builder dividerColor(DialogColor dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        public CustomDatePickerDialog.Builder year(int year) {
            if (year <= 0) return this;
            this.year = year;
            return this;
        }

        public CustomDatePickerDialog.Builder monthOfYear(int monthOfYear) {
            if (monthOfYear <= 0) return this;
            this.monthOfYear = monthOfYear;
            return this;
        }

        public CustomDatePickerDialog.Builder dayOfMonth(int dayOfMonth) {
            if (dayOfMonth <= 0) return this;
            this.dayOfMonth = dayOfMonth;
            return this;
        }

        public CustomDatePickerDialog.Builder datePickerCallback(@NonNull CustomDatePickerDialog.DialogDatePickerCallback callback) {
            this.datePickerCallback = callback;
            return this;
        }

        public CustomDatePickerDialog.Builder showYear(boolean show) {
            this.showYear = show;
            return this;
        }

        public CustomDatePickerDialog.Builder showMonth(boolean show) {
            this.showMonthOfYear = show;
            return this;
        }

        public CustomDatePickerDialog.Builder showDay(boolean show) {
            this.showDayOfMonth = show;
            return this;
        }

        @Override
        @UiThread
        public CustomDatePickerDialog build() {
            return new CustomDatePickerDialog(this);
        }

        @Override
        @UiThread
        public CustomDatePickerDialog show() {
            CustomDatePickerDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }

    public interface DialogDatePickerCallback {

        void onDateSet(@NonNull CustomDatePickerDialog dialog, DatePicker view, int year, int monthOfYear, int dayOfMonth);
    }
}