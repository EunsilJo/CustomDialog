package com.github.eunsiljo.customdialog;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.eunsiljo.customdialoglib.adapter.SelectButtonAdapter;
import com.github.eunsiljo.customdialoglib.view.CustomDatePickerDialog;
import com.github.eunsiljo.customdialoglib.view.CustomDialog;
import com.github.eunsiljo.customdialoglib.view.CustomInputDialog;
import com.github.eunsiljo.customdialoglib.view.CustomSelectDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mListView;
    private DemoAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private List<String> mSamples = Arrays.asList("Apple", "Banana", "Cherry", "Durian", "Melon", "Orange");
    private ArrayList<Integer> mSelectedItems = new ArrayList<>(Arrays.asList(1, 2, 5));
    private int mSelectedItem = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        initListener();
        initData();
    }

    private void initLayout(){
        mAdapter = new DemoAdapter();
        mListView = (RecyclerView)findViewById(R.id.recycler);
        mListView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mListView.setLayoutManager(mLayoutManager);
    }

    private void initListener(){
        mAdapter.setOnItemClickListener(new DemoItemViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showAlertDialog(position);
            }
        });
    }

    private void initData(){
        ArrayList<String> demos = new ArrayList<>();
        demos.add("CustomDialog");
        demos.add("CustomDialog - Neutral");
        demos.add("CustomInputDialog");
        demos.add("CustomInputDialog - Prefill");
        demos.add("CustomSelectDialog - CheckBox List");
        demos.add("CustomSelectDialog - RadioButton List");
        demos.add("CustomSelectDialog - Button");
        demos.add("CustomDatePickerDialog");
        demos.add("CustomDatePickerDialog - Year, Month");
        demos.add("CustomDialog - Button Color");
        mAdapter.addAll(demos);
    }

    private void showAlertDialog(int position){
        switch (position){
            case 0:
                //CustomDialog
                showAlertDialogOkCancel(MainActivity.this,
                        "Are you sure you want to sign out?",
                        "Sign Out",
                        "Cancel",
                        new CustomDialog.DialogButtonCallback() {
                            @Override
                            public void onClick(@NonNull CustomDialog dialog) {
                                showToast(MainActivity.this, "Click Sign Out!");
                            }
                        },
                        new CustomDialog.DialogButtonCallback() {
                            @Override
                            public void onClick(@NonNull CustomDialog dialog) {
                                showToast(MainActivity.this, "Click Cancel!");
                            }
                        });
                break;
            case 1:
                //CustomDialog - Neutral
                showAlertDialogOk(MainActivity.this,
                        "The verification e-mail sent. Please check.",
                        "OK",
                        new CustomDialog.DialogButtonCallback() {
                            @Override
                            public void onClick(@NonNull CustomDialog dialog) {
                                showToast(MainActivity.this, "Click OK!");
                            }
                        });
                break;
            case 2:
                //CustomInputDialog
                showInputAlertDialog(MainActivity.this,
                        "Please enter your review.",
                        "",
                        2,
                        50,
                        true,
                        "OK",
                        CustomDialog.DialogColor.PRIMARYDARK,
                        new CustomInputDialog.DialogInputCallback() {
                            @Override
                            public void onClick(@NonNull CustomInputDialog dialog, CharSequence input) {
                                showToast(MainActivity.this, "Input " + input + "!");
                            }
                        });
                break;
            case 3:
                //CustomInputDialog - Prefill
                showInputAlertDialog(MainActivity.this,
                        "www.instagram.com/[instagram id]",
                        "www.instagram.com/",
                        1,
                        -1,
                        false,
                        "OK",
                        CustomDialog.DialogColor.ACCENT,
                        new CustomInputDialog.DialogInputCallback() {
                            @Override
                            public void onClick(@NonNull CustomInputDialog dialog, CharSequence input) {
                                showToast(MainActivity.this, "Input " + input + "!");
                            }
                        });
                break;
            case 4:
                //CustomSelectDialog - CheckBox List
                showSelectAlertDialog(MainActivity.this,
                        mSamples,
                        mSelectedItems,
                        CustomDialog.DialogColor.PRIMARY,
                        CustomSelectDialog.DialogSelectType.LIST,
                        new CustomSelectDialog.DialogMultiChoiceListCallback() {
                            @Override
                            public boolean onSelection(@NonNull CustomSelectDialog dialog,
                                                       ArrayList<Integer> which, ArrayList<String> text) {
                                StringBuffer s_text = new StringBuffer();
                                for(String t : text){
                                    s_text.append(t);
                                    s_text.append(" ");
                                }
                                showToast(MainActivity.this, "Select " + s_text.toString() + "!");
                                mSelectedItems = which;
                                return true;
                            }
                        });
                break;
            case 5:
                //CustomSelectDialog - RadioButton List
                showSelectAlertDialog(MainActivity.this,
                        mSamples,
                        mSelectedItem,
                        CustomDialog.DialogColor.PRIMARYDARK,
                        new CustomSelectDialog.DialogSingleChoiceListCallback() {
                            @Override
                            public boolean onSelection(@NonNull CustomSelectDialog dialog, int which, String text) {
                                showToast(MainActivity.this, "Select " + text + "!");
                                mSelectedItem = which;
                                return true;
                            }
                        });
                break;
            case 6:
                //CustomSelectDialog - Button
                showSelectAlertDialog(MainActivity.this,
                        mSamples,
                        mSelectedItems,
                        CustomDialog.DialogColor.ACCENT,
                        CustomSelectDialog.DialogSelectType.BUTTON,
                        new CustomSelectDialog.DialogMultiChoiceListCallback() {
                            @Override
                            public boolean onSelection(@NonNull CustomSelectDialog dialog,
                                                       ArrayList<Integer> which, ArrayList<String> text) {
                                StringBuffer s_text = new StringBuffer();
                                for(String t : text){
                                    s_text.append(t);
                                    s_text.append(" ");
                                }
                                showToast(MainActivity.this, "Select " + s_text.toString() + "!");
                                mSelectedItems = which;
                                return true;
                            }
                        });
                break;
            case 7:
                //CustomDatePickerDialog
                showDatePickerAlertDialog(MainActivity.this,
                        2017,
                        9,
                        27,
                        CustomDialog.DialogColor.PRIMARY,
                        new CustomDatePickerDialog.DialogDatePickerCallback() {
                            @Override
                            public void onDateSet(@NonNull CustomDatePickerDialog dialog,
                                                  DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                showToast(MainActivity.this, "Pick " + year + "/" + monthOfYear + "/" + dayOfMonth + "!");
                            }
                        });
                break;
            case 8:
                //CustomDatePickerDialog - Year, Month
                showDatePickerAlertDialog(MainActivity.this,
                        2017,
                        9,
                        -1,
                        CustomDialog.DialogColor.PRIMARYDARK,
                        new CustomDatePickerDialog.DialogDatePickerCallback() {
                            @Override
                            public void onDateSet(@NonNull CustomDatePickerDialog dialog,
                                                  DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                showToast(MainActivity.this, "Pick " + year + "/" + monthOfYear + "!");
                            }
                        });
                break;
            case 9:
                //CustomDialog - Button Color
                showAlertDialogOkCancel(MainActivity.this,
                        "Are you sure you want to sign out?",
                        "Sign Out",
                        "Cancel",
                        R.color.colorAccent,
                        Color.parseColor("#212121"),
                        new CustomDialog.DialogButtonCallback() {
                            @Override
                            public void onClick(@NonNull CustomDialog dialog) {
                                showToast(MainActivity.this, "Click Sign Out!");
                            }
                        },
                        new CustomDialog.DialogButtonCallback() {
                            @Override
                            public void onClick(@NonNull CustomDialog dialog) {
                                showToast(MainActivity.this, "Click Cancel!");
                            }
                        });
                break;
        }
    }

    // =============================================================================
    // Alert Dialog
    // =============================================================================

    private CustomDialog showAlertDialogOk(Activity activity, String msg, String ok,
                                          CustomDialog.DialogButtonCallback listenerOk){
        if(activity.isFinishing()) {
            return null;
        }
        CustomDialog dialog = new CustomDialog.Builder(activity)
                .content(msg)
                .neutralText(ok)
                .onNeutral(listenerOk)
                .action(CustomDialog.DialogAction.NEUTRAL)
                .cancelable(false)
                .build();
        dialog.show();

        return dialog;
    }

    private CustomDialog showAlertDialogOkCancel(Activity activity, String msg, String ok, String cancel,
                                                 CustomDialog.DialogButtonCallback listenerOk,
                                                 CustomDialog.DialogButtonCallback listenerCancel){
        if(activity.isFinishing()) {
            return null;
        }
        CustomDialog dialog = new CustomDialog.Builder(activity)
                .content(msg)
                .positiveText(ok)
                .onPositive(listenerOk)
                .negativeText(cancel)
                .onNegative(listenerCancel)
                .cancelable(false)
                .build();
        dialog.show();

        return dialog;
    }

    private CustomDialog showAlertDialogOkCancel(Activity activity, String msg, String ok, String cancel,
                                                 int positiveColor, int negativeColor,
                                                 CustomDialog.DialogButtonCallback listenerOk,
                                                 CustomDialog.DialogButtonCallback listenerCancel){
        if(activity.isFinishing()) {
            return null;
        }
        CustomDialog dialog = new CustomDialog.Builder(activity)
                .content(msg)
                .positiveText(ok)
                .positiveDrawable(positiveColor)
                .onPositive(listenerOk)
                .negativeText(cancel)
                .negativeColor(negativeColor)
                .onNegative(listenerCancel)
                .cancelable(false)
                .build();
        dialog.show();

        return dialog;
    }

    private CustomDialog showInputAlertDialog(Activity activity, String hint, String prefill, int maxLines,
                                              int maxLength, boolean showLength, String ok,
                                              CustomDialog.DialogColor inputColor,
                                              CustomInputDialog.DialogInputCallback listenerInput){
        if(activity.isFinishing()) {
            return null;
        }
        CustomDialog dialog = new CustomInputDialog.Builder(activity)
                .hint(hint)
                .prefill(prefill)
                .maxLines(maxLines)
                .maxLength(maxLength)
                .showLength(showLength)
                .inputCallback(listenerInput)
                .inputColor(inputColor)
                .neutralText(ok)
                .action(CustomDialog.DialogAction.NEUTRAL)
                .cancelable(true)
                .build();
        dialog.show();

        return dialog;
    }

    private CustomDialog showSelectAlertDialog(Activity activity, Collection selects, int selectItem,
                                               CustomDialog.DialogColor selectColor,
                                               CustomSelectDialog.DialogSingleChoiceListCallback listenerSingle){
        if(activity.isFinishing()) {
            return null;
        }
        if(selects != null && selects.size() != 0) {
            CustomDialog dialog = new CustomSelectDialog.Builder(activity)
                    .items(selects)
                    .selectedItem(selectItem)
                    .choice(SelectButtonAdapter.CHOICE_MODE.SINGLE)
                    .singleChoiceListCallback(listenerSingle)
                    .selectColor(selectColor)
                    .action(CustomDialog.DialogAction.NONE)
                    .cancelable(true)
                    .build();
            dialog.show();

            return dialog;
        }else{
            showToast(activity, "There is no list.");
            return null;
        }
    }

    private CustomDialog showSelectAlertDialog(Activity activity, Collection selects, ArrayList<Integer> selectedItems,
                                               CustomDialog.DialogColor selectColor,
                                               CustomSelectDialog.DialogSelectType type,
                                               CustomSelectDialog.DialogMultiChoiceListCallback listenerMulti){
        if(activity.isFinishing()) {
            return null;
        }
        if(selects != null && selects.size() != 0) {
            CustomDialog dialog = new CustomSelectDialog.Builder(activity)
                    .items(selects)
                    .selectedItems(selectedItems)
                    .choice(SelectButtonAdapter.CHOICE_MODE.MULTIPLE)
                    .type(type)
                    .multiChoiceListCallback(listenerMulti)
                    .selectColor(selectColor)
                    .action(CustomDialog.DialogAction.NEUTRAL)
                    .cancelable(true)
                    .build();
            dialog.show();

            return dialog;
        }else{
            showToast(activity, "There is no list.");
            return null;
        }
    }

    private CustomDialog showDatePickerAlertDialog(Activity activity, int year, int monthOfYear, int dayOfMonth,
                                                   CustomDialog.DialogColor dividerColor,
                                                   CustomDatePickerDialog.DialogDatePickerCallback listenerDatePicker){
        if(activity.isFinishing()) {
            return null;
        }
        CustomDialog dialog = new CustomDatePickerDialog.Builder(activity)
                .year(year)
                .monthOfYear(monthOfYear)
                .dayOfMonth(dayOfMonth)
                .showYear(year > 0)
                .showMonth(monthOfYear > 0)
                .showDay(dayOfMonth > 0)
                .datePickerCallback(listenerDatePicker)
                .dividerColor(dividerColor)
                .cancelable(true)
                .build();
        dialog.show();

        return dialog;
    }

    // =============================================================================
    // Toast
    // =============================================================================

    private void showToast(Activity activity, String msg){
        Toast toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }
}
