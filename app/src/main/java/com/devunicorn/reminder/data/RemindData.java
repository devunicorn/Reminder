package com.devunicorn.reminder.data;


import com.devunicorn.reminder.Constants;

import java.util.Date;

public class RemindData {

    private String title;
    private long date;
    private long time;
    private int priority;
    private int status;
    private long timeStamp;

    public RemindData() {
        this.status = -1;
        this.timeStamp = new Date().getTime();
    }

    public RemindData(String title, long date, int priority, int status, long timeStamp) {
        this.title = title;
        this.date = date;
        this.priority = priority;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public String getPriorityStatus() {
        switch (getPriority()) {
            case Constants.PRIORITY_HIGH:
                if (getStatus() == Constants.STATUS_CURRENT || getStatus() == Constants.STATUS_OVERDUE) {
                    return "High priority - done";
                } else {
                    return "High priority";
                }
            case Constants.PRIORITY_NORMAL:
                if (getStatus() == Constants.STATUS_CURRENT || getStatus() == Constants.STATUS_OVERDUE) {
                    return "Normal priority - done";
                } else {
                    return "Normal priority";
                }
            case Constants.PRIORITY_LOW:
                if (getStatus() == Constants.STATUS_CURRENT || getStatus() == Constants.STATUS_OVERDUE) {
                    return "Low priority - done";
                } else {
                    return "Low priority";
                }
            default:
                return "";
        }
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
