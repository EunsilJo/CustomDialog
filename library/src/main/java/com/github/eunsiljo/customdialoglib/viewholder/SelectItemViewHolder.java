package com.github.eunsiljo.customdialoglib.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.eunsiljo.customdialoglib.R;
import com.github.eunsiljo.customdialoglib.adapter.SelectButtonAdapter;
import com.github.eunsiljo.customdialoglib.adapter.SelectListAdapter;
import com.github.eunsiljo.customdialoglib.data.SelectItemData;
import com.github.eunsiljo.customdialoglib.view.CustomDialog;


/**
 * Created by EunsilJo on 2017. 7. 25..
 */

public class SelectItemViewHolder extends SelectButtonViewHolder{

    private ImageView imgSelect;

    public SelectItemViewHolder(View itemView) {
        super(itemView);
        imgSelect = (ImageView)itemView.findViewById(R.id.imgSelect);
    }

    @Override
    public void setSelectItem(SelectItemData selectItem) {
        if(selectItem != null) {
            mSelectItemData = selectItem;
            txtSelect.setText(selectItem.getText());
            imgSelect.setImageResource(getColorDrawable());
            itemView.setSelected(selectItem.isSelected());
        }
    }

    @Override
    protected int getColorDrawable(){
        int drawable;
        switch (choice) {
            case SelectButtonAdapter.CHOICE_MODE.SINGLE:
                switch (selectColor) {
                    case PRIMARY:
                        drawable = R.drawable.icon_radio_primary;
                        break;
                    case PRIMARYDARK:
                        drawable = R.drawable.icon_radio_primarydark;
                        break;
                    default:
                        drawable = R.drawable.icon_radio_accent;
                }
                break;
            default:
                switch (selectColor) {
                    case PRIMARY:
                        drawable = R.drawable.icon_check_primary;
                        break;
                    case PRIMARYDARK:
                        drawable = R.drawable.icon_check_primarydark;
                        break;
                    default:
                        drawable = R.drawable.icon_check_accent;
                }
        }
        return drawable;
    }
}
