package com.github.eunsiljo.customdialoglib.data;

/**
 * Created by EunsilJo on 2017. 7. 25..
 */

public class SelectItemData<T> {
    private boolean isSelected = false;
    private String text;
    private T key;

    public SelectItemData(String text) {
        this.text = text;
    }

    public SelectItemData(String text, T key) {
        this.text = text;
        this.key = key;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getText() {
        return text;
    }

    public T getKey() {
        return key;
    }
}
