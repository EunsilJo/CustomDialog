package com.github.eunsiljo.customdialoglib.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.eunsiljo.customdialoglib.R;
import com.github.eunsiljo.customdialoglib.adapter.SelectButtonAdapter;
import com.github.eunsiljo.customdialoglib.data.SelectItemData;
import com.github.eunsiljo.customdialoglib.view.CustomDialog;

/**
 * Created by EunsilJo on 2017. 10. 10..
 */

public class SelectButtonViewHolder extends RecyclerView.ViewHolder{

    protected SelectItemData mSelectItemData;
    protected Context mContext;

    protected View itemView;
    protected TextView txtSelect;

    protected int choice = SelectButtonAdapter.CHOICE_MODE.MULTIPLE;
    protected CustomDialog.DialogColor selectColor = CustomDialog.DialogColor.ACCENT;

    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public SelectButtonViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        this.itemView = itemView;

        txtSelect = (TextView)itemView.findViewById(R.id.txtSelect);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, getLayoutPosition());
                }
            }
        });
    }

    public void setSelectItem(SelectItemData selectItem) {
        if(selectItem != null) {
            mSelectItemData = selectItem;
            txtSelect.setText(selectItem.getText());
            txtSelect.setTextColor(mContext.getResources().getColorStateList(getColor()));
            itemView.setBackgroundResource(getColorDrawable());
            itemView.setSelected(selectItem.isSelected());
        }
    }

    public void setChoice(int choice){
        this.choice = choice;
    }

    public void setSelectColor(CustomDialog.DialogColor selectColor) {
        this.selectColor = selectColor;
    }

    protected int getColorDrawable() {
        int drawable;
        switch (selectColor) {
            case PRIMARY:
                drawable = R.drawable.select_item_bg_primary;
                break;
            case PRIMARYDARK:
                drawable = R.drawable.select_item_bg_primarydark;
                break;
            default:
                drawable = R.drawable.select_item_bg_accent;
        }
        return drawable;
    }

    protected int getColor() {
        int color;
        switch (selectColor) {
            case PRIMARY:
                color = R.color.select_item_text_color_primary;
                break;
            case PRIMARYDARK:
                color = R.color.select_item_text_color_primarydark;
                break;
            default:
                color = R.color.select_item_text_color_accent;
        }
        return color;
    }
}