package com.github.eunsiljo.customdialoglib.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;


/**
 * Created by EunsilJo on 2017. 12. 4..
 */

public class DialogUtils {

    /**
     * Show keyboard
     *
     * @param dialog dialog
     */
    public static void showKeyboard(@NonNull Dialog dialog, @NonNull Context context) {
        if (dialog != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    /**
     * Hide keyboard
     *
     * @param dialog dialog
     */
    public static void hideKeyboard(@NonNull Dialog dialog, @NonNull Context context) {
        if (dialog != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
