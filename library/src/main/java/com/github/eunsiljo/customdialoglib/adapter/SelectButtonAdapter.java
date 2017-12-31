package com.github.eunsiljo.customdialoglib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.eunsiljo.customdialoglib.R;
import com.github.eunsiljo.customdialoglib.data.SelectItemData;
import com.github.eunsiljo.customdialoglib.view.CustomDialog;
import com.github.eunsiljo.customdialoglib.viewholder.OnItemClickListener;
import com.github.eunsiljo.customdialoglib.viewholder.SelectButtonViewHolder;
import com.github.eunsiljo.customdialoglib.viewholder.SelectItemViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by EunsilJo on 2017. 7. 25..
 */

public class SelectButtonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class CHOICE_MODE {
        public static final int MULTIPLE = 101;
        public static final int SINGLE = 202;
    }

    protected int choice = CHOICE_MODE.MULTIPLE;
    protected CustomDialog.DialogColor selectColor = CustomDialog.DialogColor.ACCENT;

    public SelectButtonAdapter() {
    }

    public SelectButtonAdapter(int choice) {
        this.choice = choice;
    }

    public void setChoice(int choice){
        this.choice = choice;
    }

    public void setSelectColor(CustomDialog.DialogColor selectColor) {
        this.selectColor = selectColor;
    }

    public int getTextPosition(String text){
        for(int i=0; i<items.size(); i++){
            if(text.equals(items.get(i).getText())){
                return i;
            }
        }
        return -1;
    }

    protected HashMap<Object, Integer> selectKeyMap = new HashMap();

    public void updateKeyMap(List<SelectItemData> items){
        for(int i=0; i<items.size(); i++){
            selectKeyMap.put(items.get(i).getKey(), i);
        }
    }

    public void clearKeyMap(){
        selectKeyMap.clear();
    }

    public int getKeyPosition(Object key){
        Integer integer = selectKeyMap.get(key);
        if(integer != null){
            return integer;
        }
        return -1;
    }

    protected List<SelectItemData> items = new ArrayList<SelectItemData>();

    public SelectItemData getItem(int position) {
        return items.get(position);
    }

    public void clear() {
        items.clear();
        clearKeyMap();
        notifyDataSetChanged();
    }

    public void removeAll(List<SelectItemData> items) {
        this.items.removeAll(items);
        notifyDataSetChanged();
    }

    public void add(SelectItemData item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<SelectItemData> items) {
        addAll(items, 0);
    }

    public void addAll(List<SelectItemData> items, int select) {
        this.items.addAll(items);
        if(choice == CHOICE_MODE.SINGLE){
            if(getItemCount() > select) {
                setItemSelected(select, true);
            }
        }
        notifyDataSetChanged();
    }

    public void addAll(List<SelectItemData> items, ArrayList<Integer> selects) {
        this.items.addAll(items);
        for(int i=0; i<selects.size(); i++){
            setItemSelected(selects.get(i), true);
            if(i == 0 && choice == CHOICE_MODE.SINGLE){
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void setItemSelected(int position, boolean selected) {
        if (items.get(position).isSelected() != selected) {
            if(choice == CHOICE_MODE.SINGLE){
                for(int i=0; i<items.size(); i++) {
                    items.get(i).setSelected(false);
                }
            }
            items.get(position).setSelected(selected);
            notifyDataSetChanged();
        }
    }

    public void setAllItemSelected(boolean selected) {
        if(choice == CHOICE_MODE.MULTIPLE){
            for(int i=0; i<items.size(); i++) {
                setItemSelected(i, selected);
            }
        }
    }

    public void toggle(int position) {
        if(choice == CHOICE_MODE.SINGLE){
            setItemSelected(position, true);
        }else{
            setItemSelected(position, !items.get(position).isSelected());
        }
    }

    public int getSelectedCnt(){
        int result = 0;
        for(SelectItemData item : items){
            if(item.isSelected()){
                result = result + 1;
            }
        }
        return result;
    }

    public ArrayList<SelectItemData> getSelectedItems(){
        ArrayList<SelectItemData> result = new ArrayList<>();
        for(SelectItemData item : items){
            if(item.isSelected()){
                result.add(item);
            }
        }
        return result;
    }

    public ArrayList<String> getSelectedTexts(){
        ArrayList<String> result = new ArrayList<>();
        for(SelectItemData item : items){
            if(item.isSelected()){
                result.add(item.getText());
            }
        }
        return result;
    }

    public ArrayList<Integer> getSelectedPositions(){
        ArrayList<Integer> result = new ArrayList<>();
        for(int i=0; i<items.size(); i++){
            SelectItemData item = items.get(i);
            if(item.isSelected()){
                result.add(i);
            }
        }
        return result;
    }

    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_select_button_item, parent, false);
        return new SelectButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SelectButtonViewHolder h = (SelectButtonViewHolder) holder;
        h.setChoice(choice);
        h.setSelectColor(selectColor);
        h.setSelectItem(items.get(position));
        h.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}