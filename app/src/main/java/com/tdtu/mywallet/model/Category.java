package com.tdtu.mywallet.model;

public class Category {
    private String categoryName;
    private String categoryColor;
    private String iconResID;

    public Category() {
    }

    public Category(String categoryName, String categoryColor, String iconResID) {
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.iconResID = iconResID;
    }

    public Category(String categoryName, String iconResID) {
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

    public String getIconResID() {
        return iconResID;
    }

    public void setIconResID(String iconResID) {
        this.iconResID = iconResID;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
