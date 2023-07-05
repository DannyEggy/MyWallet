package com.tdtu.mywallet.model;

public class Icon {
    private String iconName;
    private int iconResID;

    public Icon() {
    }

    public Icon(String iconName, int iconResID) {
        this.iconName = iconName;
        this.iconResID = iconResID;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getIconResID() {
        return iconResID;
    }

    public void setIconResID(int iconResID) {
        this.iconResID = iconResID;
    }

    @Override
    public String toString() {
        return iconName ;
    }
}
