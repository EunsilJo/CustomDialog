package com.github.eunsiljo.customdialoglib.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.github.eunsiljo.customdialoglib.R;


/**
 * Created by EunsilJo on 2017. 6. 8..
 */

public class CustomDialog extends Dialog implements View.OnClickListener{

    protected Builder mBuilder;

    private TextView message;
    protected TextView positiveButton;
    protected TextView negativeButton;
    protected TextView neutralButton;

    protected View layoutDefaultButton;
    protected View layoutNeutralButton;

    public enum DialogAction {
        DEFAULT,
        NEUTRAL,
        NONE
    }

    public enum DialogColor {
        PRIMARY,
        PRIMARYDARK,
        ACCENT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initListener();
        initData();
    }

    public CustomDialog(Builder builder) {
        super(builder.context , R.style.CustomDialogTheme);
        mBuilder = builder;

        setContentView(R.layout.dialog_custom);
    }

    private void initLayout() {
        positiveButton = (TextView)findViewById(R.id.btnRight);
        negativeButton = (TextView)findViewById(R.id.btnLeft);
        neutralButton = (TextView)findViewById(R.id.btnNeutral);
        layoutDefaultButton = findViewById(R.id.layoutDefaultButton);
        layoutNeutralButton = findViewById(R.id.layoutNeutralButton);

        initDialogLayout();
    }

    private void initListener() {
        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);
        neutralButton.setOnClickListener(this);

        initDialogListener();
    }

    private void initData() {
        positiveButton.setText(mBuilder.positiveText);
        negativeButton.setText(mBuilder.negativeText);
        neutralButton.setText(mBuilder.neutralText);

        if(mBuilder.positiveColor != -1){
            positiveButton.setBackgroundColor(mBuilder.positiveColor);
        }else{
            positiveButton.setBackgroundResource(mBuilder.positiveDrawable);
        }

        if(mBuilder.negativeColor != -1){
            negativeButton.setBackgroundColor(mBuilder.negativeColor);
        }else{
            negativeButton.setBackgroundResource(mBuilder.negativeDrawable);
        }

        if(mBuilder.neutralColor != -1){
            negativeButton.setBackgroundColor(mBuilder.neutralColor);
        }else{
            neutralButton.setBackgroundResource(mBuilder.neutralDrawable);
        }

        setVisibilityActionButton(mBuilder.action);
        setCancelable(mBuilder.cancelable);
        setCanceledOnTouchOutside(mBuilder.canceledOnTouchOutside);

        initDialogData();
    }

    protected void initDialogLayout() {
        message = (TextView)findViewById(R.id.message);
        message.setMovementMethod(new ScrollingMovementMethod());
    }

    protected void initDialogListener() {

    }

    protected void initDialogData(){
        message.setText(mBuilder.content);
    }

    @Override
    public void onClick(View v) {
        if(v == positiveButton){
            DialogButtonCallback callback = mBuilder.onPositiveCallback;
            if(callback != null){
                callback.onClick(this);
            }
        }else if(v == negativeButton){
            DialogButtonCallback callback = mBuilder.onNegativeCallback;
            if(callback != null){
                callback.onClick(this);
            }
        }else if(v == neutralButton){
            DialogButtonCallback callback = mBuilder.onNeutralCallback;
            if(callback != null){
                callback.onClick(this);
            }
        }
        if(mBuilder.autoDismiss) dismiss();
    }

    protected void setVisibilityActionButton(DialogAction action){
        switch (action){
            case DEFAULT:
                layoutDefaultButton.setVisibility(View.VISIBLE);
                layoutNeutralButton.setVisibility(View.GONE);
                break;
            case NEUTRAL:
                layoutDefaultButton.setVisibility(View.GONE);
                layoutNeutralButton.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * The class used to construct a CustomDialog.
     */
    public static class Builder {
        protected final Context context;
        protected CharSequence content;
        protected CharSequence positiveText;
        protected CharSequence negativeText;
        protected CharSequence neutralText;
        protected int positiveDrawable = -1;
        protected int negativeDrawable = -1;
        protected int neutralDrawable = -1;
        protected int positiveColor = -1;
        protected int negativeColor = -1;
        protected int neutralColor = -1;

        protected boolean cancelable = true;
        protected boolean canceledOnTouchOutside = true;

        protected DialogButtonCallback onPositiveCallback;
        protected DialogButtonCallback onNegativeCallback;
        protected DialogButtonCallback onNeutralCallback;

        protected DialogAction action = DialogAction.DEFAULT;
        protected boolean autoDismiss = true;

        public Builder(@NonNull Context context) {
            this.context = context;
            positiveText = context.getText(android.R.string.yes);
            negativeText = context.getText(android.R.string.no);
            neutralText = context.getText(android.R.string.yes);
            positiveDrawable = R.color.colorPrimary;
            negativeDrawable = R.color.color_gray_light;
            neutralDrawable = R.color.colorPrimary;
        }

        public final Context getContext() {
            return context;
        }

        public Builder content(@StringRes int contentRes) {
            content(this.context.getText(contentRes));
            return this;
        }

        public Builder content(@NonNull CharSequence content) {
            this.content = content;
            return this;
        }

        public Builder positiveText(@StringRes int postiveRes) {
            if (postiveRes == 0) return this;
            positiveText(this.context.getText(postiveRes));
            return this;
        }

        public Builder positiveText(@NonNull CharSequence message) {
            this.positiveText = message;
            return this;
        }

        public Builder positiveDrawable(@DrawableRes int postiveRes) {
            if (postiveRes == 0) return this;
            this.positiveDrawable = postiveRes;
            return this;
        }

        public Builder positiveColor(@ColorRes int postiveRes) {
            if (postiveRes == 0) return this;
            this.positiveColor = postiveRes;
            return this;
        }

        public Builder negativeText(@StringRes int negativeRes) {
            if (negativeRes == 0) return this;
            return negativeText(this.context.getText(negativeRes));
        }

        public Builder negativeText(@NonNull CharSequence message) {
            this.negativeText = message;
            return this;
        }

        public Builder negativeDrawable(@DrawableRes int negativeRes) {
            if (negativeRes == 0) return this;
            this.negativeDrawable = negativeRes;
            return this;
        }

        public Builder negativeColor(@ColorRes int negativeRes) {
            if (negativeRes == 0) return this;
            this.negativeColor = negativeRes;
            return this;
        }

        public Builder neutralText(@StringRes int neutraleRes) {
            if (neutraleRes == 0) return this;
            return neutralText(this.context.getText(neutraleRes));
        }

        public Builder neutralText(@NonNull CharSequence message) {
            this.neutralText = message;
            return this;
        }

        public Builder neutralDrawable(@DrawableRes int neutralRes) {
            if (neutralRes == 0) return this;
            this.neutralDrawable = neutralRes;
            return this;
        }

        public Builder neutralColor(@ColorRes int neutralRes) {
            if (neutralRes == 0) return this;
            this.neutralColor = neutralRes;
            return this;
        }

        public Builder onPositive(@NonNull DialogButtonCallback callback) {
            this.onPositiveCallback = callback;
            return this;
        }

        public Builder onNegative(@NonNull DialogButtonCallback callback) {
            this.onNegativeCallback = callback;
            return this;
        }

        public Builder onNeutral(@NonNull DialogButtonCallback callback) {
            this.onNeutralCallback = callback;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            this.canceledOnTouchOutside = cancelable;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder action(DialogAction action){
            this.action = action;
            return this;
        }

        public Builder autoDismiss(boolean dismiss) {
            this.autoDismiss = dismiss;
            return this;
        }

        @UiThread
        public CustomDialog build() {
            return new CustomDialog(this);
        }

        @UiThread
        public CustomDialog show() {
            CustomDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }

    public interface DialogButtonCallback {

        void onClick(@NonNull CustomDialog dialog);
    }
}
