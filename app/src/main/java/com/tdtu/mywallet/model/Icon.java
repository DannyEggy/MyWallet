package com.tdtu.mywallet.model;

public class Icon {
    private String iconName;
    private String iconResID;

    public Icon() {
    }

    public Icon(String iconName, String iconResID) {
        this.iconName = iconName;
        this.iconResID = iconResID;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconResID() {
        return iconResID;
    }

    public void setIconResID(String iconResID) {
        this.iconResID = iconResID;
    }

    @Override
    public String toString() {
        return iconName ;
    }
}
