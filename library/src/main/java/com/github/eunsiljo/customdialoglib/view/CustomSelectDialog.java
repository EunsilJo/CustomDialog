package com.github.eunsiljo.customdialoglib.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.eunsiljo.customdialoglib.R;
import com.github.eunsiljo.customdialoglib.adapter.SelectButtonAdapter;
import com.github.eunsiljo.customdialoglib.adapter.SelectListAdapter;
import com.github.eunsiljo.customdialoglib.data.SelectItemData;
import com.github.eunsiljo.customdialoglib.viewholder.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by EunsilJo on 2017. 8. 18..
 */

public class CustomSelectDialog extends CustomDialog implements View.OnClickListener {

    protected CustomSelectDialog.Builder mBuilder;

    private View layoutSelectList;
    private View layoutSelectButton;

    private RecyclerView mListView;
    private SelectButtonAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    public enum DialogSelectType {
        BUTTON,
        LIST
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CustomSelectDialog(CustomSelectDialog.Builder builder) {
        super(builder);
        mBuilder = builder;

        setContentView(R.layout.dialog_custom_select);
    }

    @Override
    protected void initDialogLayout() {
        layoutSelectList = findViewById(R.id.layoutSelectList);
        layoutSelectButton = findViewById(R.id.layoutSelectButton);

        if(mBuilder.type == DialogSelectType.LIST) {
            mAdapter = new SelectListAdapter();
            mLayoutManager = new LinearLayoutManager(getContext());
            mListView = (RecyclerView)findViewById(R.id.selectList);
        }else{
            mAdapter = new SelectButtonAdapter();
            mLayoutManager = new GridLayoutManager(getContext(), mBuilder.spanCount);
            mListView = (RecyclerView)findViewById(R.id.selectButton);
        }
        mListView.setAdapter(mAdapter);
        mListView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void initDialogListener() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mAdapter.toggle(position);
                if(mBuilder.action == DialogAction.NONE){
                    positiveButton.callOnClick();
                }
            }
        });
    }

    @Override
    protected void initDialogData(){
        mAdapter.setChoice(mBuilder.choice);
        mAdapter.setSelectColor(mBuilder.selectColor);

        ArrayList<SelectItemData> selectItems = new ArrayList<>();
        for(String item : mBuilder.items){
            selectItems.add(new SelectItemData(item));
        }
        switch (mBuilder.choice) {
            case SelectButtonAdapter.CHOICE_MODE.MULTIPLE:
                mAdapter.addAll(selectItems, mBuilder.selectedItems);
                break;
            case SelectButtonAdapter.CHOICE_MODE.SINGLE:
                mAdapter.addAll(selectItems, mBuilder.selectedItem);
                break;
        }

        setVisibilitySelectLayout(mBuilder.type);
    }

    @Override
    public void onClick(View v) {
        if(v == positiveButton || v == neutralButton){
            switch (mBuilder.choice) {
                case SelectButtonAdapter.CHOICE_MODE.MULTIPLE:
                    CustomSelectDialog.DialogMultiChoiceListCallback multi = mBuilder.multiChoiceListCallback;
                    if(multi != null){
                        multi.onSelection(this, mAdapter.getSelectedPositions(), mAdapter.getSelectedTexts());
                    }
                    break;
                case SelectButtonAdapter.CHOICE_MODE.SINGLE:
                    CustomSelectDialog.DialogSingleChoiceListCallback single = mBuilder.singleChoiceListCallback;
                    if(single != null){
                        single.onSelection(this, mAdapter.getSelectedPositions().get(0), mAdapter.getSelectedTexts().get(0));
                    }
                    break;
            }
        }
        if(mBuilder.autoDismiss) dismiss();
    }

    private void setVisibilitySelectLayout(DialogSelectType type){
        if(type == DialogSelectType.LIST) {
            layoutSelectList.setVisibility(View.VISIBLE);
            layoutSelectButton.setVisibility(View.GONE);
        }else{
            layoutSelectList.setVisibility(View.GONE);
            layoutSelectButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setVisibilityActionButton(CustomDialog.DialogAction action) {
        switch (action) {
            case DEFAULT:
                layoutDefaultButton.setVisibility(View.VISIBLE);
                layoutNeutralButton.setVisibility(View.GONE);
                break;
            case NEUTRAL:
                layoutDefaultButton.setVisibility(View.GONE);
                layoutNeutralButton.setVisibility(View.VISIBLE);
                break;
            default:
                layoutDefaultButton.setVisibility(View.GONE);
                layoutNeutralButton.setVisibility(View.GONE);
        }
    }

    /**
     * The class used to construct a CustomSelectDialog.
     */
    public static class Builder extends CustomDialog.Builder{
        protected DialogColor selectColor = DialogColor.ACCENT;
        protected ArrayList<String> items;
        protected int selectedItem = 0;
        protected ArrayList<Integer> selectedItems = new ArrayList<>();
        protected int choice = SelectButtonAdapter.CHOICE_MODE.MULTIPLE;
        protected DialogSelectType type = DialogSelectType.LIST;
        protected int spanCount = 3;

        protected CustomSelectDialog.DialogMultiChoiceListCallback multiChoiceListCallback;
        protected CustomSelectDialog.DialogSingleChoiceListCallback singleChoiceListCallback;


        public Builder(@NonNull Context context) {
            super(context);
        }

        public CustomSelectDialog.Builder selectColor(DialogColor selectColor) {
            this.selectColor = selectColor;
            return this;
        }

        public CustomSelectDialog.Builder items(@NonNull Collection items) {
            this.items = new ArrayList<>(items);
            return this;
        }

        public CustomSelectDialog.Builder selectedItem(int selectedItem) {
            this.selectedItem = selectedItem;
            return this;
        }

        public CustomSelectDialog.Builder selectedItems(ArrayList<Integer> selectedItems) {
            this.selectedItems = selectedItems;
            return this;
        }

        public CustomSelectDialog.Builder choice(int choice) {
            this.choice = choice;
            return this;
        }

        public CustomSelectDialog.Builder type(DialogSelectType type) {
            this.type = type;
            return this;
        }

        public CustomSelectDialog.Builder spanCount(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }

        public CustomSelectDialog.Builder multiChoiceListCallback(@NonNull CustomSelectDialog.DialogMultiChoiceListCallback callback) {
            this.multiChoiceListCallback = callback;
            return this;
        }

        public CustomSelectDialog.Builder singleChoiceListCallback(@NonNull CustomSelectDialog.DialogSingleChoiceListCallback callback) {
            this.singleChoiceListCallback = callback;
            return this;
        }

        @Override
        @UiThread
        public CustomSelectDialog build() {
            return new CustomSelectDialog(this);
        }

        @Override
        @UiThread
        public CustomSelectDialog show() {
            CustomSelectDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }

    public interface DialogMultiChoiceListCallback {

        boolean onSelection(@NonNull CustomSelectDialog dialog, ArrayList<Integer> which, ArrayList<String> text);
    }
    public interface DialogSingleChoiceListCallback {

        boolean onSelection(@NonNull CustomSelectDialog dialog, int which, String text);
    }
}
