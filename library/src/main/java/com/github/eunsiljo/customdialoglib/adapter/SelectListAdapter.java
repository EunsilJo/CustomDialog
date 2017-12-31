package com.github.eunsiljo.customdialoglib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.eunsiljo.customdialoglib.R;
import com.github.eunsiljo.customdialoglib.viewholder.SelectItemViewHolder;


/**
 * Created by EunsilJo on 2017. 8. 18..
 */

public class SelectListAdapter extends SelectButtonAdapter {

    public SelectListAdapter() {
        super();
    }

    public SelectListAdapter(int choice) {
        super(choice);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_select_list_item, parent, false);
        return new SelectItemViewHolder(view);
    }
}
