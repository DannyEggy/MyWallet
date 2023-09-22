package com.tdtu.mywallet.model;

public class Category {
    private String categoryName;
    private String categoryColor;
    private String iconResID;
    private int categoryID;

    public Category() {
    }

    public Category(int categoryID,String categoryName, String categoryColor, String iconResID) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.iconResID = iconResID;
    }

    public Category(int categoryID,String categoryName, String iconResID) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.iconResID = iconResID;
        this.categoryColor = "white";
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
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
