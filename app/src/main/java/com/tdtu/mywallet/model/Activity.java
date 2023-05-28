package com.tdtu.mywallet.model;

import java.util.Calendar;

public class Activity {
    private String activityID;
    private String activityName;
    private int activityMoney;
    private String activityTimeDate;
    private String activityPlace;
    private Category activityCategory;
    private String activityType;

    public Activity(String activityID, String activityName, int activityMoney,  Category activityCategory, String activityType, String activityTimeDate, String activityPlace) {
        this.activityID = activityID;
        this.activityName = activityName;
        this.activityMoney = activityMoney;
        this.activityTimeDate = activityTimeDate;
        this.activityPlace = activityPlace;
        this.activityCategory = activityCategory;
        this.activityType = activityType;
    }

    public Activity(String activityID, String activityName, int activityMoney, Category activityCategory, String activityType) {
        this.activityID = activityID;
        this.activityName = activityName;
        this.activityMoney = activityMoney;
        this.activityCategory = activityCategory;
        this.activityType = activityType;
        this.activityPlace = null;
//        this.activityTimeDate =null;

        // only use for example activity
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        // Display current time and date
        String currentTimeAndDate = String.format("%02d:%02d:%02d - %02d/%02d/%04d", hour, minute, second, day, month + 1, year);
        this.activityTimeDate = currentTimeAndDate;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityMoney() {
        return activityMoney;
    }

    public void setActivityMoney(int activityMoney) {
        this.activityMoney = activityMoney;
    }

    public String getActivityTimeDate() {
        return activityTimeDate;
    }

    public void setActivityTimeDate(String activityTimeDate) {
        this.activityTimeDate = activityTimeDate;
    }

    public String getActivityPlace() {
        return activityPlace;
    }

    public void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace;
    }

    public Category getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(Category activityCategory) {
        this.activityCategory = activityCategory;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}
