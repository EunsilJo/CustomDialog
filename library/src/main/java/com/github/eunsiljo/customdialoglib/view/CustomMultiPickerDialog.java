package com.github.eunsiljo.customdialoglib.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.NumberPicker;

import com.github.eunsiljo.customdialoglib.R;

import java.lang.reflect.Field;


/**
 * Created by EunsilJo on 2017. 12. 4..
 */

public class CustomMultiPickerDialog extends CustomDialog implements View.OnClickListener{

    protected CustomMultiPickerDialog.Builder mBuilder;

    private View multiPicker;
    private NumberPicker numberPicker1;
    private NumberPicker numberPicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CustomMultiPickerDialog(CustomMultiPickerDialog.Builder builder) {
        super(builder);
        mBuilder = builder;

        setContentView(R.layout.dialog_custom_multipicker);
    }

    @Override
    protected void initDialogLayout() {
        multiPicker = findViewById(R.id.multiPicker);
        numberPicker1 = (NumberPicker)findViewById(R.id.numberPicker1);
        numberPicker2 = (NumberPicker)findViewById(R.id.numberPicker2);
    }

    @Override
    protected void initDialogListener() {

    }

    @Override
    protected void initDialogData(){
        setDividerDrawable(numberPicker1, getColorDrawable());
        setDividerDrawable(numberPicker2, getColorDrawable());

        numberPicker1.setMinValue(mBuilder.minValue1);
        numberPicker1.setMaxValue(mBuilder.maxValue1);
        numberPicker1.setDisplayedValues(mBuilder.displayValues1);
        numberPicker1.setValue(mBuilder.number1);

        numberPicker2.setMinValue(mBuilder.minValue2);
        numberPicker2.setMaxValue(mBuilder.maxValue2);
        numberPicker2.setDisplayedValues(mBuilder.displayValues2);
        numberPicker2.setValue(mBuilder.number2);
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
            CustomMultiPickerDialog.DialogMultiPickerCallback callback = mBuilder.multiPickerCallback;
            if(callback != null){
                callback.onNumberSet(this, multiPicker, numberPicker1.getValue(), numberPicker2.getValue());
            }
        }
        if(mBuilder.autoDismiss) dismiss();
    }

    public boolean setDividerDrawable(NumberPicker numberPicker, @DrawableRes int dividerRes) {
        if (dividerRes == 0) return false;
        try {
            Class<?> clsNumberPicker = Class.forName("android.widget.NumberPicker");
            Field clsSelectionDivider = clsNumberPicker.getDeclaredField("mSelectionDivider");

            clsSelectionDivider.setAccessible(true);
            clsSelectionDivider.set(numberPicker, ContextCompat.getDrawable(mBuilder.getContext(), dividerRes));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The class used to construct a CustomMultiPickerDialog.
     */
    public static class Builder extends CustomDialog.Builder{
        protected DialogColor dividerColor = DialogColor.ACCENT;
        protected int number1 = 0;
        protected int minValue1 = 0;
        protected int maxValue1 = 0;
        protected String[] displayValues1;
        protected int number2 = 0;
        protected int minValue2 = 0;
        protected int maxValue2 = 0;
        protected String[] displayValues2;
        protected CustomMultiPickerDialog.DialogMultiPickerCallback multiPickerCallback;


        public Builder(@NonNull Context context) {
            super(context);
        }

        public CustomMultiPickerDialog.Builder dividerColor(DialogColor dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        //Number1
        public CustomMultiPickerDialog.Builder number1(int number1) {
            this.number1 = number1;
            return this;
        }

        public CustomMultiPickerDialog.Builder minMaxValue1(int minValue1, int maxValue1) {
            this.minValue1 = minValue1;
            this.maxValue1 = maxValue1;
            return this;
        }

        public CustomMultiPickerDialog.Builder displayValues1(String[] displayValues1) {
            this.displayValues1 = displayValues1;
            return this;
        }

        //Number2
        public CustomMultiPickerDialog.Builder number2(int number2) {
            this.number2 = number2;
            return this;
        }

        public CustomMultiPickerDialog.Builder minMaxValue2(int minValue2, int maxValue2) {
            this.minValue2 = minValue2;
            this.maxValue2 = maxValue2;
            return this;
        }

        public CustomMultiPickerDialog.Builder displayValues2(String[] displayValues2) {
            this.displayValues2 = displayValues2;
            return this;
        }

        public CustomMultiPickerDialog.Builder multiPickerCallback(@NonNull CustomMultiPickerDialog.DialogMultiPickerCallback callback) {
            this.multiPickerCallback = callback;
            return this;
        }

        @Override
        @UiThread
        public CustomMultiPickerDialog build() {
            return new CustomMultiPickerDialog(this);
        }

        @Override
        @UiThread
        public CustomMultiPickerDialog show() {
            CustomMultiPickerDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }

    public interface DialogMultiPickerCallback {

        void onNumberSet(@NonNull CustomMultiPickerDialog dialog, View view, int number1, int number2);
    }
}