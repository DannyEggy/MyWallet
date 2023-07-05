package com.tdtu.mywallet.model;

public class Category {
    private String categoryName;
    private String categoryColor;
    private int iconResID;

    public Category() {
    }

    public Category(String categoryName, String categoryColor, int iconResID) {
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.iconResID = iconResID;
    }

    public Category(String categoryName, int iconResID) {
        this.categoryName = categoryName;
        this.iconResID = iconResID;
        this.categoryColor = "white";
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }

    public int getIconResID() {
        return iconResID;
    }

    public void setIconResID(int iconResID) {
        this.iconResID = iconResID;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
