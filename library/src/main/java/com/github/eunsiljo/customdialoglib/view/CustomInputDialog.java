package com.github.eunsiljo.customdialoglib.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.github.eunsiljo.customdialoglib.R;
import com.github.eunsiljo.customdialoglib.utils.DialogUtils;

import java.lang.reflect.Field;


/**
 * Created by EunsilJo on 2017. 8. 14..
 */

public class CustomInputDialog extends CustomDialog implements View.OnClickListener{

    protected CustomInputDialog.Builder mBuilder;

    private EditText input;
    private TextView txtLength;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CustomInputDialog(CustomInputDialog.Builder builder) {
        super(builder);
        mBuilder = builder;

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContentView(R.layout.dialog_custom_input);
    }

    @Override
    protected void initDialogLayout() {
        input = (EditText)findViewById(R.id.input);
        txtLength = (TextView)findViewById(R.id.txtLength);
    }

    @Override
    protected void initDialogListener() {
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setLengthView(s.length());
            }
        });
    }

    @Override
    protected void initDialogData(){
        input.setBackgroundResource(getColorDrawable());
        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(input, getCursorColorDrawable());
        } catch (Exception e) {
        }
        txtLength.setTextColor(mBuilder.context.getResources().getColor(getColor()));

        input.setHint(mBuilder.hint);
        input.setSingleLine(mBuilder.singleLine);
        input.setMaxLines(mBuilder.maxLines);

        CharSequence prefill = mBuilder.prefill;
        int max = mBuilder.maxLength;
        if(max > 0 && prefill.length() > max){
            prefill = prefill.subSequence(0, max);
        }
        input.setText(prefill);
        input.setFilters(mBuilder.inputFilters);

        if(mBuilder.showLength){
            setLengthView(input.getText().length());
            txtLength.setVisibility(View.VISIBLE);
        }else{
            txtLength.setVisibility(View.GONE);
        }
        input.setSelection(input.getText().length());
    }

    private int getColorDrawable(){
        int drawable;
        switch (mBuilder.inputColor){
            case PRIMARY:
                drawable = R.drawable.edit_bg_primary;
                break;
            case PRIMARYDARK:
                drawable = R.drawable.edit_bg_primarydark;
                break;
            default:
                drawable = R.drawable.edit_bg_accent;
        }
        return drawable;
    }

    private int getCursorColorDrawable(){
        int drawable;
        switch (mBuilder.inputColor){
            case PRIMARY:
                drawable = R.drawable.cursor_primary;
                break;
            case PRIMARYDARK:
                drawable = R.drawable.cursor_primarydark;
                break;
            default:
                drawable = R.drawable.cursor_accent;
        }
        return drawable;
    }

    private int getColor(){
        int color;
        switch (mBuilder.inputColor){
            case PRIMARY:
                color = R.color.colorPrimary;
                break;
            case PRIMARYDARK:
                color = R.color.colorPrimaryDark;
                break;
            default:
                color = R.color.colorAccent;
        }
        return color;
    }

    @Override
    public void onClick(View v) {
        if(v == positiveButton || v == neutralButton){
            CustomInputDialog.DialogInputCallback callback = mBuilder.inputCallback;
            if(callback != null){
                callback.onClick(this, input.getText());
            }
        }
        if(mBuilder.autoDismiss) dismiss();
    }

    private void setLengthView(int length){
        txtLength.setText(String.format(getContext().getString(R.string.custom_input_dialog_length),
                length, mBuilder.maxLength));
    }

    @Override
    public void dismiss() {
        DialogUtils.hideKeyboard(this, mBuilder.getContext());
        super.dismiss();
    }


    /**
     * The class used to construct a CustomInputDialog.
     */
    public static class Builder extends CustomDialog.Builder{
        protected DialogColor inputColor = DialogColor.ACCENT;
        protected CharSequence hint;
        protected CharSequence prefill;
        protected boolean singleLine = true;
        protected int maxLines = 1;
        protected int maxLength = 0;
        protected InputFilter[] inputFilters = new InputFilter[0];

        protected boolean showLength = false;

        protected CustomInputDialog.DialogInputCallback inputCallback;


        public Builder(@NonNull Context context) {
            super(context);
        }

        public CustomInputDialog.Builder inputColor(DialogColor inputColor) {
            this.inputColor = inputColor;
            return this;
        }

        public CustomInputDialog.Builder hint(@StringRes int contentRes) {
            hint(this.context.getText(contentRes));
            return this;
        }

        public CustomInputDialog.Builder hint(@NonNull CharSequence hint) {
            this.hint = hint;
            return this;
        }

        public CustomInputDialog.Builder prefill(@StringRes int contentRes) {
            prefill(this.context.getText(contentRes));
            return this;
        }

        public CustomInputDialog.Builder prefill(@NonNull CharSequence prefill) {
            this.prefill = prefill;
            return this;
        }

        public CustomInputDialog.Builder singleLine(boolean singleLine) {
            this.singleLine = singleLine;
            return this;
        }

        public CustomInputDialog.Builder maxLines(int maxLines) {
            if (maxLines <= 0) return this;
            this.maxLines = maxLines;
            this.singleLine = (maxLines == 1);
            return this;
        }

        public CustomInputDialog.Builder maxLength(int maxLength) {
            if (maxLength <= 0) return this;
            this.maxLength = maxLength;
            InputFilter[] inputFilters = new InputFilter[1];
            inputFilters[0] = new InputFilter.LengthFilter(maxLength);
            this.inputFilters = inputFilters;
            return this;
        }

        public CustomInputDialog.Builder showLength(boolean show) {
            this.showLength = show;
            return this;
        }

        public CustomInputDialog.Builder inputCallback(@NonNull CustomInputDialog.DialogInputCallback callback) {
            this.inputCallback = callback;
            return this;
        }

        @Override
        @UiThread
        public CustomInputDialog build() {
            return new CustomInputDialog(this);
        }

        @Override
        @UiThread
        public CustomInputDialog show() {
            CustomInputDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }

    public interface DialogInputCallback {

        void onClick(@NonNull CustomInputDialog dialog, CharSequence input);
    }
}